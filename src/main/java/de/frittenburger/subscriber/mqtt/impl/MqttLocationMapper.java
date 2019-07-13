package de.frittenburger.subscriber.mqtt.impl;

import java.util.function.Function;

import de.frittenburger.geo.model.GeoPoint;
import de.frittenburger.geo.model.TrackPoint;
import de.frittenburger.subscriber.mqtt.model.MqttLocation;

public class MqttLocationMapper implements Function<MqttLocation, TrackPoint> {

	@Override
	public TrackPoint apply(MqttLocation t) {

		GeoPoint geoPoint = new GeoPoint();
		geoPoint.setLatitude(t.getLat());
		geoPoint.setLongitude(t.getLon());
		
		TrackPoint trackPoint = new TrackPoint();
		
		trackPoint.setDevice(t.getTid());
		trackPoint.setPoint(geoPoint);
		trackPoint.setAltitude(t.getAlt());
		trackPoint.setTime(t.getTst());
		
		return trackPoint;
	}

}
