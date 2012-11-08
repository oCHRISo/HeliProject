package Test;

import com.mycompany.reservationsystem.peer.daemon.TransactionDaemon;

public class CancelTransactionDaemonTestClass {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		TransactionDaemon tD = new TransactionDaemon();
		tD.start();
		
		try {
			System.out.println("Hello");
			Thread.currentThread();
			Thread.sleep(1000 * 500);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		tD.stop();
	}
}
