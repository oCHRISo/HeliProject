package com.mycompany.reservationsystem.peer.client;

import java.util.ArrayList;

import com.mycompany.reservationsystem.peer.data.Peer;
import com.mycompany.reservationsystem.peer.data.PeerTable;

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
		PeerTable peerTable = PeerTable.getInstance();
		peerTable.connect();
		ArrayList<Peer> peersByState = peerTable.findPeersByState(Peer.STATE.ACTIVE);
		peerTable.disconnect();
		
		for(Peer peer : peersByState){
			new Thread(new PeerClientWorker(peer.getPeerIpAddress())).start();
		}
	}
}
