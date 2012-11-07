package Test;

import com.mycompany.reservationsystem.peer.daemon.PeerStateDaemon;

public class PeerStateDeamonTestClass {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		PeerStateDaemon peerDeamon = new PeerStateDaemon();
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
