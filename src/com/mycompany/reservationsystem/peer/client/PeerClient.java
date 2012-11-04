package com.mycompany.reservationsystem.peer.client;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	private ExecutorService pool = Executors.newFixedThreadPool(10);
	
	public void run(){
		while(true){
			//System.out.println("PeerClient");
			Database peerTable = Database.getInstance();
			peerTable.connect();
			ArrayList<Peer> peersByState = peerTable.findPeersByState(Peer.STATE.ACTIVE);
			peerTable.disconnect();
			
			if(peersByState.size() != 0){
				try{
					for(Peer peer : peersByState){
						pool.execute(new PeerClientWorker(peer.getPeerIpAddress()));
					}
				}
				catch(NullPointerException e){
					
				}
			}
			else{
				try {
					sleep(new Random().nextInt(500));
				} 
				catch (InterruptedException e) {
					//e.printStackTrace();
				}
			}
			yield();
		}
	}
}
