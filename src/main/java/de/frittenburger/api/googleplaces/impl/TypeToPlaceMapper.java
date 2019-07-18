package de.frittenburger.api.googleplaces.impl;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.api.googleplaces.model.Establishment;
import de.frittenburger.api.googleplaces.model.Place;
import de.frittenburger.api.googleplaces.model.Route;
import de.frittenburger.api.googleplaces.model.Town;

public class TypeToPlaceMapper implements Function<List<String>, Place> {

	private static final Logger logger = LogManager.getLogger(TypeToPlaceMapper.class);

	private static final List<String> TOWNTYPES = Arrays.asList("locality,political".split(","));
	private static final List<String> ROUTETYPES = Arrays.asList("route");
	private static final List<String> ESTABLISHMENTTYPES = Arrays.asList("point_of_interest,establishment".split(","));

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

    	if(types.containsAll(ESTABLISHMENTTYPES))
    	{
    		List<String> establishmentTypes = types.stream().filter(t -> !ESTABLISHMENTTYPES.contains(t)).collect(Collectors.toList());
    		if(establishmentTypes.size() > 0)
    			logger.warn("Special Establishment "+ establishmentTypes);
    		return new Establishment();


    	}
    	
    	
    	logger.warn("Unknown type "+ types);
    	
    	Place place = new Place();
    	place.setType(types.get(0));
		return place;
	}

}
