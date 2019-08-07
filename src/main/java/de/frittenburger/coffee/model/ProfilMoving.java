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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((endPoint == null) ? 0 : endPoint.hashCode());
		result = prime * result
				+ ((startPoint == null) ? 0 : startPoint.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfilMoving other = (ProfilMoving) obj;
		if (Double.doubleToLongBits(distance) != Double
				.doubleToLongBits(other.distance))
			return false;
		if (endPoint == null) {
			if (other.endPoint != null)
				return false;
		} else if (!endPoint.equals(other.endPoint))
			return false;
		if (startPoint == null) {
			if (other.startPoint != null)
				return false;
		} else if (!startPoint.equals(other.startPoint))
			return false;
		return true;
	}
	
}
