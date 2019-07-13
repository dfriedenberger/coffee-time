package de.frittenburger.geo.model;

public class GeoCircle extends GeoRegion {

	
	GeoPoint point;
	double radius;
	public GeoPoint getPoint() {
		return point;
	}
	public void setPoint(GeoPoint point) {
		this.point = point;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	@Override
	public String toString() {
		return "GeoCircle [point=" + point + ", radius=" + radius + "]";
	}
	
	
}
