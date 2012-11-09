package com.mycompany.reservationsystem.peer.daemon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.mycompany.reservationsystem.peer.data.Database;
import com.mycompany.reservationsystem.peer.data.FlightBooking;
import com.mycompany.reservationsystem.peer.data.FlightTime;
import com.mycompany.reservationsystem.peer.data.PropertyFile;

public class TransactionDaemon extends Thread {
	public void run(){
		while(true){
			try {
				sleep(60000 * PropertyFile.getInstance().getExecuteTranactionDaemonTime());
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//System.out.println("Running Transaction Daemon");
			//1352335297129L;
			Date currentTime = Calendar.getInstance().getTime();
			Calendar startPeriod = Calendar.getInstance();
			startPeriod.setTime(currentTime);
			startPeriod.add(Calendar.MINUTE, -(PropertyFile.getInstance().getTransactionTimePeriod()*2));
			Calendar endPeriod = Calendar.getInstance();
			endPeriod.setTime(currentTime);
			endPeriod.add(Calendar.MINUTE, -PropertyFile.getInstance().getTransactionTimePeriod());
			
			//long period = PropertyFile.getInstance().getTransactionTimePeriod() * EPOCH_MINUTE; //7200L;
			
			long startOfPeriod = startPeriod.getTime().getTime(); //1352335282729L;
			long endOfPeriod = endPeriod.getTime().getTime(); //1352335289929L;
			
			//1352441950862
			
			System.out.println("startOfPeriod " + startOfPeriod);
			System.out.println("endOfPeriod " + endOfPeriod);
			System.out.println("period " + PropertyFile.getInstance().getTransactionTimePeriod());
			System.out.println("currentEpoch " + currentTime.getTime());
			
			ArrayList<FlightBooking> timePeriodBookings = Database.getInstance().findBooking(startOfPeriod, endOfPeriod);
			
			ArrayList<FlightBooking> cancelTransactions = getTransactionsByState(timePeriodBookings, FlightBooking.STATE.CANCEL);
			//Do CANCEL transactions first
			processCancelTransactions(cancelTransactions);
			
			ArrayList<FlightBooking> requestedTransactions = getTransactionsByState(timePeriodBookings, FlightBooking.STATE.REQUESTED);
			processRequestedTransactions(requestedTransactions);		
		}
	}
	
	private ArrayList<FlightBooking> getTransactionsByState(ArrayList<FlightBooking> timePeriodBookings, FlightBooking.STATE state){
		ArrayList<FlightBooking> bookings = new ArrayList<FlightBooking>();
		
		for(FlightBooking flightBooking : timePeriodBookings){
			if(flightBooking.getState().toString().equals(state.toString())){
				bookings.add(flightBooking);
			}
		}
		
		return bookings;
	}
	
	private void processCancelTransactions(ArrayList<FlightBooking> cancelTransactions){
		//For each cancel transaction
		for(FlightBooking flightBooking : cancelTransactions){
			//Check to see if there is a confirmed transaction
			ArrayList<FlightBooking> confirmedBooking = Database.getInstance().findBookings(flightBooking.getEmail(), flightBooking.getFlightToCityAt(), 
					flightBooking.getFlightToCampAt(), FlightBooking.STATE.CONFIRMED);
			
			//If there are no confirmed transaction, then cannot cancel a transaction that has not been confirmed
			if(confirmedBooking.size() == 0){ 
				flightBooking.setTransactionTime(new Date().getTime());
				flightBooking.setState(FlightBooking.STATE.CANCEL_REJECTED);
			}
			//Else there is a confirmed booking, cancel the confirmed transaction
			else{ 
				flightBooking.setTransactionTime(new Date().getTime());
				flightBooking.setState(FlightBooking.STATE.CANCELED);	
			}
			
			//Check to see if transaction is already done
			if(Database.getInstance().findBookings(flightBooking.getEmail(), flightBooking.getFlightToCityAt(), 
					flightBooking.getFlightToCampAt(), flightBooking.getState()).size() == 0){
				Database.getInstance().addBooking(flightBooking);
			}
		}
	}
	
	private void processRequestedTransactions(ArrayList<FlightBooking> requestedTransactions){
		for(FlightBooking flightBooking : requestedTransactions){
			String dateTime = "";
			boolean toCity = false;
			if(flightBooking.getFlightToCityAt().equals("NA")){
				dateTime = flightBooking.getFlightToCampAt();
				toCity = false;
			}
			else if(flightBooking.getFlightToCampAt().equals("NA")){
				dateTime = flightBooking.getFlightToCityAt();
				toCity = true;
			}
			
			ArrayList<FlightBooking> allTransactionsForDateTime = Database.getInstance().findBooking(dateTime, toCity);
			
			int numOfConfirmed = 0;
			int numOfCanceled = 0;
			for(FlightBooking transaction : allTransactionsForDateTime){
				if(transaction.getState().toString().equals(FlightBooking.STATE.CONFIRMED.toString())){
					numOfConfirmed++;
				}
				if(transaction.getState().toString().equals(FlightBooking.STATE.CANCELED.toString())){
					numOfCanceled++;
				}
			}
			
			int seatsUsed = numOfConfirmed - numOfCanceled;
			String time = dateTime.substring(dateTime.indexOf("@")+1);
			
			FlightTime flightTime = Database.getInstance().getNumberSeats(time);
			
			//Can confirm the transaction
			if(seatsUsed < flightTime.getNumOfSeats()){
				flightBooking.setTransactionTime(new Date().getTime());
				flightBooking.setState(FlightBooking.STATE.CONFIRMED);
			}
			//Have to reject the transaction
			else{
				flightBooking.setTransactionTime(new Date().getTime());
				flightBooking.setState(FlightBooking.STATE.REJECTED);
			}
			
			//Check to see if transaction is already done		
			if(Database.getInstance().findBookings(flightBooking.getEmail(), flightBooking.getFlightToCityAt(), 
					flightBooking.getFlightToCampAt(), FlightBooking.STATE.CONFIRMED).size() == 0 && Database.getInstance().findBookings(flightBooking.getEmail(), flightBooking.getFlightToCityAt(), 
							flightBooking.getFlightToCampAt(), FlightBooking.STATE.REJECTED).size() == 0){
				Database.getInstance().addBooking(flightBooking);
			}
		}
	}
}
