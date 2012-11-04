package com.mycompany.reservationsystem.peer.server.booking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookingServer extends Thread{
	private static final int PORT_NUMBER = 50001;
	private ServerSocket serverSocket;
	private ExecutorService pool = Executors.newFixedThreadPool(50);
	
	public void run(){
		while(true){
			try {
				serverSocket = new ServerSocket(PORT_NUMBER);
				Socket connection = serverSocket.accept();
				//Spawn new thread for client
				pool.equals(new BookingServerWorker(connection));
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				//Closing connection
				try{
					pool.shutdown();
					serverSocket.close();
				}
				catch(IOException ioException){
					ioException.printStackTrace();
				}
			}
		}
	}
	
	//To unblock the serverSocket.accept() method, safely closing down the server
	public void close(){
		try {
			Socket socket = new Socket("127.0.0.1",PORT_NUMBER);
			socket.close();
		} 
		catch (UnknownHostException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
