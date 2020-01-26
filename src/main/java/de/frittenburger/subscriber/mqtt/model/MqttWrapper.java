package de.frittenburger.subscriber.mqtt.model;

import com.fasterxml.jackson.databind.JsonNode;

public class MqttWrapper extends MqttElement {

	private final JsonNode tree;

	public MqttWrapper(String type,JsonNode tree) {
		set_type(type);
		this.tree = tree;
	}

	@Override
	public String toString() {
		return "MqttWrapper [tree=" + tree + "]";
	}

}
