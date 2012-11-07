package com.mycompany.reservationsystem.peer.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
	
	public ArrayList<String> getCompaniesIpAddresses(){
		return parseIpAddresses();
	}
	
	//Sample data = 192.168.1.1,192.168.1.2,192.168.1.3,
 	private ArrayList<String> parseIpAddresses(){
 		String ipAddresses = prop.getProperty("CompanyServerIPAddresses");
 		
 		int numberOfIpAddresses = 0;
 		for(int i = 0; i < ipAddresses.length(); i++){
 			if(ipAddresses.charAt(i) == ','){
 				numberOfIpAddresses++;
 			}
 		}
 		
 		ArrayList<String> listOfIpAddresses = new ArrayList<String>();
 		do{
 			//extract ip address and add to list
 			String ip = ipAddresses.substring(0, ipAddresses.indexOf(","));
 			listOfIpAddresses.add(ip);
 			//Move up to next ip address
 			ipAddresses = ipAddresses.substring(ipAddresses.indexOf(",")+1);
 			numberOfIpAddresses--;
 		}while(numberOfIpAddresses > 0);
 		
 		return listOfIpAddresses;
 	}
}
