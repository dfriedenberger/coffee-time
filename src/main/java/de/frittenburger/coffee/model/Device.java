package de.frittenburger.coffee.model;

import de.frittenburger.coffee.impl.TrackPointCluster;

public class Device {

	private final String id;
	private TrackPointCluster trackPointCluster = null;
	private String action = "unknown";

	public Device(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public TrackPointCluster getTrackPointCluster() {
		return trackPointCluster;
	}

	public void setTrackPointCluster(TrackPointCluster trackPointCluster) {
		this.trackPointCluster = trackPointCluster;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}