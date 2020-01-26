package de.frittenburger.subscriber.mqtt.impl;

import java.util.function.Function;

import de.frittenburger.cqrs.model.LocationEvent;
import de.frittenburger.subscriber.mqtt.model.MqttLocation;

public class LocationMapperImpl implements Function<MqttLocation, LocationEvent> {

	@Override
	public LocationEvent apply(MqttLocation mqttLocation) {

		LocationEvent location = new LocationEvent();
		
		location.setId(mqttLocation.getTid());
		location.setTime(mqttLocation.getTst());
		location.setLatitude(mqttLocation.getLat());
		location.setLongitude(mqttLocation.getLon());

		return location;
	}

}
