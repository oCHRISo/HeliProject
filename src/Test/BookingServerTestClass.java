package Test;

import com.mycompany.reservationsystem.peer.server.booking.BookingServer;

public class BookingServerTestClass {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		BookingServer bookingServer = new BookingServer();
		bookingServer.start();
		
		try {
			System.out.println("Hello");
			Thread.currentThread();
			Thread.sleep(100000);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		bookingServer.stop();
		bookingServer.close();
	}
}
