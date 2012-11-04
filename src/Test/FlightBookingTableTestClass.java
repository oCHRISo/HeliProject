package Test;

import java.util.ArrayList;
import java.util.Date;

import com.mycompany.reservationsystem.peer.data.Database;
import com.mycompany.reservationsystem.peer.data.FlightBooking;

public class FlightBookingTableTestClass {
	public static void main(String[] args) {
		FlightBooking newBooking = new FlightBooking(new Date().getTime(),
				"bob@gmail.com","31/08/2012@1200","NA",false,true,0.0,FlightBooking.STATE.REQUESTED);
		
		Database flightTable = Database.getInstance();
		
		flightTable.connect();
		flightTable.addBooking(newBooking);
		ArrayList<FlightBooking> bookings = flightTable.getAllBookings();
		flightTable.disconnect();
		
		for(FlightBooking flight : bookings){
			System.out.println(flight.getEmail() + " " + flight.getFlightToCityAt() + " " + flight.getState().toString());
		}
	}
}
