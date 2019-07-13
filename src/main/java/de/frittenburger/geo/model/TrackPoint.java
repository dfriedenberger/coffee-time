package de.frittenburger.geo.model;

public class TrackPoint {

	private GeoPoint point;
	
	private int altitude;
	
	private long time;

	private String device;
	
	public GeoPoint getPoint() {
		return point;
	}

	public void setPoint(GeoPoint point) {
		this.point = point;
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	@Override
	public String toString() {
		return "TrackPoint [point=" + point + ", altitude=" + altitude
				+ ", time=" + time + ", device=" + device + "]";
	}

	
	
}
