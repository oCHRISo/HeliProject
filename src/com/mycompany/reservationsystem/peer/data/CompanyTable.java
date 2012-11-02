package com.mycompany.reservationsystem.peer.data;

import java.util.ArrayList;

public class CompanyTable extends Database{
	private static CompanyTable instance = null;
	
	private CompanyTable(){
    	
    }
    
    public static CompanyTable getInstance(){
    	if(instance != null){
    		return instance;
    	}
    	else{
    		instance = new CompanyTable();
    		return instance;
    	}
    }
    
    public synchronized ArrayList<String> getCompanies(){
    	try {
    		resultSet = statement.executeQuery("SELECT * FROM companies");
    		ArrayList<String> companies = new ArrayList<String>();
    		
    		while(resultSet.next()){
    			companies.add(resultSet.getString("company_name"));
    		}
    		return companies;
    	}
    	catch (Exception e) {  
        e.printStackTrace();
        }
    	return null;
    } 
}
