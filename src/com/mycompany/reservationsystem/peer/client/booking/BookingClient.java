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
		while(true){
			System.out.println("BookingClient");
			PeerTable peerTable = PeerTable.getInstance();
			yield();
			peerTable.connect();
			ArrayList<Peer> peersByState = peerTable.findPeersByState(Peer.STATE.ACTIVE);
			peerTable.disconnect();
			yield();
			for(Peer peer : peersByState){
				BookingClientWorker bookingWorker = new BookingClientWorker(peer.getPeerIpAddress());
				bookingWorker.setPriority(MAX_PRIORITY);
				new Thread(bookingWorker).start();
				yield();
			}
			yield();
		}
	}
}
