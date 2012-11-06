package com.mycompany.reservationsystem.peer.data;

public class FlightBooking {
public enum STATE{REQUESTED, CONFIRMED, REJECTED, CANCEL, CANCELED};
	
	private long transactionTime;
	private String email;
	private String flightToCityAt;
	private String flightToCampAt;
	private boolean fromCity;
	private boolean fromCamp;
	private double price;
	private STATE state;
	
	public FlightBooking(){
		
	}
	
	public FlightBooking(long transactionTime, String email, 
			String flightToCityAt, String flightToCampAt, boolean fromCity, 
			boolean fromCamp, double price, FlightBooking.STATE state){
		this.setTransactionTime(transactionTime);
		this.setEmail(email);
		this.setFlightToCityAt(flightToCityAt);
		this.setFlightToCampAt(flightToCampAt);
		this.setFromCity(fromCity);
		this.setFromCamp(fromCamp);
		this.setPrice(price);
		this.setState(state);
	}

	public long getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(long transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFlightToCityAt() {
		return flightToCityAt;
	}

	public void setFlightToCityAt(String flightToCity) {
		this.flightToCityAt = flightToCity;
	}

	public String getFlightToCampAt() {
		return flightToCampAt;
	}

	public void setFlightToCampAt(String flightToCamp) {
		this.flightToCampAt = flightToCamp;
	}

	public boolean isFromCity() {
		return fromCity;
	}

	public void setFromCity(boolean fromCity) {
		this.fromCity = fromCity;
	}

	public boolean isFromCamp() {
		return fromCamp;
	}

	public void setFromCamp(boolean fromCamp) {
		this.fromCamp = fromCamp;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public STATE getState() {
		return state;
	}

	public void setState(STATE state) {
		this.state = state;
	}
}
