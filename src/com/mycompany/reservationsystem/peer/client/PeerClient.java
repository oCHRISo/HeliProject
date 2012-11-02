package com.mycompany.reservationsystem.peer.client;

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
public class PeerClient extends Thread {

	public void run(){
		
	}
}