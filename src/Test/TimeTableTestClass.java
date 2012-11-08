package Test;

import java.util.ArrayList;

import com.mycompany.reservationsystem.peer.data.Database;
import com.mycompany.reservationsystem.peer.data.FlightTime;

/*
 * 
 */
public class TimeTableTestClass {
	public static void main(String[] args) {
		Database timeTable = Database.getInstance();
		timeTable.connect();
		ArrayList<FlightTime> times = timeTable.getTimes();
		timeTable.disconnect();
		
		for(FlightTime i : times){
			System.out.println(i.getFlightTime());
		}
	}
}
