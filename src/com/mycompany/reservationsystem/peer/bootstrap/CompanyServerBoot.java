package com.mycompany.reservationsystem.peer.bootstrap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mycompany.reservationsystem.peer.daemon.PeerStateDaemon;
import com.mycompany.reservationsystem.peer.daemon.TransactionDaemon;
import com.mycompany.reservationsystem.peer.server.PeerServer;
import com.mycompany.reservationsystem.peer.server.booking.BookingServer;

public class CompanyServerBoot {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		PeerStateDaemon peerDeamon = new PeerStateDaemon();
		TransactionDaemon transaction = new TransactionDaemon();
		PeerServer peerServer = new PeerServer();
		BookingServer bookingServer = new BookingServer();
		
		transaction.setDaemon(true);
		peerDeamon.setDaemon(true);
		transaction.start();
		peerDeamon.start();
		peerServer.start();
		bookingServer.start();
		
		String userInput = null;
		do{
			System.out.println("Enter 1 to stop the program:");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			try {
				userInput = br.readLine();
		    } 
			catch (IOException ioe) {
				System.out.println("IO error trying to read your input!");
		    }
		}while(!userInput.equals("1"));
		
		peerServer.close();
		bookingServer.close();
		peerServer.stop();
		bookingServer.stop();
		peerDeamon.stop();
		transaction.stop();
	}
}
