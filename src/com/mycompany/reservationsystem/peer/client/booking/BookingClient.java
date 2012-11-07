package com.mycompany.reservationsystem.peer.client.booking;

import java.util.ArrayList;

import com.mycompany.reservationsystem.peer.data.Database;
import com.mycompany.reservationsystem.peer.data.Peer;

/*
 * Booking client spawns all booking client worker threads that connect to many booking servers

    Begin
    Connect to DB
    Find all peers that are in an active state
    For each peer
        Spawn a booking worker thread
    End
 */
public class BookingClient extends Thread{
	public void run(){
		Database peerTable = Database.getInstance();
		
		ArrayList<Peer> peersByState = peerTable.findPeersByState(Peer.STATE.ACTIVE);
		if(peersByState.size() != 0){
			for(Peer peer : peersByState){
				new BookingClientWorker(peer.getPeerIpAddress()).start();
			}
		}
	}
}
