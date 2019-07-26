package de.frittenburger.coffee.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.coffee.interfaces.CoffeeJob;
import de.frittenburger.coffee.interfaces.CoffeeQueryService;
import de.frittenburger.coffee.interfaces.DistanceStrategy;
import de.frittenburger.coffee.interfaces.NotificationService;
import de.frittenburger.coffee.interfaces.PlaceResolveService;
import de.frittenburger.coffee.model.Device;
import de.frittenburger.coffee.model.MetricException;
import de.frittenburger.geo.interfaces.PositionService;
import de.frittenburger.geo.model.TrackPoint;

public class FindLocationJob implements CoffeeJob {

	private static final Logger logger = LogManager.getLogger(FindLocationJob.class);

	private long lastUpdate = 0;

	private final CoffeeQueryService coffeeQueryService;
	private final NotificationService notificationService;
	private final DistanceStrategy distanceStrategy;
	private final PlaceResolveService placeResolveService;
	private final PositionService positionService;

	
	public FindLocationJob(CoffeeQueryService coffeeQueryService, NotificationService notificationService,DistanceStrategy distanceStrategy, 
			PlaceResolveService placeResolveService,PositionService positionService) {
		this.coffeeQueryService = coffeeQueryService;
		this.notificationService = notificationService;
		this.distanceStrategy = distanceStrategy;
		this.placeResolveService = placeResolveService;
		this.positionService = positionService;
	}
	


	@Override
	public void exec(long cycle) {

		
		long update = coffeeQueryService.getUpdateTime();
		if(update > lastUpdate)
		{
			lastUpdate = update;
			
			Collection<TrackPoint> tp = coffeeQueryService.getTrackPoints();
			Collection<Device> devices = coffeeQueryService.getDevices();
			
			devices.forEach(d -> { 
				
					try {
						findCurrentPosition(tp,d);
					} catch (IOException e) {
						logger.error(e);
					} catch (MetricException e) {
						logger.error(e);
					}
				
			});
		}
		
		
	}

	
	private void findCurrentPosition(Collection<TrackPoint> tp, Device device) throws IOException, MetricException {
		
		//Find position and use it if changed more than 500 meters
		TrackPoint currentTp = positionService.getLastPosition(tp.stream().filter(t -> t.getDevice().equals(device.getId())).collect(Collectors.toList()));
		
		
		
		//position changed more then 500 meters?
		
		if(!distanceStrategy.positionChangeIsRelevant(device.getTrackPoint(),currentTp))
			return;
		
		
		device.setTrackPoint(currentTp);
		
		
		String adress = placeResolveService.getNearestAdress(currentTp.getPoint());
		
		
		if(adress == null) 
		{
			logger.info("new position found for {}", device.getId());
			return;
		}
		
		if(device.getAdress().equals(adress))
			return;
		
		logger.info("new position {} for {}", adress, device.getId());

		device.setAdress(adress);
		notificationService.sendMessage(device.getId() + " is at " +  adress);
		
	}


	
	
	
	
}
