package de.frittenburger.coffee;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

import java.util.Arrays;

import de.frittenburger.api.googleplaces.impl.PlacesClient;
import de.frittenburger.api.googleplaces.model.Establishment;
import de.frittenburger.api.googleplaces.model.Route;
import de.frittenburger.api.googleplaces.model.Town;
import de.frittenburger.coffee.impl.PlaceResolveServiceImpl;
import de.frittenburger.coffee.interfaces.PlaceResolveService;
import de.frittenburger.geo.model.GeoPoint;

public class PlaceResolveServiceImplTest {

	private PlacesClient placesClient;

	@Before
	public void setUp() throws Exception {
		placesClient = Mockito.mock(PlacesClient.class);		
	}

	@Test
	public void testNearestTown() {
		
		GeoPoint point = new GeoPoint();
		Town nearestTown = new Town();
		when(placesClient.getPlaces(point, 50)).thenReturn(Arrays.asList(
				nearestTown,new Establishment(),new Establishment(),new Route()));
	 
		PlaceResolveService service = new PlaceResolveServiceImpl(placesClient);
		
		Town town = service.getNearestTown(point);
		
		assertEquals(nearestTown, town);
		
	}
	
	@Test
	public void testNearestAdress() {
		
		GeoPoint point = new GeoPoint();
		Town town = new Town();
		town.setName("Town");
		Route route = new Route();
		Establishment esta1 = new Establishment();
		esta1.setName("Establishment");
		Establishment esta2 = new Establishment();

		
		
		when(placesClient.getPlaces(point, 50)).thenReturn(Arrays.asList(
				town,route,esta1,esta2));
	 
		PlaceResolveService service = new PlaceResolveServiceImpl(placesClient);
		
		String adress = service.getNearestAdress(point);
		
		assertEquals("Establishment / Town", adress);
		
	}

	

}
