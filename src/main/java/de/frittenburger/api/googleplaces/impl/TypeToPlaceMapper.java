package de.frittenburger.api.googleplaces.impl;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.api.googleplaces.model.Place;
import de.frittenburger.api.googleplaces.model.Route;
import de.frittenburger.api.googleplaces.model.Town;

public class TypeToPlaceMapper implements Function<List<String>, Place> {

	private static final Logger logger = LogManager.getLogger(TypeToPlaceMapper.class);

	private static final List<String> TOWNTYPES = Arrays.asList("locality,political".split(","));
	private static final List<String> ROUTETYPES = Arrays.asList("route");

	//"point_of_interest","establishment"
	
	
	@Override
	public Place apply(List<String> types) {
		
    	
    	if(TOWNTYPES.equals(types))
    	{
    		return new Town();
    	}
    	if(ROUTETYPES.equals(types))
    	{
    		return new Route();
    	}
    	
    	logger.warn("Unknoww type "+ types);
    	
    	Place place = new Place();
    	place.setType(types.get(0));
		return place;
	}

}
