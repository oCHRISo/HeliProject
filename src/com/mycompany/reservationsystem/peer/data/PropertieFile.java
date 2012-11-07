package com.mycompany.reservationsystem.peer.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertieFile {
	private static PropertieFile instance = null;
	private Properties prop;
	private final String propFilePath = "data\\config.properties";
	
	private PropertieFile(){
		prop = new Properties();
		try {
			//load a properties file
			prop.load(new FileInputStream(propFilePath));
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static PropertieFile getInstance(){
		if(instance == null){
			instance = new PropertieFile();
		}
		
		return instance;
	}
	
	//get the property value and return
	public int getPeerStateDeamonTime(){
		return Integer.parseInt(prop.getProperty("PeerStateDeamon"));
	}
}
