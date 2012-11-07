package com.mycompany.reservationsystem.peer.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertieFile {
	private Properties prop;
	private final String propFilePath = "data\\config.properties";
	
	public PropertieFile(){
		prop = new Properties();
		try {
			//load a properties file
			prop.load(new FileInputStream(propFilePath));
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	//get the property value and return
	public String getCompanyNumber(){
		return prop.getProperty("CompanyNumber");
	}
	
	//get the property value and return
	public String getTimeToWait(){
		return prop.getProperty("TimeToWait");
	}
}
