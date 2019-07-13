package de.frittenburger.api.googleplaces.model;

import de.frittenburger.geo.model.GeoPoint;

public class Place {

	private String type;
	private GeoPoint location;
	private String name;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public GeoPoint getLocation() {
		return location;
	}
	public void setLocation(GeoPoint location) {
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString() {
		return "Place [type=" + type + ", location=" + location + ", name="
				+ name + "]";
	}
	

}
