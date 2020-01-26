package de.frittenburger.subscriber.mqtt.model;

public class MqttWaypoint extends MqttElement {
	

	private long tst;

	private float lon;
	private float lat;
	
	private int rad;
	private String desc;
	
	public long getTst() {
		return tst;
	}
	public void setTst(long tst) {
		this.tst = tst;
	}
	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public int getRad() {
		return rad;
	}
	public void setRad(int rad) {
		this.rad = rad;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public String toString() {
		return "MgttWaypoint [lon=" + lon + ", lat=" + lat + ", rad=" + rad
				+ ", desc=" + desc + "]";
	}
	
	
}
