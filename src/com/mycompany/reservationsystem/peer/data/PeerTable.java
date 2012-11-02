package com.mycompany.reservationsystem.peer.data;
import java.sql.SQLException;

public class PeerTable extends Database {
	private static PeerTable instance = null;
	
    private PeerTable(){
    	
    }
    
    public static PeerTable getInstance(){
    	if(instance != null){
    		return instance;
    	}
    	else{
    		instance = new PeerTable();
    		return instance;
    	}
    }

    public synchronized void addPeer(Peer peer){
    	try {  
           statement.executeUpdate("INSERT INTO Peers (peer_address, active, last_updated) VALUES ('" + peer.getPeerIpAddress() + 
        		   "', '" + peer.getState().toString() + "', '" + peer.getEpochTime() + "');");  
        } 
    	catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    
    public synchronized void updatePeer(Peer peer){
    	try {
			statement.executeUpdate("UPDATE Peers SET active='" + peer.getState().toString() + "', last_updated='" + peer.getEpochTime() +
					"' WHERE peer_address='" + peer.getPeerIpAddress() + "'");
		} 
    	catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public synchronized Peer findPeerByIpAddress(String ipAddress){
    	try {
    		resultSet = statement.executeQuery("SELECT * FROM Peers WHERE peer_address='" + ipAddress + "'");
    		
    		while(resultSet.next()){
    			Peer peer = new Peer();
        		peer.setPeerIpAddress(resultSet.getString("peer_address"));
        		
        		if(resultSet.getInt("active") == 1)
        		{
        			peer.setState(Peer.STATE.ACTIVE);
        		}
        		else{
        			peer.setState(Peer.STATE.INACTIVE);
        		}
        		peer.setEpochTime(resultSet.getLong("last_updated"));
        		
        		return peer;
    		}
    	}
    	catch (Exception e) {  
        e.printStackTrace();
        }
    	return null;
    }   
}
