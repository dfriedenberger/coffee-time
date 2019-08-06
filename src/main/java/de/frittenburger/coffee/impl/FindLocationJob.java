package de.frittenburger.coffee.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.api.googleplaces.model.Town;
import de.frittenburger.coffee.interfaces.CoffeeJob;
import de.frittenburger.coffee.interfaces.CoffeeQueryService;
import de.frittenburger.coffee.interfaces.NotificationService;
import de.frittenburger.coffee.interfaces.PlaceResolveService;
import de.frittenburger.coffee.interfaces.ProfilStrategy;
import de.frittenburger.coffee.model.Device;
import de.frittenburger.coffee.model.MetricException;
import de.frittenburger.geo.model.TrackPoint;

public class FindLocationJob implements CoffeeJob {

	private static final Logger logger = LogManager.getLogger(FindLocationJob.class);

	private long lastUpdate = 0;

	private final CoffeeQueryService coffeeQueryService;
	private final NotificationService notificationService;
	private final ProfilStrategy profilStrategy;
	private final PlaceResolveService placeResolveService;

	
	public FindLocationJob(CoffeeQueryService coffeeQueryService, NotificationService notificationService,ProfilStrategy profilStrategy, 
			PlaceResolveService placeResolveService) {
		this.coffeeQueryService = coffeeQueryService;
		this.notificationService = notificationService;
		this.profilStrategy = profilStrategy;
		this.placeResolveService = placeResolveService;
	}
	


	@Override
	public void exec(long cycle) {

		
		long update = coffeeQueryService.getUpdateTime();
		if(update > lastUpdate)
		{
			lastUpdate = update;
			logger.info("DatabaseUpdate");
			Collection<TrackPoint> tp = coffeeQueryService.getTrackPoints();
			Collection<Device> devices = coffeeQueryService.getDevices();
			
			devices.forEach(device -> { 
				
					try {
						List<TrackPoint> tp4device = tp.stream().filter(t -> t.getDevice().equals(device.getId())).collect(Collectors.toList());
						findCurrentPosition(tp4device,device);
					} catch (IOException e) {
						logger.error(e);
					} catch (MetricException e) {
						logger.error(e);
					}
				
			});
		}
		
		
	}

	
	private void findCurrentPosition(List<TrackPoint> tp, Device device) throws IOException, MetricException {
		
		
		if(tp.isEmpty())
		{
			logger.warn("no track points found for {}", device.getId());
			return;
		}
		
		
		List<TrackPointCluster> states = profilStrategy.createProfil(tp);
		
		if(states.isEmpty())
		{
			logger.warn("no states found for {}", device.getId());
			return;
		}
		
		TrackPointCluster state = states.get(states.size() - 1);
		
		//state changed?
		TrackPointCluster lastState = device.getTrackPointCluster();
		
	
		if(!stateChanged(lastState,state)) return;
		
		device.setTrackPointCluster(state);
		
		String action = null;
		switch(state.getType())
		{
			case TrackPointCluster.staying:
				{
					//Mittelpunkt finden
					TrackPoint lastTp = state.getLast();
					action = " is at "+placeResolveService.getNearestAdress(lastTp.getPoint());
				}
				break;
			case TrackPointCluster.moving:
				{
					TrackPoint tp2 = state.getLast();
					Town town2 =  placeResolveService.getNearestTown(tp2.getPoint());
					action = " reached " + town2.getName();
				}
				break;
		}
		
		
		
		
		if(action == null) 
		{
			logger.warn("no action found for {}", device.getId());
			return;
		}
		
		if(device.getAction().equals(action))
			return;
		
		logger.info("new action: {} {}", device.getId(), action);

		device.setAction(action);
		notificationService.sendMessage(device.getId() + action);
		
	}



	private boolean stateChanged(TrackPointCluster lastState,
			TrackPointCluster state) {

		if(lastState == null)
			return true;
		
		if(!state.getType().equals(lastState.getType()))
			return true;

		//same types 
		switch(state.getType())
		{
			case TrackPointCluster.staying:
				{
					TrackPoint tp1 = lastState.getFirst();
					TrackPoint tp2 = state.getFirst();
					if(tp1.getTime() != tp2.getTime()) return true;
				}
				break;
			case TrackPointCluster.moving:
				{
					TrackPoint tp1 = lastState.getLast();
					TrackPoint tp2 = state.getLast();
					if(tp2.getTime() - tp1.getTime() > 300) return true;
				}
				break;
		}
		
		return false;
	}


	
	
	
	
}
