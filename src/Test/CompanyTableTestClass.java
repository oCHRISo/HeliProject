package Test;

import java.util.ArrayList;

import com.mycompany.reservationsystem.peer.data.Database;
/*
 * 
 */
public class CompanyTableTestClass {
	public static void main(String[] args) {
		Database companyTable = Database.getInstance();
		companyTable.connect();
		ArrayList<String> companies = companyTable.getCompanies();
		companyTable.disconnect();
		
		for(String s : companies){
			System.out.println(s);
		}
	}
}
