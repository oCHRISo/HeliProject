package com.mycompany.reservationsystem.peer.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CustomerUserInterface extends Thread{
	
	
	public void run(){
		System.out.println("Please select an option:\n1 For adding a booking\n2 For canceling a booking\n3 To view booking\n4 To exit");
		String option = readLine();
		
		boolean keepRunning = true;
		
		do{
			if(option.equals("1")){
				addBookingOption();
			}
			else if(option.equals("2")){
				cancelBookingOption();
			}
			else if(option.equals("3")){
				viewBookingOption();
			}
			else if(option.equals("4")){
				keepRunning = false;
			}
			else{
				System.out.println("Uknown option");
			}
		}while(keepRunning);
		
	}
	
	private String readLine(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String userInput = null;
		try {
			userInput = br.readLine();
	    } 
		catch (IOException ioe) {
			System.out.println("IO error trying to read your input!");
	    }
		return userInput;
	}
	
	private void addBookingOption(){
		System.out.println("Please enter in your email address:");
		String email = readLine();
		
		System.out.println("Please enter in your date you wish to travel (Format dd/mm/yyyy):");
		String date = readLine();
		
		System.out.println("Please enter in your time you wish to fly (Format 24 hour, all fights leave on hour or half of the hour):");
		String time = readLine();
		
		System.out.println("Please enter where you would like to fly to:\1 For the city\n2 For the camp");
		String toOption = readLine();
		
		System.out.println("Please enter where you are flying from:\1 From the city\n2 From the camp");
		String fromOption = readLine();
		
		//TODO Add the booking to the database and tell user it is added
		
	}
	
	private void cancelBookingOption(){
		System.out.println("Please enter in your email address:");
		String email = readLine();
		
		//TODO Show all confirmed bookings for the email address
		
		System.out.println("Please select one of the confirmed bookings that you wish to cancel:");
		String calcelOption = readLine();
		
		//TODO Add transaction to cancel the booking
	}
	
	private void viewBookingOption(){
		System.out.println("Please enter in your email address:");
		
		String email = readLine();
		
		//TODO Seach all transaction for email address
	}
}
