package com.mycompany.reservationsystem.peer.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFile {
	private static PropertyFile instance = null;
	private Properties prop;
	private final String propFilePath = "data\\config.properties";
	
	private PropertyFile(){
		prop = new Properties();
		try {
			//load a properties file
			prop.load(new FileInputStream(propFilePath));
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static PropertyFile getInstance(){
		if(instance == null){
			instance = new PropertyFile();
		}
		
		return instance;
	}
	
	//get the property value and return
	public int getPeerStateDeamonTime(){
		return Integer.parseInt(prop.getProperty("ExecutePeerStateDeamon"));
	}
	
	public int getExecuteTranactionDaemonTime(){
		return Integer.parseInt(prop.getProperty("ExecuteTranactionDaemon"));
	}
	
	public int getTransactionTimePeriod(){
		return Integer.parseInt(prop.getProperty("TransactionTimePeriod"));
	}
	
	public int getDuplicatesDaemonTime(){
		return Integer.parseInt(prop.getProperty("DuplicatesDaemonTime"));
	}
}
