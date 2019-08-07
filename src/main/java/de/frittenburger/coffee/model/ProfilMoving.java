package de.frittenburger.coffee.model;

import de.frittenburger.geo.model.GeoPoint;


public class ProfilMoving extends ProfilLink {
	
	private GeoPoint startPoint;
	private GeoPoint endPoint;
	private double distance;
	
	public GeoPoint getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(GeoPoint startPoint) {
		this.startPoint = startPoint;
	}
	public GeoPoint getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(GeoPoint endPoint) {
		this.endPoint = endPoint;
	}
	
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	@Override
	public String toString() {
		return "ProfilMoving "+super.toTimeString()+" [startPoint=" + startPoint + ", endPoint="
				+ endPoint + ", distance=" + distance + "]";
	}
	
	@Override
	public boolean isEqualState(ProfilLink obj) {
		if (this == obj)
			return true;
		if(obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfilMoving other = (ProfilMoving) obj;
		if (Double.doubleToLongBits(distance) != Double
				.doubleToLongBits(other.distance))
			return false;
		return true;
	}
	
}
