package de.frittenburger.coffee.model;

import java.util.ArrayList;
import java.util.List;

import de.frittenburger.geo.model.TrackPoint;

public class TrackPointCluster {

	public static final String staying = "staying";
	public static final String moving = "moving";
	
	private final List<TrackPoint> trackPoints = new ArrayList<>();
	private String type = "unknown";
	
	public TrackPointCluster(TrackPoint tp) {
		trackPoints.add(tp);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TrackPoint getLast() {
		return trackPoints.get(trackPoints.size() -1);
	}
	
	public TrackPoint getFirst() {
		return trackPoints.get(0);
	}

	public int size() {
		return trackPoints.size();
	}

	public void add(TrackPoint tp) {
		trackPoints.add(tp);
	}

	public void addAll(List<TrackPoint> tps) {
		trackPoints.addAll(tps);
	}
	public List<TrackPoint> getAll() {
		return trackPoints;
	}

	
}
