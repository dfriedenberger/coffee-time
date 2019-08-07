package de.frittenburger.coffee;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.coffee.impl.ProfilStrategyImpl;
import de.frittenburger.coffee.interfaces.ProfilStrategy;
import de.frittenburger.coffee.model.ProfilLink;
import de.frittenburger.coffee.model.TrackPointCluster;
import de.frittenburger.geo.impl.GeoDistanceServiceImpl;
import de.frittenburger.geo.model.TrackPoint;

public class ProfilStrategyImplTest {

	private GeoDistanceServiceImpl distanceService = new GeoDistanceServiceImpl();

	
	@Test
	public void testDF() throws IOException {
		
		File data = new File(getClass().getClassLoader().getResource("data/2019-08-01-trackpoints.json").getFile());
		List<TrackPoint> trackPoints = new ObjectMapper().readValue(data, new TypeReference<List<TrackPoint>>(){});

		List<TrackPoint> trackPointsDevice = trackPoints.stream().filter(tp -> tp.getDevice().equals("DF")).collect(Collectors.toList());
		
		ProfilStrategy strategy = new ProfilStrategyImpl(new GeoDistanceServiceImpl());
		
		List<ProfilLink> clusters = strategy.createProfil(trackPointsDevice);
	
		for(ProfilLink link :  clusters)
			System.out.println(link);
		
		assertEquals(16,clusters.size());
	}
	


	@Test
	public void testHE() throws IOException {
		
		File data = new File(getClass().getClassLoader().getResource("data/2019-08-01-trackpoints.json").getFile());
		List<TrackPoint> trackPoints = new ObjectMapper().readValue(data, new TypeReference<List<TrackPoint>>(){});

		List<TrackPoint> trackPointsDevice = trackPoints.stream().filter(tp -> tp.getDevice().equals("HE")).collect(Collectors.toList());
		
		ProfilStrategy strategy = new ProfilStrategyImpl(new GeoDistanceServiceImpl());
		
		List<ProfilLink> clusters = strategy.createProfil(trackPointsDevice);
		
		for(ProfilLink link :  clusters)
			System.out.println(link);
		
		assertEquals(3,clusters.size());
	}

}
