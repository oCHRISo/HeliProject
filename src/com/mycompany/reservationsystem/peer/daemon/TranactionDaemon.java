package com.mycompany.reservationsystem.peer.daemon;

import java.util.ArrayList;
import java.util.Date;

import com.mycompany.reservationsystem.peer.data.Database;
import com.mycompany.reservationsystem.peer.data.FlightBooking;
import com.mycompany.reservationsystem.peer.data.PropertyFile;

public class TranactionDaemon extends Thread {
	private final int EPOCH_MINUTE = 60;
	
	public void run(){
		while(true){
			try {
				sleep(60000 * PropertyFile.getInstance().getExecuteTranactionDaemonTime());
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			long currentEpoch = new Date().getTime();
			long period = PropertyFile.getInstance().getTransactionTimePeriod() * EPOCH_MINUTE;
			long startOfPeriod = currentEpoch - (period*2);
			long endOfPeriod = currentEpoch - period;
			
			ArrayList<FlightBooking> timePeriodBookings = Database.getInstance().findBooking(startOfPeriod, endOfPeriod);
			
			
			ArrayList<FlightBooking> cancelTransactions = findBookingsByState(timePeriodBookings, FlightBooking.STATE.CANCEL);
			processCancelTransactions(cancelTransactions);
			
			
			
			//TODO transactions to commit
			
			//Do CANCEL transactions first
		}
	}
	
	private ArrayList<FlightBooking> findBookingsByState(ArrayList<FlightBooking> timePeriodBookings, FlightBooking.STATE state){
		ArrayList<FlightBooking> bookings = new ArrayList<FlightBooking>();
		
		for(FlightBooking flightBooking : timePeriodBookings){
			if(flightBooking.getState().toString().equals(state.toString())){
				bookings.add(flightBooking);
			}
		}
		
		return bookings;
	}
	
	private void processCancelTransactions(ArrayList<FlightBooking> cancelTransactions){
		/*
		 * 
		 */
	}
}
