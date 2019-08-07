package de.frittenburger.coffee.model;


public class Device {

	private final String id;
	private ProfilLink profilLink = null;
	private String action = "unknown";

	public Device(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public ProfilLink getProfilLink() {
		return profilLink;
	}

	public void setProfilLink(ProfilLink profilLink) {
		this.profilLink = profilLink;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}