package com.mycompany.reservationsystem.peer.deamon;

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
public class PeerStateDeamon extends Thread{
	public PeerStateDeamon(){
		
	}
	
	public void run(){
		
	}
}
