package com.mycompany.reservationsystem.peer.data;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class SystemLogger {
	private static SystemLogger instance;
	private final String logPath = "data\\out.log";
	private FileHandler hand;
	private Logger log;
	
	private SystemLogger(){
		try{
			hand = new FileHandler(logPath);
			log = Logger.getLogger("log_file");
		}
		catch(IOException e){
			
		}
	}
	
	public static SystemLogger getInstance(){
		if(instance == null){
			instance = new SystemLogger();
		}
		return instance;
	}
	
	public void logWarning(String warning){
		log.addHandler(hand);
		log.warning(warning);
	}
	
	public void logInfo(String info){
		log.addHandler(hand);
		log.info(info);
	}
	
	public void logSevere(String message){
		log.addHandler(hand);
		log.severe(message);
	}
}
