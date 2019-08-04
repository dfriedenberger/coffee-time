package de.frittenburger.coffee;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import de.frittenburger.api.googleplaces.impl.PlacesClient;
import de.frittenburger.api.googleplaces.impl.WebClientImpl;
import de.frittenburger.coffee.impl.DistanceStrategyImpl;
import de.frittenburger.coffee.impl.FindLocationJob;
import de.frittenburger.coffee.impl.PlaceResolveServiceImpl;
import de.frittenburger.coffee.interfaces.CoffeeJob;
import de.frittenburger.coffee.interfaces.CoffeeQueryService;
import de.frittenburger.coffee.interfaces.DistanceStrategy;
import de.frittenburger.coffee.interfaces.NotificationService;
import de.frittenburger.coffee.interfaces.PlaceResolveService;
import de.frittenburger.coffee.model.Device;
import de.frittenburger.geo.impl.GeoDistanceServiceImpl;
import de.frittenburger.geo.impl.PositionServiceImpl;
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

	
	@Test
	public void test1august() throws JsonParseException, JsonMappingException, IOException {
		
		File data = new File(getClass().getClassLoader().getResource("data/2019-08-01-trackpoints.json").getFile());
		
		List<TrackPoint> trackpoints = new ObjectMapper().readValue(data, new TypeReference<List<TrackPoint>>(){});

		
		
		
		
		Device deviceDF =  new Device("DF");
		Device deviceHE =  new Device("HE");

		when(coffeeQueryService.getDevices()).thenReturn(Arrays.asList(deviceDF,deviceHE));
	
		
		
		List<String> notifications = new ArrayList<>();
		
		doAnswer((Answer<Void>) invocation -> {
	        String msg = invocation.getArgument(0);
	 
	        notifications.add(msg);	        
	        
	        return null;
	    }).when(notificationService).sendMessage(anyString());
	 
		
		
		
		
		CoffeeJob job = new FindLocationJob(coffeeQueryService, notificationService, 
				new DistanceStrategyImpl(new GeoDistanceServiceImpl()), placeResolveService, 
				new PositionServiceImpl() );
		
		
		
		for(long time : trackpoints.stream().map(tp -> tp.getTime()).sorted().collect(Collectors.toList()))
		{
			List<TrackPoint> filtered = trackpoints.stream().filter(tp -> tp.getTime() <= time).collect(Collectors.toList());
			when(coffeeQueryService.getTrackPoints()).thenReturn(filtered);
			when(coffeeQueryService.getUpdateTime()).thenReturn(time);
			when(placeResolveService.getNearestAdress(any())).thenReturn("address"+time);

			job.exec(0);
		
		}
		
		for(String msg : notifications)
			System.out.println(msg);

		
	}

	
	
	
	
	
}
