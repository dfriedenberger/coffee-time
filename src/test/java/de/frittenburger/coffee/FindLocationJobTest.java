package de.frittenburger.coffee;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.frittenburger.coffee.impl.FindLocationJob;
import de.frittenburger.coffee.interfaces.CoffeeJob;
import de.frittenburger.coffee.interfaces.CoffeeQueryService;
import de.frittenburger.coffee.interfaces.DistanceStrategy;
import de.frittenburger.coffee.interfaces.NotificationService;
import de.frittenburger.coffee.interfaces.PlaceResolveService;
import de.frittenburger.geo.interfaces.DistanceService;
import de.frittenburger.geo.interfaces.PositionService;

public class FindLocationJobTest {

	private CoffeeQueryService coffeeQueryService;
	private NotificationService notificationService;
	private DistanceStrategy distanceStrategy;
	private PlaceResolveService placeResolveService;
	private PositionService positionService;

	
	
	@Before 
	public void setUp() {
		coffeeQueryService = Mockito.mock(CoffeeQueryService.class);
		notificationService = Mockito.mock(NotificationService.class);
		distanceStrategy = Mockito.mock(DistanceStrategy.class);
		placeResolveService = Mockito.mock(PlaceResolveService.class);
		positionService = Mockito.mock(PositionService.class);

	}
	
	@Test
	public void test() {
		
		
		Mockito.when(coffeeQueryService.getUpdateTime()).thenReturn(1L);
		CoffeeJob job = new FindLocationJob(coffeeQueryService, notificationService, distanceStrategy, placeResolveService, positionService );
		
		
		job.exec(0);
		
		
		
		fail("Not yet implemented");
	}

}
