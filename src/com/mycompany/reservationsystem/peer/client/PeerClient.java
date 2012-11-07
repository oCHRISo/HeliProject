package com.mycompany.reservationsystem.peer.client;

import java.util.ArrayList;

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
	public void run(){
		//System.out.println("PeerClient");
		Database peerTable = Database.getInstance();
		ArrayList<Peer> peersByState = peerTable.findPeersByState(Peer.STATE.ACTIVE);
		
		if(peersByState.size() != 0){
			for(Peer peer : peersByState){
				new PeerClientWorker(peer.getPeerIpAddress()).start();
			}
		}
	}
}
