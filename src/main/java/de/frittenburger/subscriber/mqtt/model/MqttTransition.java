package de.frittenburger.subscriber.mqtt.model;


public class MqttTransition extends MqttElement {
	

	private long tst;
	private long wtst;

	private float lon;
	private float lat;

	
	private String desc;
	private String event;

	private String tid;
	
	
	
	private int acc;

	private String t;

	
	public long getTst() {
		return tst;
	}
	public void setTst(long tst) {
		this.tst = tst;
	}
	public long getWtst() {
		return wtst;
	}
	public void setWtst(long wtst) {
		this.wtst = wtst;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public int getAcc() {
		return acc;
	}
	public void setAcc(int acc) {
		this.acc = acc;
	}
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t = t;
	}
	@Override
	public String toString() {
		return "MgttTransition [tst=" + tst + ", wtst=" + wtst + ", lon=" + lon
				+ ", lat=" + lat + ", desc=" + desc + ", event=" + event
				+ ", tid=" + tid + "]";
	}
	
	
	
	
}
