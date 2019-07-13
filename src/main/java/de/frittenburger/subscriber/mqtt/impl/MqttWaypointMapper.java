package de.frittenburger.subscriber.mqtt.impl;

import java.util.function.Function;

import de.frittenburger.geo.model.GeoCircle;
import de.frittenburger.geo.model.GeoPoint;
import de.frittenburger.geo.model.GeoRegion;
import de.frittenburger.subscriber.mqtt.model.MqttWaypoint;

public class MqttWaypointMapper implements
		Function<MqttWaypoint, GeoRegion> {

	@Override
	public GeoRegion apply(MqttWaypoint t) {
		
		GeoPoint p = new GeoPoint();
		
		
		p.setLatitude(t.getLat());
				p.setLongitude(t.getLon());

		GeoCircle circle = new GeoCircle();
			
		circle.setId("mqtt#"+t.getTst());
		circle.setName(t.getDesc());
		circle.setPoint(p);
		circle.setRadius(t.getRad());
				
		return circle;
		
	}

}
