package Test.TestClient;

import com.mycompany.reservationsystem.peer.client.PeerClient;
import com.mycompany.reservationsystem.peer.client.booking.BookingClient;
import com.mycompany.reservationsystem.peer.daemon.PeerStateDaemon;

public class ClientTestClass {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		PeerStateDaemon peerDeamon = new PeerStateDaemon();
		PeerClient peerClient = new PeerClient();
		BookingClient bookingClient = new BookingClient();
		
		peerDeamon.setDaemon(true);
		peerDeamon.start();
		peerClient.start();
		bookingClient.start();
		
		try {
			System.out.println("Hello");
			Thread.currentThread();
			Thread.sleep(1000 * 500);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		peerDeamon.stop();
		peerClient.stop();
		bookingClient.stop();
	}
}
