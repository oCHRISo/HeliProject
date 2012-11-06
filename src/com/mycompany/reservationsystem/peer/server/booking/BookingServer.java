package com.mycompany.reservationsystem.peer.server.booking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class BookingServer extends Thread{
	private static final int PORT_NUMBER = 50001;
	private ServerSocket serverSocket;
	
	public void run(){
		while(true){
			try {
				serverSocket = new ServerSocket(PORT_NUMBER);
				Socket connection = serverSocket.accept();
				//Spawn new thread for client
				new BookingServerWorker(connection).start();
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
