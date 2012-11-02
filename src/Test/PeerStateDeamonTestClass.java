package Test;

import com.mycompany.reservationsystem.peer.deamon.PeerStateDeamon;

public class PeerStateDeamonTestClass {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		PeerStateDeamon peerDeamon = new PeerStateDeamon();
		peerDeamon.start();
		
		try {
			System.out.println("Hello");
			Thread.currentThread();
			Thread.sleep(30000);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		peerDeamon.stop();
	}
}
