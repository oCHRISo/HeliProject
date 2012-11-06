package com.mycompany.reservationsystem.peer.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import com.mycompany.reservationsystem.peer.communication.CommunicationMessages;
import com.mycompany.reservationsystem.peer.data.Database;
import com.mycompany.reservationsystem.peer.data.Peer;

/*
 * Peer client spawns all peer worker threads that connect to many servers

    Begin
    Connect to DB
    Find all peers that are in an active state
    For each peer
        Spawn a client worker thread
    End
 */
public class PeerClient extends Thread{
	private static final int PORT_NUMBER = 50000;
	private Socket requestSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String ipAddress;
 	private boolean isFinished;
	
	public void run(){
		//System.out.println("PeerClient");
		Database peerTable = Database.getInstance();
		ArrayList<Peer> peersByState = peerTable.findPeersByState(Peer.STATE.ACTIVE);
		
		if(peersByState.size() != 0){
			for(Peer peer : peersByState){
				//set IP address
				//setIPAddress(peer.getPeerIpAddress());
				//find other peer from a peer
				//findPeers();
				new PeerClientWorker(peer.getPeerIpAddress()).start();
			}
		}
	}
	
	private void setIPAddress(String ipAddress){
		this.ipAddress = ipAddress;
 		this.isFinished = false;
	}
	
	private void findPeers(){
		try{
			requestSocket = new Socket(this.ipAddress, PORT_NUMBER);
			
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			while(isFinished == false){
				sendMessage(CommunicationMessages.IP_REQUEST);
				
				String message = (String) in.readObject();
				System.out.println("Got message " + message);
				if(message.startsWith(CommunicationMessages.IP_RESPONSE.toString())){
					/*
					 * Message with no ip address shows that server has given all ip address, 
					 * server will return IP_RESPONSE: (12 chars)
					 */
					if(message.length() != 12){
						String ipAddress = message.substring(message.indexOf(":")+1, message.length());
						
						Database peerTable = Database.getInstance();
						//Check if ip address is present, if ip address isn't present then will return null
						if(peerTable.findPeerByIpAddress(ipAddress) == null){
							//Add ip address to DB
							Peer newPeer = new Peer(ipAddress,Peer.STATE.INACTIVE, new Date().getTime());
							peerTable.addPeer(newPeer);
						}
					}
					else{
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
		}
		finally{
			//Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(NullPointerException e){
				
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	
	private void sendMessage(CommunicationMessages communicationMessage){
		String message = "";
		if(communicationMessage.toString().equals(CommunicationMessages.IP_REQUEST.toString())){
			message += CommunicationMessages.IP_REQUEST.toString();
		}
		
		try{
			out.writeObject(message);
			out.flush();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}
