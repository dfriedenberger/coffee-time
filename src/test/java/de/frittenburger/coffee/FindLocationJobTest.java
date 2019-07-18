package de.frittenburger.coffee;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.frittenburger.coffee.impl.FindLocationJob;
import de.frittenburger.coffee.interfaces.CoffeeJob;
import de.frittenburger.coffee.interfaces.CoffeeQueryService;
import de.frittenburger.coffee.interfaces.NotificationService;
import de.frittenburger.coffee.interfaces.PlaceResolveService;
import de.frittenburger.geo.interfaces.DistanceService;

public class FindLocationJobTest {

	private CoffeeQueryService coffeeQueryService;
	private NotificationService notificationService;
	private DistanceService distanceService;
	private PlaceResolveService placeResolveService;

	
	
	@Before 
	public void setUp() {
		coffeeQueryService = Mockito.mock(CoffeeQueryService.class);
		notificationService = Mockito.mock(NotificationService.class);
		distanceService = Mockito.mock(DistanceService.class);
		placeResolveService = Mockito.mock(PlaceResolveService.class);
	}
	@Test
	public void test() {
		
		
		Mockito.when(coffeeQueryService.getUpdateTime()).thenReturn(1L);
		CoffeeJob job = new FindLocationJob(coffeeQueryService, notificationService, distanceService, placeResolveService, null);
		
		
		job.exec(0);
		
		
		
		fail("Not yet implemented");
	}

}
