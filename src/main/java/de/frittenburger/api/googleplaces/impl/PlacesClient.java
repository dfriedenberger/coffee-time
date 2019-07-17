package de.frittenburger.api.googleplaces.impl;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.api.googleplaces.interfaces.WebClient;
import de.frittenburger.api.googleplaces.model.Place;
import de.frittenburger.api.googleplaces.model.PlacesClientConfiguration;
import de.frittenburger.coffee.interfaces.MetricService;
import de.frittenburger.geo.model.GeoPoint;

public class PlacesClient {

	
	private static final Logger logger = LogManager.getLogger(PlacesClient.class);

	private static final Function<JsonNode,Place> mapper = new ResponseToPlacesMapper();

	private final PlacesClientConfiguration configuration;
	private final MetricService metricService;

	private WebClient webClient;

	public PlacesClient(PlacesClientConfiguration configuration, MetricService metricService,WebClient webClient) {
		this.configuration = configuration;
		this.metricService = metricService;
		this.webClient = webClient;
	}

	public List<Place> getPlaces(GeoPoint point,int distance) {

		try {
			
			metricService.permit("google.nearbysearch");
		
			URIBuilder builder = new URIBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json");
			
			//latitude,longitude
			builder.setParameter("location", point.getLatitude()+","+point.getLongitude());
			builder.setParameter("radius",""+ distance);
			//builder.setParameter("fields", "name,vicinity"); kostet immer
			builder.setParameter("key", configuration.getApiKey()); 
	
			
			URI request = builder.build();
			logger.info(request);
		
			try(InputStream is = webClient.get(request))
			{
				JsonNode tree = new ObjectMapper().readTree(is);  
			    List<Place> places = new ArrayList<>();
			    for(JsonNode node : tree.get("results"))
			    {
					logger.info(node);
			    	places.add(mapper.apply(node));
			    }
			    
			    return places;
			}
	
		} 
		catch(Exception e)
		{
			logger.error(e);
		}
		return new ArrayList<>();
		
	}
	
	
}
