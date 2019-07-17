package de.frittenburger.subscriber.mqtt;

import static org.junit.Assert.*;

import java.util.function.Function;

import org.junit.Test;

import de.frittenburger.geo.model.TrackPoint;
import de.frittenburger.subscriber.mqtt.impl.MqttLocationMapper;
import de.frittenburger.subscriber.mqtt.model.MqttLocation;

public class MqttLocationMapperTest {

	@Test
	public void test() {
		
		Function<MqttLocation, TrackPoint> mapper = new MqttLocationMapper(); 
		
		
		MqttLocation location = new MqttLocation();
		location.setLat(50.123f);
		location.setLon(8.567f);
		location.setAlt(2996);
		location.setTst(1234567);
		location.setTid("Test");
		
		TrackPoint trackPoint = mapper.apply(location);
		
		assertEquals(50.123, trackPoint.getPoint().getLatitude(),0.0001);
		assertEquals(8.567, trackPoint.getPoint().getLongitude(),0.0001);
		
		assertEquals(1234567, trackPoint.getTime());
		assertEquals(2996, trackPoint.getAltitude());
		assertEquals("Test", trackPoint.getDevice());

	}

}
