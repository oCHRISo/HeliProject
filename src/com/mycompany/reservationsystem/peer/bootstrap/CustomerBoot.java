package com.mycompany.reservationsystem.peer.bootstrap;

import com.mycompany.reservationsystem.peer.daemon.PeerStateDaemon;
import com.mycompany.reservationsystem.peer.server.PeerServer;
import com.mycompany.reservationsystem.peer.server.booking.BookingServer;
import com.mycompany.reservationsystem.peer.ui.CustomerUserInterface;

public class CustomerBoot {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		PeerStateDaemon peerDeamon = new PeerStateDaemon();
		PeerServer peerServer = new PeerServer();
		BookingServer bookingServer = new BookingServer();
		
		peerDeamon.setDaemon(true);
		peerDeamon.start();
		peerServer.start();
		bookingServer.start();
		
		CustomerUserInterface cui = new CustomerUserInterface();
		cui.showOptions();
		
		peerServer.close();
		bookingServer.close();
		peerServer.stop();
		bookingServer.stop();
		peerDeamon.stop();
	}
}
