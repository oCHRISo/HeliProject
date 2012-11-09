package com.mycompany.reservationsystem.peer.daemon;

import com.mycompany.reservationsystem.peer.data.Database;
import com.mycompany.reservationsystem.peer.data.PropertyFile;

public class RemoveDuplicatesDaemon extends Thread{
	
	public void run(){
		try {
			sleep(60000 * PropertyFile.getInstance().getDuplicatesDaemonTime());
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		Database.getInstance().removeDuplicateBookings();
	}
}
