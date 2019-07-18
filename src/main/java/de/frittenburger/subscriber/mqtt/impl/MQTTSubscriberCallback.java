 package de.frittenburger.subscriber.mqtt.impl;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import de.frittenburger.coffee.interfaces.CoffeeCommandService;
import de.frittenburger.geo.model.GeoRegion;
import de.frittenburger.geo.model.TrackPoint;
import de.frittenburger.subscriber.mqtt.model.MqttElement;
import de.frittenburger.subscriber.mqtt.model.MqttLocation;
import de.frittenburger.subscriber.mqtt.model.MqttWaypoint;
import de.frittenburger.subscriber.mqtt.model.MqttWaypointList;


public class MQTTSubscriberCallback implements MqttCallback {

	private static final Logger logger = LogManager.getLogger(MQTTSubscriberCallback.class);
	private final static MQTTParser parser = new MQTTParser();
	
	private final static Function<MqttWaypoint,GeoRegion> waypointMapper = new MqttWaypointMapper();
	private final static Function<MqttLocation,TrackPoint> locationMapper = new MqttLocationMapper();
	
	
	private final CoffeeCommandService coffeeCommandService;

	public MQTTSubscriberCallback(CoffeeCommandService coffeeCommandService) {
		this.coffeeCommandService = coffeeCommandService;
	}

	@Override
	public void connectionLost(Throwable e) {
		logger.error("connectionLost",e.getMessage());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		logger.info("deliveryComplete",token);

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		logger.info("topic {} msg {}",topic,message);

		String payload = new String(message.getPayload(),StandardCharsets.UTF_8);
		
	
		MqttElement obj = parser.parse(payload);
		logger.info(obj);
		
		//Convert to Event
		if(obj instanceof MqttLocation)
		{
			MqttLocation location = MqttLocation.class.cast(obj);
			TrackPoint trackPoint = locationMapper.apply(location);
			coffeeCommandService.addTrackPoint(trackPoint);
		}
		if(obj instanceof MqttWaypoint)
		{
			MqttWaypoint waypoint = MqttWaypoint.class.cast(obj);
			GeoRegion region = waypointMapper.apply(waypoint);
			coffeeCommandService.updateRegion(region);
		}
		if(obj instanceof MqttWaypointList)
		{
			MqttWaypointList waypointList = MqttWaypointList.class.cast(obj);
			waypointList.getWaypoints().stream().map(waypointMapper).forEach(region -> coffeeCommandService.updateRegion(region));
		}
		
	}

	

}
