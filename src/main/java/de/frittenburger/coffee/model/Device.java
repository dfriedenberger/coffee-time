package de.frittenburger.coffee.model;

import de.frittenburger.geo.model.TrackPoint;

public class Device {

	private final String id;
	private TrackPoint trackPoint = null;
	private String adress = "unknown";

	public Device(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public TrackPoint getTrackPoint() {
		return trackPoint;
	}

	public void setTrackPoint(TrackPoint trackPoint) {
		this.trackPoint = trackPoint;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}


	
	
	
}