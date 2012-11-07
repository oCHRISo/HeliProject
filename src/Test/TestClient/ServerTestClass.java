package Test.TestClient;

import com.mycompany.reservationsystem.peer.daemon.PeerStateDaemon;
import com.mycompany.reservationsystem.peer.server.PeerServer;
import com.mycompany.reservationsystem.peer.server.booking.BookingServer;

public class ServerTestClass {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		PeerStateDaemon peerDeamon = new PeerStateDaemon();
		PeerServer peerServer = new PeerServer();
		BookingServer bookingServer = new BookingServer();
        
		peerDeamon.setDaemon(true);
		peerDeamon.start();
		peerServer.start();
		bookingServer.start();
		
		try {
			System.out.println("Hello");
			Thread.currentThread();
			Thread.sleep(1000 * 500);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		peerServer.close();
		bookingServer.close();
		peerDeamon.stop();
		peerServer.stop();
		bookingServer.stop();
	}
}
