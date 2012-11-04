package Test;

import java.util.Date;

import com.mycompany.reservationsystem.peer.data.Database;
import com.mycompany.reservationsystem.peer.data.Peer;

/*
 * 
 */
public class PeerTableTestClass {
	public static void main(String args[]){
		//May need to change if ip address is already in database
		String ipAddress = "192.168.1.1";
		
		Peer newPeer = new Peer(ipAddress, Peer.STATE.ACTIVE, new Date().getTime());
		
		Database peerTable = Database.getInstance();
		peerTable.connect();
		peerTable.addPeer(newPeer);
		
		newPeer.setState(Peer.STATE.INACTIVE);
		
		peerTable.updatePeer(newPeer);
		
		Peer findPeer = new Peer();
		findPeer.setPeerIpAddress(ipAddress);
		
		Peer foundPeer = peerTable.findPeerByIpAddress(ipAddress);
		peerTable.disconnect();
		
		System.out.println(foundPeer.getPeerIpAddress() + " " + foundPeer.getState().toString() +
				" " + foundPeer.getEpochTime());
    }
}
