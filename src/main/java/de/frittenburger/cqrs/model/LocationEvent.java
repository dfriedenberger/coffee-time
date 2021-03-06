package de.frittenburger.cqrs.model;

public class LocationEvent {
	
	private String id;
	private long time;
	private double latitude;
	private double longitude;
	
	private ConnectionType connection;
	private int battery;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
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
	
	public ConnectionType getConnection() {
		return connection;
	}
	
	public void setConnection(ConnectionType connection) {
		this.connection = connection;
	}
	
	public int getBattery() {
		return battery;
	}
	public void setBattery(int battery) {
		this.battery = battery;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + battery;
		result = prime * result
				+ ((connection == null) ? 0 : connection.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (time ^ (time >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocationEvent other = (LocationEvent) obj;
		if (battery != other.battery)
			return false;
		if (connection != other.connection)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		if (time != other.time)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LocationEvent [id=" + id + ", time=" + time + ", latitude="
				+ latitude + ", longitude=" + longitude + ", connection="
				+ connection + ", battery=" + battery + "]";
	}
	
	
	
	

}
