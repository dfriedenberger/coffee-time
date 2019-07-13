package de.frittenburger.api.googleplaces.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.api.googleplaces.model.Place;
import de.frittenburger.api.googleplaces.model.PlacesClientConfiguration;
import de.frittenburger.geo.model.GeoPoint;

public class PlacesClient {

	
	private static final Logger logger = LogManager.getLogger(PlacesClient.class);

	private static final Function<JsonNode,Place> mapper = new ResponseToPlacesMapper();

	private PlacesClientConfiguration configuration;

	public PlacesClient(PlacesClientConfiguration configuration) {
		this.configuration = configuration;
	}

	public List<Place> getPlaces(GeoPoint point,int distance) {

		try {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		
		URIBuilder builder = new URIBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json");
		
		//latitude,longitude
		builder.setParameter("location", point.getLatitude()+","+point.getLongitude());
		builder.setParameter("radius",""+ distance);
		builder.setParameter("fields", "name,vicinity");
		builder.setParameter("key", configuration.getApiKey()); 

		
		URI request = builder.build();
		logger.info(request);
		HttpGet httpGet = new HttpGet(request);
		
		
		CloseableHttpResponse response1 = httpclient.execute(httpGet);
		
		
		// The underlying HTTP connection is still held by the response object
		// to allow the response content to be streamed directly from the network socket.
		// In order to ensure correct deallocation of system resources
		// the user MUST call CloseableHttpResponse#close() from a finally clause.
		// Please note that if response content is not fully consumed the underlying
		// connection cannot be safely re-used and will be shut down and discarded
		// by the connection manager. 
		
		    System.out.println(response1.getStatusLine());
		    HttpEntity entity1 = response1.getEntity();
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    
		    JsonNode tree = new ObjectMapper().readTree(entity1.getContent());
		    response1.close();

		   
		    List<Place> places = new ArrayList<>();
		    for(JsonNode node : tree.get("results"))
		    {
		    	places.add(mapper.apply(node));
		    	
		    
		    	
		    }
		    return places;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		return null;
		
		
	}
	
	/*
	
	{"html_attributions":[],"results":[{"geometry":{"location":{"lat":45.6030238,"lng":12.8859589},"viewport":{"northeast":{"lat":45.6232566,"lng":12.9112277},"southwest":{"lat":45.588373,"lng":12.8604247}}},"icon":"https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png","id":"c95fc526cdce368f7f7b290d3d82068e50850f8c","name":"Caorle","photos":[{"height":4128,"html_attributions":["<a href=\"https://maps.google.com/maps/contrib/114984958572252835205/photos\">Daniela Paraschiva</a>"],"photo_reference":"CmRaAAAAn7hJAwMChSNGEYLg032mLcM2FoTKV0vfoJ6Ra1askICd7njSsjV-QYgooo0siGVvS14jVGsovr1Hg1VMSIQz94jVry-YL166d1TIMGItL8jPjocA-7tKd-aXydI57034EhDMVf5o8WLy1_xQpZdpKLOYGhR0waUW2r6iD8sqJPPGe5SfIM6SCA","width":3096}],"place_id":"ChIJK10sOLvxe0cRAw8pCmgcw1Q","reference":"ChIJK10sOLvxe0cRAw8pCmgcw1Q","scope":"GOOGLE","types":["locality","political"],"vicinity":"Caorle"},{"geometry":{"location":{"lat":45.6157321,"lng":12.9067578},"viewport":{"northeast":{"lat":45.6173161802915,"lng":12.9077027802915},"southwest":{"lat":45.6146182197085,"lng":12.9050048197085}}},"icon":"https://maps.gstatic.com/mapfiles/place_api/icons/shopping-71.png","id":"1081cd67fde62438d648886f65c31efce3baf389","name":"crai SVILUPPO COMMERCIALE S.N.C.","place_id":"ChIJ____g-7xe0cRhSdrDXDfnzg","reference":"ChIJ____g-7xe0cRhSdrDXDfnzg","scope":"GOOGLE","types":["supermarket","grocery_or_supermarket","food","store","point_of_interest","establishment"],"vicinity":"Viale dei Cacciatori, 28, Caorle"}],"status":"OK"}

*/
}
