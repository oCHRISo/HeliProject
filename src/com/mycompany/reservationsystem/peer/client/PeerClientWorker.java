package com.mycompany.reservationsystem.peer.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

import com.mycompany.reservationsystem.peer.communication.COMMUNICATION_MESSAGES;
import com.mycompany.reservationsystem.peer.data.Peer;
import com.mycompany.reservationsystem.peer.data.PeerTable;

/*
 * Peer client thread getting ip addresses from peer server

    Begin
    Connect to the server
    Request an ip address
    Read response and check if ip address is known or unknown
        if ip address is unknown
        then connect to the DB and add the ip address with inactive state with epoch time
    Repeat steps 3 and 4 until the server sends a blank ip address
    End
 */
public class PeerClientWorker extends Thread {
	private static final int PORT_NUMBER = 50000;
	private Socket requestSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
 	private String ipAddress;
 	private boolean isFinished;
 	
 	public PeerClientWorker(String ipAddress){
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
				sendMessage(COMMUNICATION_MESSAGES.IP_REQUEST);
				
				String message = (String) in.readObject();
				if(message.startsWith(COMMUNICATION_MESSAGES.IP_RESPONSE.toString())){
					/*
					 * Message with no ip address shows that server has given all ip address, 
					 * server will return IP_RESPONSE: (12 chars)
					 */
					if(message.length() != 12){
						String ipAddress = message.substring(message.indexOf(":")+1, message.length());
						
						//Add ip address to DB
						PeerTable peerTable = PeerTable.getInstance();
						Peer newPeer = new Peer(ipAddress,Peer.STATE.INACTIVE, new Date().getTime());
						peerTable.connect();
						peerTable.addPeer(newPeer);
						peerTable.disconnect();
					}
					else{
						isFinished = true;
					}
				}
			}
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			logPeerInactive(ipAddress); //Log that this peer ip address is inactive
		}
		
		catch(IOException ioException){
			ioException.printStackTrace();
			logPeerInactive(ipAddress); //Log that this peer ip address is inactive
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
				logPeerInactive(ipAddress);
			}
		}
	}
	
	private void sendMessage(COMMUNICATION_MESSAGES communicationMessage){
		String message = "";
		if(communicationMessage.equals(COMMUNICATION_MESSAGES.IP_REQUEST)){
			message += COMMUNICATION_MESSAGES.IP_REQUEST.toString();
		}
		
		try{
			out.writeObject(message);
			out.flush();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
			logPeerInactive(ipAddress); //Log that this peer ip address is inactive
		}
	}
	
	private void logPeerInactive(String ipAddress){
		PeerTable peerTable = PeerTable.getInstance();
		peerTable.connect();
		Peer peer = peerTable.findPeerByIpAddress(ipAddress);
		peer.setState(Peer.STATE.INACTIVE);
		peer.setEpochTime(new Date().getTime());
		peerTable.updatePeer(peer);
		peerTable.disconnect();
	}
}