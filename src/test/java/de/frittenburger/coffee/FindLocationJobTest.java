package de.frittenburger.coffee;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import de.frittenburger.api.googleplaces.model.Town;
import de.frittenburger.coffee.impl.FindLocationJob;
import de.frittenburger.coffee.impl.ProfilStrategyImpl;
import de.frittenburger.coffee.interfaces.CoffeeJob;
import de.frittenburger.coffee.interfaces.CoffeeQueryService;
import de.frittenburger.coffee.interfaces.NotificationService;
import de.frittenburger.coffee.interfaces.PlaceResolveService;
import de.frittenburger.coffee.interfaces.ProfilStrategy;
import de.frittenburger.coffee.model.Device;
import de.frittenburger.coffee.model.ProfilStaying;
import de.frittenburger.geo.impl.GeoDistanceServiceImpl;
import de.frittenburger.geo.model.TrackPoint;

public class FindLocationJobTest {

	private CoffeeQueryService coffeeQueryService;
	private NotificationService notificationService;
	private ProfilStrategy profilStrategy;
	private PlaceResolveService placeResolveService;

	
	
	@Before 
	public void setUp() {
		coffeeQueryService = mock(CoffeeQueryService.class);
		notificationService = mock(NotificationService.class);
		profilStrategy = mock(ProfilStrategy.class);
		placeResolveService = mock(PlaceResolveService.class);
	}
	
	@Test
	public void test() {
		
		Device device =  new Device("xx");
		TrackPoint trackpoint = new TrackPoint();
		trackpoint.setDevice("xx");
		List<TrackPoint> trackPoints = Arrays.asList(trackpoint);
		ProfilStaying staying = new ProfilStaying();
		
		
		when(coffeeQueryService.getDevices()).thenReturn(Arrays.asList(device));
		when(coffeeQueryService.getTrackPoints()).thenReturn(trackPoints);
		when(coffeeQueryService.getUpdateTime()).thenReturn(1L);
		
		when(profilStrategy.createProfil(trackPoints)).thenReturn(Arrays.asList(staying));
		
		when(placeResolveService.getNearestAdress(null)).thenReturn("testaddress");
		CoffeeJob job = new FindLocationJob(coffeeQueryService, notificationService, profilStrategy, placeResolveService );
		
		
		job.exec(0);
		
		
		//set current Trackpoint and address to device
		assertEquals(staying, device.getProfilLink());
		assertEquals(" is at testaddress", device.getAction());
		
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
				new ProfilStrategyImpl(new GeoDistanceServiceImpl()), placeResolveService );
		
		
		
		for(long time : trackpoints.stream().map(tp -> tp.getTime()).sorted().collect(Collectors.toList()))
		{
			List<TrackPoint> filtered = trackpoints.stream().filter(tp -> tp.getTime() <= time).collect(Collectors.toList());
			when(coffeeQueryService.getTrackPoints()).thenReturn(filtered);
			when(coffeeQueryService.getUpdateTime()).thenReturn(time);
			when(placeResolveService.getNearestAdress(any())).thenReturn("address"+time);
			Town town = new Town();
			town.setName("Town"+time);
			when(placeResolveService.getNearestTown(any())).thenReturn(town);

			job.exec(0);
		
		}
		
		for(String msg : notifications)
			System.out.println(msg);

		
	}

	
	
	
	
	
}
