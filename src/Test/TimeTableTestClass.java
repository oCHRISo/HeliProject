package Test;

import java.util.ArrayList;

import com.mycompany.reservationsystem.peer.data.Database;

/*
 * 
 */
public class TimeTableTestClass {
	public static void main(String[] args) {
		Database timeTable = Database.getInstance();
		timeTable.connect();
		ArrayList<Integer> times = timeTable.getTimes();
		timeTable.disconnect();
		
		for(Integer i : times){
			System.out.println(i);
		}
	}
}
