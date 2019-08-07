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
import de.frittenburger.coffee.model.ProfilLink;
import de.frittenburger.coffee.model.ProfilMoving;
import de.frittenburger.coffee.model.ProfilStaying;
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
		
		
		List<ProfilLink> links = profilStrategy.createProfil(tp);
		
		if(links.isEmpty())
		{
			logger.warn("no profil links found for {}", device.getId());
			return;
		}
		
		ProfilLink profilLink = links.get(links.size() - 1);
		
		//dump 
		logger.info("device {} curr {}",device.getId(),profilLink);

		
		//state changed?
		ProfilLink lastLink = device.getProfilLink();
		logger.info("device {} last {}",device.getId(),lastLink);

	
		if(profilLink.isEqualState(lastLink)) return;
		
		device.setProfilLink(profilLink);
		
		String action = null;
		
		if(profilLink instanceof ProfilStaying)
		{
			ProfilStaying staying = ProfilStaying.class.cast(profilLink);
			action = " is at "+placeResolveService.getNearestAdress(staying.getPoint());			
		}
		
		if(profilLink instanceof ProfilMoving)
		{
			ProfilMoving moving = ProfilMoving.class.cast(profilLink);
			Town town2 =  placeResolveService.getNearestTown(moving.getEndPoint());
			action = " reached " + town2.getName();		
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


	
	
}
