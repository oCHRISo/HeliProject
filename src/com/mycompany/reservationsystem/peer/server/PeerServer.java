package com.mycompany.reservationsystem.peer.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PeerServer extends Thread {
	private static final int PORT_NUMBER = 50000;
	private ServerSocket serverSocket;
	
	public void run(){
		while(true){
			try {
				serverSocket = new ServerSocket(PORT_NUMBER);
				Socket connection = serverSocket.accept();
				new Thread(new PeerServerWorker(connection)).start();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				//Closing connection
				try{
					serverSocket.close();
				}
				catch(IOException ioException){
					ioException.printStackTrace();
				}
			}
		}
	}
}
