package Test;

import java.util.ArrayList;
import java.util.Date;

import com.mycompany.reservationsystem.peer.data.FlightBooking;
import com.mycompany.reservationsystem.peer.data.FlightBookingTable;

public class FlightBookingTableTestClass {
	public static void main(String[] args) {
		FlightBooking newBooking = new FlightBooking(new Date().getTime(),
				"bob@gmail.com","31/08/2012@1200","NA",false,true,0.0,FlightBooking.STATE.REQUESTED);
		
		FlightBookingTable flightTable = FlightBookingTable.getInstance();
		
		flightTable.connect();
		flightTable.addBooking(newBooking);
		ArrayList<FlightBooking> bookings = flightTable.findBookingByEmail("bob@gmail.com");
		flightTable.disconnect();
		
		for(FlightBooking flight : bookings){
			System.out.println(flight.getEmail() + " " + flight.getFlightToCityAt() + " " + flight.getState().toString());
		}
	}
}
