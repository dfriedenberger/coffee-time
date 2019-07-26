package de.frittenburger.api.googleplaces;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import de.frittenburger.api.googleplaces.impl.PlacesClient;
import de.frittenburger.api.googleplaces.model.Place;
import de.frittenburger.geo.model.GeoPoint;

public class PlacesClientTest {

	@Test
	public void test() {
		
		
		PlacesClient client = new PlacesClient(null, null, null);
		
		
		GeoPoint point = new GeoPoint();
		List<Place> places = client.getPlaces(point, 50);
		assertEquals(places.size(), 2);
	
	}

}
