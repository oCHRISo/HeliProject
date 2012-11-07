package Test;

import java.util.ArrayList;

import com.mycompany.reservationsystem.peer.data.PropertieFile;

public class PropertiesFileTestClass {
	public static void main(String[] args) {
		PropertieFile pF = new PropertieFile();
		System.out.println(pF.getCompanyNumber());
		System.out.println(pF.getTimeToWait());
		ArrayList<String> companiesIpAddresses = pF.getCompaniesIpAddresses();
		
		for(String ip : companiesIpAddresses){
			System.out.println(ip);
		}
		
	}
}
