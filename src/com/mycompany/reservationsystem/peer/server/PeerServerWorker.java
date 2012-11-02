package com.mycompany.reservationsystem.peer.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.mycompany.reservationsystem.peer.communication.COMMUNICATION_MESSAGES;

public class PeerServerWorker extends Thread{
	private Socket connection;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean isFinished;
	
	public PeerServerWorker(Socket socket){
		this.connection = socket;
		this.isFinished = false;
	}

	public void run(){
		
		
		try{
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			
			String message = null;
			message = (String)in.readObject();
			System.out.println(message);
			
			while(isFinished() == false){
				if(message.equals(COMMUNICATION_MESSAGES.IP_REQUEST.toString())){
					sendMessage(COMMUNICATION_MESSAGES.IP_RESPONSE);
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			//Closing connection
			try{
				in.close();
				out.close();
				connection.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	
	//Send message to client
	private void sendMessage(COMMUNICATION_MESSAGES communicationMessage){
		if(communicationMessage.equals(COMMUNICATION_MESSAGES.IP_RESPONSE)){
			String message = "";
			message += COMMUNICATION_MESSAGES.IP_RESPONSE.toString() + ":" + ;
			System.out.println(message);
			
			try{
				out.writeObject(message);
				out.flush();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
		else if(communicationMessage.equals(COMMUNICATION_MESSAGES.IP_RESPONSE)){
			
		}
	}
	
	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
}
