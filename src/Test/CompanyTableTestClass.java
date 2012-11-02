package Test;

import java.util.ArrayList;

import com.mycompany.reservationsystem.peer.data.CompanyTable;
/*
 * 
 */
public class CompanyTableTestClass {
	public static void main(String[] args) {
		CompanyTable companyTable = CompanyTable.getInstance();
		companyTable.connect();
		ArrayList<String> companies = companyTable.getCompanies();
		companyTable.disconnect();
		
		for(String s : companies){
			System.out.println(s);
		}
	}
}
