package Test;

import com.mycompany.reservationsystem.peer.server.PeerServer;

public class PeerSeverTestClass {
	@SuppressWarnings("deprecation")
	//Pass in number of milli seconds to keep peer server open
	public static void main(String[] args) {
		PeerServer peerServer = new PeerServer();
		peerServer.start();
		
		try {
			System.out.println("Hello");
			Thread.currentThread();
			Thread.sleep(Integer.parseInt(args[0]));
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		peerServer.stop();
		peerServer.close();
	}
}