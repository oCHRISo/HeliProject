package Test;

import com.mycompany.reservationsystem.peer.client.PeerClient;

public class PeerClientTestClass {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		PeerClient peerClient = new PeerClient();
		peerClient.start();
		
		try {
			System.out.println("Hello");
			Thread.currentThread();
			Thread.sleep(100000);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		peerClient.stop();
	}
}