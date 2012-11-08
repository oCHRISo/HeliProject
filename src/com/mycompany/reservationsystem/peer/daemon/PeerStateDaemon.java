package com.mycompany.reservationsystem.peer.daemon;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;

import com.mycompany.reservationsystem.peer.client.PeerClient;
import com.mycompany.reservationsystem.peer.client.booking.BookingClient;
import com.mycompany.reservationsystem.peer.data.Database;
import com.mycompany.reservationsystem.peer.data.Peer;
import com.mycompany.reservationsystem.peer.data.PropertyFile;

/*
 * Thread to maintain peer states

    Begin
    Connect to database
    Get all inactive ip address
    Disconnect from database
    For each ip address
        If ip address is active
        then connect to DB and set ip address to active
        Disconnect from ip address
    Loop until overall program exits
    End
 */
public class PeerStateDaemon extends Thread{
	private final int timeout = 1500;
	
	public void run(){
		while(true){
			Database peerTable = Database.getInstance();
			ArrayList<Peer> peersByState = peerTable.findPeersByState(Peer.STATE.INACTIVE);
			for(Peer peer : peersByState){
				try {
					if(InetAddress.getByName(peer.getPeerIpAddress().trim()).isReachable(timeout)){
						peer.setState(Peer.STATE.ACTIVE);
						peer.setEpochTime(new Date().getTime());
						peerTable.updatePeer(peer);
					}
				} 
				catch(NullPointerException e){
					//e.printStackTrace();
				}
				catch (Exception e) {
					//e.printStackTrace();
				}
			}
			
			new PeerClient().start();
			new BookingClient().start();
			
			try {
				sleep(1000 * PropertyFile.getInstance().getPeerStateDeamonTime());
			} 
			catch (InterruptedException e) {
				
			}
		}
	}
}