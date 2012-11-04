package com.mycompany.reservationsystem.peer.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Peer server thread giving known ip address to clients

    Begin
    Wait for client to connect
    Client requests for an ip
    Find all known ip addresses at given point in time
    Send an ip address to client
    Wait for client to ask for another
    Repeat step 5 and 6 until given all ip addresses
    When all ips are given send blank message
    Client then disconnects
    End
 */
public class PeerServer extends Thread {
	private static final int PORT_NUMBER = 50000;
	private ServerSocket serverSocket;
	private ExecutorService pool = Executors.newFixedThreadPool(50);
	
	public void run(){
		while(true){
			try {
				serverSocket = new ServerSocket(PORT_NUMBER);
				Socket connection = serverSocket.accept();
				//Spawn new thread for client
				pool.execute(new PeerServerWorker(connection));
			} 
			catch (IOException e) {
				//e.printStackTrace();
			}
			finally{
				//Closing connection
				try{
					pool.shutdown();
					serverSocket.close();
				}
				catch(IOException ioException){
					//ioException.printStackTrace();
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
			//e.printStackTrace();
		} 
		catch (IOException e) {
			//e.printStackTrace();
		}
	}
}
