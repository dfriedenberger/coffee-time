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
import de.frittenburger.coffee.impl.TrackPointCluster;
import de.frittenburger.coffee.interfaces.ProfilStrategy;
import de.frittenburger.geo.impl.GeoDistanceServiceImpl;
import de.frittenburger.geo.model.TrackPoint;

public class ProfilStrategyImplTest {

	private GeoDistanceServiceImpl distanceService = new GeoDistanceServiceImpl();

	private void dump(List<TrackPointCluster> clusters) {
		for(TrackPointCluster tpc :  clusters)
		{
			TrackPoint tp1 = tpc.getFirst();
			TrackPoint tp2 = tpc.getLast();
			
			double distanceKM = distanceService.getDistance(tp1.getPoint(), tp2.getPoint());
			long timeSeconds = tp2.getTime() - tp1.getTime();
			
			double speed = 0.0;
			if(timeSeconds > 0)
				speed = distanceKM * 3600.0 / timeSeconds;
			
			Date date1 = new Date(tp1.getTime() * 1000);
			Date date2 = new Date(tp2.getTime() * 1000);
			DateFormat  df = new SimpleDateFormat("HH:mm");
			String readable = String.format("%d:%02d", timeSeconds/60, timeSeconds%60);
			System.out.println(df.format(date1)+" Uhr - "+df.format(date2)+" Uhr cluster("+tpc.getType()+","+tpc.size()+") time = "+ readable +" distanceKM = " + distanceKM + " speed = "+speed);
			

		}
		
	}
	
	@Test
	public void testDF() throws IOException {
		
		File data = new File(getClass().getClassLoader().getResource("data/2019-08-01-trackpoints.json").getFile());
		List<TrackPoint> trackPoints = new ObjectMapper().readValue(data, new TypeReference<List<TrackPoint>>(){});

		List<TrackPoint> trackPointsDevice = trackPoints.stream().filter(tp -> tp.getDevice().equals("DF")).collect(Collectors.toList());
		
		ProfilStrategy strategy = new ProfilStrategyImpl(new GeoDistanceServiceImpl());
		
		List<TrackPointCluster> clusters = strategy.createProfil(trackPointsDevice);
	
		dump(clusters);
		
		assertEquals(16,clusters.size());
	}
	


	@Test
	public void testHE() throws IOException {
		
		File data = new File(getClass().getClassLoader().getResource("data/2019-08-01-trackpoints.json").getFile());
		List<TrackPoint> trackPoints = new ObjectMapper().readValue(data, new TypeReference<List<TrackPoint>>(){});

		List<TrackPoint> trackPointsDevice = trackPoints.stream().filter(tp -> tp.getDevice().equals("HE")).collect(Collectors.toList());
		
		ProfilStrategy strategy = new ProfilStrategyImpl(new GeoDistanceServiceImpl());
		
		List<TrackPointCluster> clusters = strategy.createProfil(trackPointsDevice);
		
		dump(clusters);
		
		assertEquals(3,clusters.size());
	}

}
