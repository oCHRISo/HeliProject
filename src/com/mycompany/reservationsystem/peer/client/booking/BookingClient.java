package com.mycompany.reservationsystem.peer.client.booking;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	private ExecutorService pool = Executors.newFixedThreadPool(10);
	
	public void run(){
		while(true){
			PeerTable peerTable = PeerTable.getInstance();
			peerTable.connect();
			ArrayList<Peer> peersByState = peerTable.findPeersByState(Peer.STATE.ACTIVE);
			peerTable.disconnect();
			
			for(Peer peer : peersByState){
				pool.execute(new BookingClientWorker(peer.getPeerIpAddress()));
			}
			yield();
		}
	}
}
