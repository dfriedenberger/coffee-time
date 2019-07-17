package de.frittenburger.api.googleplaces.impl;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.databind.JsonNode;

import de.frittenburger.api.googleplaces.model.Place;
import de.frittenburger.api.googleplaces.model.Town;
import de.frittenburger.geo.model.GeoPoint;

public class ResponseToPlacesMapper implements Function<JsonNode, Place> {

	
	
	private Function<List<String>,Place> typetoPlaceMapper = new TypeToPlaceMapper();
	
	@Override
	public Place apply(JsonNode node) {

		String name = node.get("name").asText();
    	//String vicinity = node.get("vicinity").asText();
    	
    	JsonNode location = node.get("geometry").get("location");
    	double latitude = location.get("lat").asDouble();
    	double longitude =location.get("lng").asDouble();
    	GeoPoint p = new GeoPoint();
    	p.setLatitude(latitude);
    	p.setLongitude(longitude);

    	
    	
    	List<String> type = StreamSupport.stream(node.get("types").spliterator(),true).map(n -> n.asText()).collect(Collectors.toList());
    	
    	
    	Place place = typetoPlaceMapper.apply(type);
    	
    	 
    	place.setLocation(p);
    	place.setName(name);
		
		return place;
	}

	

	
}
