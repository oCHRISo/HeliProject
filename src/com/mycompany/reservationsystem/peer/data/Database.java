package com.mycompany.reservationsystem.peer.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
	protected Connection connection = null;  
	protected ResultSet resultSet = null;  
    protected Statement statement = null;
    protected static final String connectionString = "jdbc:sqlite:data\\peer.db";
    
    public synchronized void connect(){
    	try {
    		//Loading class
			Class.forName("org.sqlite.JDBC");
		    connection = DriverManager.getConnection(connectionString);  
		    statement = connection.createStatement();
		    
    	} 
    	catch (Exception e) {  
    		e.printStackTrace();  
    	}
    }
    
    public synchronized void disconnect(){
    	try {  
            if(resultSet != null){
            	resultSet.close();
            }
            statement.close();  
            connection.close();  
        } 
    	catch (Exception e) {  
            e.printStackTrace();  
        }
    }
}
