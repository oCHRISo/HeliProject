package Test;

import java.util.ArrayList;

import com.mycompany.reservationsystem.peer.data.TimeTable;

/*
 * 
 */
public class TimeTableTestClass {
	public static void main(String[] args) {
		TimeTable timeTable = TimeTable.getInstance();
		timeTable.connect();
		ArrayList<Integer> times = timeTable.getTimes();
		timeTable.disconnect();
		
		for(Integer i : times){
			System.out.println(i);
		}
	}
}
