package de.frittenburger.coffee.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.api.googleplaces.impl.PlacesClient;
import de.frittenburger.api.googleplaces.model.Establishment;
import de.frittenburger.api.googleplaces.model.Place;
import de.frittenburger.api.googleplaces.model.Town;
import de.frittenburger.coffee.interfaces.PlaceResolveService;
import de.frittenburger.geo.interfaces.DistanceService;
import de.frittenburger.geo.model.GeoPoint;

public class PlaceResolveServiceImpl implements PlaceResolveService {

	private static final Logger logger = LogManager.getLogger(PlaceResolveServiceImpl.class);

	private final PlacesClient placesClient;

	private final DistanceService distanceService;

	public PlaceResolveServiceImpl(PlacesClient placesClient,DistanceService distanceService) {
		this.placesClient = placesClient;
		this.distanceService = distanceService;
	}

	@Override
	public Town getNearestTown(GeoPoint point) {

		List<Place> places = placesClient.getPlaces(point,50);	
		
		
		List<Town> town = places.stream().filter(Town.class::isInstance).map(Town.class::cast).collect(Collectors.toList());

		switch(town.size())
		{
			case 0:
				logger.warn("No town found in {}" , places);
				return null;
			default:
				logger.warn("More than one town found in {}, take first" , places);
				/* FALLTHROUGH */	
			case 1:
				return town.get(0);
		}
	}

	@Override
	public String getNearestAdress(GeoPoint point) {
		
		List<Place> places = placesClient.getPlaces(point,50);	

		if(places.size() == 0)
			return null; //Nothing found
			
		
		for(Place place : places)
		{
			GeoPoint location = place.getLocation();
			double distance = distanceService.getDistance(point, location);
			logger.info("{} type:{} distance:{}",place.getName(),place.getType(),distance);
		}
		
		
		
		Establishment establishment = get(places,Establishment.class);
		Town town = get(places,Town.class);

		
		
		if(establishment == null)
		{
			if(town == null)
				return "#" + places.get(0).getName();
			return town.getName();
		}
		
		String adress = establishment.getName();
		if(town != null)
			adress += " / "+town.getName();
		
		return adress;
	}

	private <T> T get(List<Place> places, Class<T> clazz) {
		List<T> t = places.stream().filter(o -> clazz.isInstance(o)).map(o -> clazz.cast(o)).collect(Collectors.toList());

		switch(t.size())
		{
			case 0:
				logger.warn("No {} found in {}" , clazz.getSimpleName() , places);
				return null;
			default:
				logger.warn("More than one {} found in {}, take first" , clazz.getSimpleName()  , places);
				/* FALLTHROUGH */	
			case 1:
				return t.get(0);
		}	}


}
