package Test;

import com.mycompany.reservationsystem.peer.client.booking.BookingClient;

public class BookingClientTestClass {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		BookingClient bookingClient = new BookingClient();
		bookingClient.start();
		
		try {
			System.out.println("Hello");
			Thread.currentThread();
			Thread.sleep(100000);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		bookingClient.stop();
	}
}
