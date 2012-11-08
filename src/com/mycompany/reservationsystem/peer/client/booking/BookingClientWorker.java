package com.mycompany.reservationsystem.peer.client.booking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

import com.mycompany.reservationsystem.peer.communication.CommunicationMessages;
import com.mycompany.reservationsystem.peer.data.Database;
import com.mycompany.reservationsystem.peer.data.FlightBooking;
import com.mycompany.reservationsystem.peer.data.Peer;
import com.mycompany.reservationsystem.peer.data.SystemLogger;

public class BookingClientWorker extends Thread{
	private static final int PORT_NUMBER = 50001;
	private Socket requestSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
 	private String ipAddress;
 	private boolean isFinished;
 	
 	public BookingClientWorker(String ipAddress){
 		this.ipAddress = ipAddress;
 		this.isFinished = false;
 	}
 	
 	public void run(){
 		try{
			requestSocket = new Socket(this.ipAddress, PORT_NUMBER);
			
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			while(isFinished == false){
				sendMessage(CommunicationMessages.TRANSACTION_REQUEST);
				
				String message = (String) in.readObject();
				if(message.startsWith(CommunicationMessages.TRANSACTION_RESPONSE.toString())){
					/*
					 * Message with no booking shows that server has given all bookings, 
					 * server will return TRANSACTION_RESPONSE: (21 chars)
					 */
					if(message.length() != 21){ //Got a booking
						String dataPartOfMessage = message.substring(message.indexOf(":")+1, message.length());
						
						//Flight booking that was sent from another peer
						FlightBooking booking = parseBookingMessage(dataPartOfMessage);
						
						//Trying to find the same booking within this peers DB 
						FlightBooking flightBookingFromDatabase = Database.getInstance().findFlightBooking(booking.getTransactionTime(),
								booking.getEmail());
						
						//If the booking from another peer is not equal to any of this peers booking then save the booking
						if(!(booking.getTransactionTime() == flightBookingFromDatabase.getTransactionTime() &&
								booking.getEmail().equals(flightBookingFromDatabase.getEmail()))){
							Database.getInstance().addBooking(booking);
						}
					}
					else{ //Got blank booking
						isFinished = true;
					}
				}
			}
 		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		catch(IOException ioException){
			ioException.printStackTrace();
			Peer peer = new Peer();
			peer = Database.getInstance().findPeerByIpAddress(ipAddress);
			peer.setState(Peer.STATE.INACTIVE);
			peer.setEpochTime(new Date().getTime());
			Database.getInstance().updatePeer(peer);
			SystemLogger.getInstance().logWarning(ioException.getMessage() + " Could be a firewall problem");
		}
		finally{
			//Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
			catch (NullPointerException e) {
			}
		}
 	}
 	
 	private void sendMessage(CommunicationMessages communicationMessage){
		String message = "";
		if(communicationMessage.toString().equals(CommunicationMessages.TRANSACTION_REQUEST.toString())){
			message += CommunicationMessages.TRANSACTION_REQUEST.toString();
		}
		
		try{
			out.writeObject(message);
			out.flush();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
 	
 	//Sample data = 1351858413975,bob@gmail.com,31/08/2012@1200,NA,0,1,0,REQUESTED,
 	private FlightBooking parseBookingMessage(String dataPartOfMessage){
 		FlightBooking booking = new FlightBooking();
 		
 		String transactionEpoch = dataPartOfMessage.substring(0,dataPartOfMessage.indexOf(","));
 		dataPartOfMessage = dataPartOfMessage.substring(dataPartOfMessage.indexOf(",")+1, dataPartOfMessage.length());
 		String email = dataPartOfMessage.substring(0,dataPartOfMessage.indexOf(","));
 		dataPartOfMessage = dataPartOfMessage.substring(dataPartOfMessage.indexOf(",")+1, dataPartOfMessage.length());
 		String flightToCityAt = dataPartOfMessage.substring(0,dataPartOfMessage.indexOf(","));
 		dataPartOfMessage = dataPartOfMessage.substring(dataPartOfMessage.indexOf(",")+1, dataPartOfMessage.length());
 		String flightToCampAt = dataPartOfMessage.substring(0,dataPartOfMessage.indexOf(","));
 		dataPartOfMessage = dataPartOfMessage.substring(dataPartOfMessage.indexOf(",")+1, dataPartOfMessage.length());
 		String fromCity = dataPartOfMessage.substring(0,dataPartOfMessage.indexOf(","));
 		dataPartOfMessage = dataPartOfMessage.substring(dataPartOfMessage.indexOf(",")+1, dataPartOfMessage.length());
 		String fromCamp = dataPartOfMessage.substring(0,dataPartOfMessage.indexOf(","));
 		dataPartOfMessage = dataPartOfMessage.substring(dataPartOfMessage.indexOf(",")+1, dataPartOfMessage.length());
 		String price = dataPartOfMessage.substring(0,dataPartOfMessage.indexOf(","));
 		dataPartOfMessage = dataPartOfMessage.substring(dataPartOfMessage.indexOf(",")+1, dataPartOfMessage.length());
 		String state = dataPartOfMessage.substring(0,dataPartOfMessage.indexOf(","));
 		
 		System.out.println(transactionEpoch + " " + email);
 		
 		booking.setTransactionTime(Long.parseLong(transactionEpoch));
 		booking.setEmail(email);
 		booking.setFlightToCityAt(flightToCityAt);
 		booking.setFlightToCampAt(flightToCampAt);
 		booking.setPrice(Double.parseDouble(price));
 		
 		if(Integer.parseInt(fromCity) == 1){
 			booking.setFromCity(true);
 		}
 		else{
 			booking.setFromCity(false);
 		}
 		
 		if(Integer.parseInt(fromCamp) == 1){
 			booking.setFromCamp(true);
 		}
 		else{
 			booking.setFromCamp(false);
 		}
 		
 		if(state.equals(FlightBooking.STATE.REQUESTED.toString())){
 			booking.setState(FlightBooking.STATE.REQUESTED);
 		}
 		else if(state.equals(FlightBooking.STATE.CONFIRMED.toString())){
 			booking.setState(FlightBooking.STATE.CONFIRMED);
 		}
 		else if(state.equals(FlightBooking.STATE.CANCEL.toString())){
 			booking.setState(FlightBooking.STATE.CANCEL);
 		}
 		else if(state.equals(FlightBooking.STATE.CANCELED.toString())){
 			booking.setState(FlightBooking.STATE.CANCELED);
 		}
 		return booking;
 	}
}
