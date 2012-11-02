package com.mycompany.reservationsystem.peer.data;

public class Peer {
	public enum STATE{ACTIVE, INACTIVE};
	private String peerIpAddress;
	private STATE state;
	private long epochTime;
	
	public Peer(){
		
	}
	public Peer(String peerIpAddress, STATE state, long epochTime){
		this.setPeerIpAddress(peerIpAddress);
		this.setState(state);
		this.setEpochTime(epochTime);
	}

	public String getPeerIpAddress() {
		return peerIpAddress;
	}

	public void setPeerIpAddress(String peerIpAddress) {
		this.peerIpAddress = peerIpAddress;
	}

	public STATE getState() {
		return state;
	}

	public void setState(STATE state) {
		this.state = state;
	}

	public long getEpochTime() {
		return epochTime;
	}

	public void setEpochTime(long epochTime) {
		this.epochTime = epochTime;
	}
}
