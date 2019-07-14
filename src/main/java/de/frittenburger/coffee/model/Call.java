package de.frittenburger.coffee.model;

public class Call {

	private final long time;
	private final String key;

	public Call(long time, String key) {
		this.time = time;
		this.key = key;
	}

	public long getTime() {
		return time;
	}

	public String getKey() {
		return key;
	}

}
