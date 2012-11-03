package com.mycompany.reservationsystem.peer.client.booking;

import java.util.ArrayList;

import com.mycompany.reservationsystem.peer.data.Peer;
import com.mycompany.reservationsystem.peer.data.PeerTable;

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
		PeerTable peerTable = PeerTable.getInstance();
		peerTable.connect();
		ArrayList<Peer> peersByState = peerTable.findPeersByState(Peer.STATE.ACTIVE);
		peerTable.disconnect();
		
		for(Peer peer : peersByState){
			new Thread(new BookingClientWorker(peer.getPeerIpAddress())).start();
		}
	}
}
