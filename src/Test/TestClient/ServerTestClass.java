package Test.TestClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mycompany.reservationsystem.peer.deamon.PeerStateDeamon;
import com.mycompany.reservationsystem.peer.server.PeerServer;
import com.mycompany.reservationsystem.peer.server.booking.BookingServer;

public class ServerTestClass {
	public static void main(String[] args) {
		PeerStateDeamon peerDeamon = new PeerStateDeamon();
		PeerServer peerServer = new PeerServer();
		BookingServer bookingServer = new BookingServer();
        
		ExecutorService pool = Executors.newFixedThreadPool(3);
		pool.execute(peerDeamon);
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
