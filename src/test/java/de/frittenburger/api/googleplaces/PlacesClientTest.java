package de.frittenburger.api.googleplaces;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.frittenburger.api.googleplaces.impl.PlacesClient;
import de.frittenburger.api.googleplaces.interfaces.WebClient;
import de.frittenburger.api.googleplaces.model.Place;
import de.frittenburger.api.googleplaces.model.PlacesClientConfiguration;
import de.frittenburger.coffee.interfaces.MetricService;
import de.frittenburger.geo.model.GeoPoint;

public class PlacesClientTest {

	private PlacesClientConfiguration configuration;
	private MetricService metricService;
	private WebClient webClient;
	
	
	@Before 
	public void setUp() {
		configuration = mock(PlacesClientConfiguration.class);
		metricService = mock(MetricService.class);
		webClient = mock(WebClient.class);
	}
	
	@Test
	public void test() throws IOException {
		
		
		
		File data = new File(getClass().getClassLoader().getResource("googleplaces/response.json").getFile());

		

		when(webClient.get(any())).thenReturn(new FileInputStream(data));
		
		PlacesClient client = new PlacesClient(configuration, metricService, webClient);
		
		
		GeoPoint point = new GeoPoint();
		List<Place> places = client.getPlaces(point, 50);
		assertEquals(places.size(), 4);
	
	}

}
