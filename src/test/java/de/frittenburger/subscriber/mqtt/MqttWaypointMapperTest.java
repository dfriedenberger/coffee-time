package de.frittenburger.subscriber.mqtt;

import static org.junit.Assert.*;

import java.util.function.Function;

import org.junit.Test;

import de.frittenburger.geo.model.GeoCircle;
import de.frittenburger.geo.model.GeoRegion;
import de.frittenburger.subscriber.mqtt.impl.MqttWaypointMapper;
import de.frittenburger.subscriber.mqtt.model.MqttWaypoint;

public class MqttWaypointMapperTest {

	@Test
	public void test() {
		Function<MqttWaypoint, GeoRegion> mapper = new MqttWaypointMapper(); 

		
		MqttWaypoint waypoint = new MqttWaypoint();
		waypoint.setLat(50.456f);
		waypoint.setLon(8.123f);
		waypoint.setTst(1234567);
		waypoint.setDesc("TestWaypoint");
		waypoint.setRad(50);
		
		GeoRegion region = mapper.apply(waypoint);
		
		assertEquals("mqtt#1234567",region.getId());
		assertEquals("TestWaypoint",region.getName());

		assertTrue(GeoCircle.class.isInstance(region));
		
		GeoCircle circle = GeoCircle.class.cast(region);
		assertEquals(50.0,circle.getRadius(),0.0);
		assertEquals(50.456,circle.getPoint().getLatitude(),0.0001);
		assertEquals(8.123,circle.getPoint().getLongitude(),0.0001);
		
	}

}
