package Test.TestClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mycompany.reservationsystem.peer.client.PeerClient;
import com.mycompany.reservationsystem.peer.client.booking.BookingClient;
import com.mycompany.reservationsystem.peer.deamon.PeerStateDeamon;
import com.mycompany.reservationsystem.peer.server.PeerServer;
import com.mycompany.reservationsystem.peer.server.booking.BookingServer;

public class PeerTestClass {
	public static void main(String args[]){
		PeerStateDeamon peerDeamon = new PeerStateDeamon();
		PeerClient peerClient = new PeerClient();
		BookingClient bookingClient = new BookingClient();
		PeerServer peerServer = new PeerServer();
		BookingServer bookingServer = new BookingServer();
		
		ExecutorService pool = Executors.newFixedThreadPool(5);
		pool.execute(peerDeamon);
		pool.execute(peerClient);
		pool.execute(bookingClient);
		pool.execute(peerServer);
		pool.execute(bookingServer);
		
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
		pool.shutdown();
	}
}
