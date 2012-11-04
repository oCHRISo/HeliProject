package Test.TestClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mycompany.reservationsystem.peer.client.PeerClient;
import com.mycompany.reservationsystem.peer.client.booking.BookingClient;
import com.mycompany.reservationsystem.peer.data.Database;
import com.mycompany.reservationsystem.peer.deamon.PeerStateDeamon;

public class ClientTestClass {
	public static void main(String[] args) {
		Database.getInstance().connect();
		PeerStateDeamon peerDeamon = new PeerStateDeamon();
		PeerClient peerClient = new PeerClient();
		BookingClient bookingClient = new BookingClient();
		
		ExecutorService pool = Executors.newFixedThreadPool(3);
		pool.execute(peerDeamon);
		pool.execute(peerClient);
		pool.execute(bookingClient);
		
		try {
			System.out.println("Hello");
			Thread.currentThread();
			Thread.sleep(1000 * 500);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		pool.shutdown();
		Database.getInstance().disconnect();
	}
}
