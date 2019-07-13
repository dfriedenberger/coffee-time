package de.frittenburger.subscriber.mqtt.model;

import java.util.List;

public class MqttLocation extends MqttElement{


	private long tst;

	private float lon;
	private float lat;
	private int alt;

	private String tid;
	
	private int batt;
	private String conn;
	
	private int acc;
	private float p;
	private int vel;
	private int vac;
	private String t;
	private int cog;
	private List<String> inregions;
	
	
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
	public int getAlt() {
		return alt;
	}
	public void setAlt(int alt) {
		this.alt = alt;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public int getBatt() {
		return batt;
	}
	public void setBatt(int batt) {
		this.batt = batt;
	}
	public String getConn() {
		return conn;
	}
	public void setConn(String conn) {
		this.conn = conn;
	}
	public int getAcc() {
		return acc;
	}
	public void setAcc(int acc) {
		this.acc = acc;
	}
	public float getP() {
		return p;
	}
	public void setP(float p) {
		this.p = p;
	}
	public int getVel() {
		return vel;
	}
	public void setVel(int vel) {
		this.vel = vel;
	}
	public int getVac() {
		return vac;
	}
	public void setVac(int vac) {
		this.vac = vac;
	}
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t = t;
	}
	public int getCog() {
		return cog;
	}
	public void setCog(int cog) {
		this.cog = cog;
	}
	
	public List<String> getInregions() {
		return inregions;
	}
	public void setInregions(List<String> inregions) {
		this.inregions = inregions;
	}
	@Override
	public String toString() {
		return "MgttLocation [tst=" + tst + ", lon=" + lon + ", lat=" + lat
				+ ", alt=" + alt + ", tid=" + tid + ", batt=" + batt
				+ ", conn=" + conn + "]";
	}

	
}
