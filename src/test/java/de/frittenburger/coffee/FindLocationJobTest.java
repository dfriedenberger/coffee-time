package de.frittenburger.coffee;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import de.frittenburger.coffee.impl.FindLocationJob;
import de.frittenburger.coffee.interfaces.CoffeeJob;
import de.frittenburger.coffee.interfaces.CoffeeQueryService;
import de.frittenburger.coffee.interfaces.DistanceStrategy;
import de.frittenburger.coffee.interfaces.NotificationService;
import de.frittenburger.coffee.interfaces.PlaceResolveService;
import de.frittenburger.coffee.model.Device;
import de.frittenburger.geo.interfaces.PositionService;
import de.frittenburger.geo.model.TrackPoint;

public class FindLocationJobTest {

	private CoffeeQueryService coffeeQueryService;
	private NotificationService notificationService;
	private DistanceStrategy distanceStrategy;
	private PlaceResolveService placeResolveService;
	private PositionService positionService;

	
	
	@Before 
	public void setUp() {
		coffeeQueryService = mock(CoffeeQueryService.class);
		notificationService = mock(NotificationService.class);
		distanceStrategy = mock(DistanceStrategy.class);
		placeResolveService = mock(PlaceResolveService.class);
		positionService = mock(PositionService.class);
	}
	
	@Test
	public void test() {
		
		Device device =  new Device("xx");
		TrackPoint trackpoint = new TrackPoint();
		trackpoint.setDevice("xx");

		when(coffeeQueryService.getDevices()).thenReturn(Arrays.asList(device));
		when(coffeeQueryService.getTrackPoints()).thenReturn(Arrays.asList(trackpoint));
		when(coffeeQueryService.getUpdateTime()).thenReturn(1L);
		
		when(positionService.getLastPosition(any())).thenReturn(trackpoint);
		
		
		when(distanceStrategy.positionChangeIsRelevant(null,trackpoint)).thenReturn(true);
		when(placeResolveService.getNearestAdress(null)).thenReturn("testaddress");
		CoffeeJob job = new FindLocationJob(coffeeQueryService, notificationService, distanceStrategy, placeResolveService, positionService );
		
		
		job.exec(0);
		
		verify(distanceStrategy).positionChangeIsRelevant(null,trackpoint);
		
		//set current Trackpoint and address to device
		assertEquals(trackpoint, device.getTrackPoint());
		assertEquals("testaddress", device.getAdress());
		
		//call notificationservice
		verify(notificationService).sendMessage("xx is at testaddress");   
	}

}
