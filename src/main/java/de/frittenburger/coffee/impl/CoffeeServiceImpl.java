package de.frittenburger.coffee.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.api.googleplaces.impl.PlacesClient;
import de.frittenburger.api.googleplaces.model.Place;
import de.frittenburger.api.googleplaces.model.Town;
import de.frittenburger.coffee.interfaces.CoffeeQueryService;
import de.frittenburger.coffee.interfaces.CoffeeService;
import de.frittenburger.coffee.interfaces.NotificationService;
import de.frittenburger.geo.interfaces.DistanceService;
import de.frittenburger.geo.model.TrackPoint;

public class CoffeeServiceImpl implements CoffeeService , Runnable {

	
	private class Device {

		private final String id;
		private String town = "unbekannt";
		private TrackPoint trackPoint = null;
		
		public Device(String id) {
			this.id = id;
		}
		
		public String getId() {
			return id;
		}
		public boolean setTown(String name) {

			if(town.equals(name)) return false;
			town = name;
			return true;
		}

		public String isInTown() {
			return id + " is in "+town;
		}

		public TrackPoint getTrackPoint() {
			return trackPoint;
		}

		public void setTrackPoint(TrackPoint trackPoint) {
			this.trackPoint = trackPoint;
		}

		
		
		
	}
	
	private static final Logger logger = LogManager.getLogger(CoffeeServiceImpl.class);

	private final CoffeeQueryService coffeeQueryService;
	private final PlacesClient placesClient;

	private final List<NotificationService> notificationServices = new ArrayList<>();
	private boolean shouldRun = true;

	private final DistanceService distanceService;

	public CoffeeServiceImpl(CoffeeQueryService coffeeQueryService,DistanceService distanceService,PlacesClient placesClient ) {
		this.coffeeQueryService = coffeeQueryService;
		this.placesClient = placesClient;
		this.distanceService = distanceService;
	}

	@Override
	public void addNotificationService(NotificationService notificationService) {
		notificationServices.add(notificationService);
	}
	
	private void sendMessage(String message) {
		
		for(NotificationService notificationService : notificationServices)
		{
			try {
				notificationService.sendMessage(message);
			} catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	@Override
	public void run() {

		
		List<Device> devices = new ArrayList<>();
		devices.add(new Device("HE"));
		devices.add(new Device("DF"));

		
		
		while(shouldRun)
		{
			
			Collection<TrackPoint> tp = coffeeQueryService.getTrackPoints();
			
			
			devices.forEach(d -> findCurrentPosition(tp,d));
			
			
			try {
				Thread.sleep(50000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	private void findCurrentPosition(Collection<TrackPoint> tp, Device device) {
		List<TrackPoint> tplist = tp.stream().filter(t -> t.getDevice().equals(device.getId())).collect(Collectors.toList());
		TrackPoint currentTp = tplist.get(tplist.size() - 1);

		TrackPoint lastTp = device.getTrackPoint();
		
		
		if(lastTp != null)
		{
			double distance = distanceService.getDistance(lastTp.getPoint(),currentTp.getPoint());
			
			logger.info("distance {} since last update of {}",distance,device.getId());

			if(distance < 1.0) return; //No chnmaging
			
		}
		device.setTrackPoint(currentTp);
		
		List<Place> places = placesClient.getPlaces(currentTp.getPoint(),50);	
		
		
		List<Town> town = places.stream().filter(Town.class::isInstance).map(Town.class::cast).collect(Collectors.toList());

		switch(town.size())
		{
			case 0:
				logger.warn("No town found in {}" , places);
				break;
			default:
				logger.warn("More than one town found in {}, take first" , places);
				/* FALLTHROUGH */	
			case 1:
				if(device.setTown(town.get(0).getName()))
				{
					logger.info("Position changed");
					sendMessage(device.isInTown());
				}
				
				logger.info("{} ",device.isInTown());
				break;
		}
		
	
		
	}



}
