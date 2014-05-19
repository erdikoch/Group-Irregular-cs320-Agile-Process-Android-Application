package com.groupIrregular.wap.background;

import java.sql.Timestamp;

public class Location {
	
	private String username;
	private double latitude;
	private double longitude;
	private Timestamp updatingTime;
	
	public Location() {}

	public Location(String username, double latitude, double longitude) {
		this.username = username;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getUsername() {
		return username;
	}

	public void setUsernamr(String username) {
		this.username = username;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Timestamp getUpdatingTime() {
		return updatingTime;
	}

	public void setUpdatingTime(Timestamp updatingTime) {
		this.updatingTime = updatingTime;
	}
}
