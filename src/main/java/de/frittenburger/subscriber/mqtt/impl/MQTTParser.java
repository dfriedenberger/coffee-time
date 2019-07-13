package de.frittenburger.subscriber.mqtt.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.subscriber.mqtt.model.MqttElement;
import de.frittenburger.subscriber.mqtt.model.MqttLocation;
import de.frittenburger.subscriber.mqtt.model.MqttTransition;
import de.frittenburger.subscriber.mqtt.model.MqttWaypoint;
import de.frittenburger.subscriber.mqtt.model.MqttWaypointList;
import de.frittenburger.subscriber.mqtt.model.MqttWrapper;



public class MQTTParser {


	private static final Logger logger = LogManager.getLogger(MQTTParser.class);

	public MqttElement parse(String data) throws IOException {
		if(data == null) 
			throw new IllegalArgumentException("data");
		
		
		JsonNode tree = new ObjectMapper().readTree(data);
		String type = tree.get("_type").asText();
		
		switch(type)
		{
		case "transition":
			return new ObjectMapper().readValue(data, MqttTransition.class);

		case "location":
			return new ObjectMapper().readValue(data, MqttLocation.class);
		case "waypoint":
			return new ObjectMapper().readValue(data, MqttWaypoint.class);
		case "waypoints":
			return new ObjectMapper().readValue(data, MqttWaypointList.class);
		default:
			logger.error("Unknown type "+type);
			return new MqttWrapper(type,tree);
		}
		
	}
	

}
