package de.frittenburger.geo;

import static org.junit.Assert.*;

import org.junit.Test;

import de.frittenburger.geo.impl.GeoDistanceServiceImpl;
import de.frittenburger.geo.interfaces.DistanceService;
import de.frittenburger.geo.model.GeoPoint;

public class GeoDistanceServiceImplTest {

	@Test
	public void testDistanceFrankfurtBerlin() {
		
		DistanceService distanceService = new GeoDistanceServiceImpl();

	
		GeoPoint frankfurt = new GeoPoint();
		frankfurt.setLatitude(50.1109221);
		frankfurt.setLongitude(8.6821267);

		GeoPoint berlin = new GeoPoint();
		berlin.setLatitude(52.520007);
		berlin.setLongitude(13.404954);
		
		double distance = distanceService.getDistance(frankfurt, berlin);
		assertEquals(424.0, distance, 1.0);
	
	}

}
