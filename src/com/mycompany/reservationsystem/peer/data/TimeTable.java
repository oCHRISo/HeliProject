package com.mycompany.reservationsystem.peer.data;

import java.util.ArrayList;

public class TimeTable extends Database{
	private static TimeTable instance = null;
	
	private TimeTable(){
    	
    }
    
    public static TimeTable getInstance(){
    	if(instance != null){
    		return instance;
    	}
    	else{
    		instance = new TimeTable();
    		return instance;
    	}
    }
    
    public synchronized ArrayList<Integer> getTimes(){
    	try {
    		resultSet = statement.executeQuery("SELECT * FROM timetable");
    		ArrayList<Integer> times = new ArrayList<Integer>();
    		
    		while(resultSet.next()){
    			times.add(Integer.valueOf(resultSet.getInt("flight_time")));
    		}
    		return times;
    	}
    	catch (Exception e) {  
        e.printStackTrace();
        }
    	return null;
    }   
}
