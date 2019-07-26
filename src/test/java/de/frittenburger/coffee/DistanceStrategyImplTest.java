package de.frittenburger.coffee;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import de.frittenburger.coffee.impl.DistanceStrategyImpl;
import de.frittenburger.coffee.interfaces.DistanceStrategy;
import de.frittenburger.geo.interfaces.DistanceService;
import de.frittenburger.geo.model.GeoPoint;
import de.frittenburger.geo.model.TrackPoint;

public class DistanceStrategyImplTest {

	private DistanceService distanceService;


	@Before
	public void setUp() throws Exception {
		distanceService = mock(DistanceService.class);
	}
	
	@Test
	public void testNoLastTrackpoint() {
		DistanceStrategy strategy = new DistanceStrategyImpl(distanceService);
		
		TrackPoint currentTrackPoint = new TrackPoint();
		currentTrackPoint.setPoint(new GeoPoint());

		when(distanceService.getDistance(any(GeoPoint.class), any(GeoPoint.class))).thenReturn(500.1);
		
		assertTrue(strategy.positionChangeIsRelevant(null, currentTrackPoint));

	}
	
	@Test
	public void testSmallStep() {
		DistanceStrategy strategy = new DistanceStrategyImpl(distanceService);
		
		TrackPoint lastTrackpoint = new TrackPoint();
		lastTrackpoint.setPoint(new GeoPoint());
		lastTrackpoint.setTime(0); 

		TrackPoint currentTrackPoint = new TrackPoint();
		currentTrackPoint.setPoint(new GeoPoint());
		currentTrackPoint.setTime(60 * 60 * 1000L); //500 Meter pro Stunde (sehr langsam)
		
		when(distanceService.getDistance(any(GeoPoint.class), any(GeoPoint.class))).thenReturn(500.0);
		
		assertFalse(strategy.positionChangeIsRelevant(lastTrackpoint, currentTrackPoint));
	}

	
	@Test
	public void testWideStep() {
		DistanceStrategy strategy = new DistanceStrategyImpl(distanceService);
		
		TrackPoint lastTrackpoint = new TrackPoint();
		lastTrackpoint.setPoint(new GeoPoint());
		lastTrackpoint.setTime(0); 

		TrackPoint currentTrackPoint = new TrackPoint();
		currentTrackPoint.setPoint(new GeoPoint());
		currentTrackPoint.setTime(60 * 60 * 1000L); //500 Meter pro Stunde (sehr langsam)
		
		when(distanceService.getDistance(any(GeoPoint.class), any(GeoPoint.class))).thenReturn(500.1);
		
		assertTrue(strategy.positionChangeIsRelevant(lastTrackpoint, currentTrackPoint));

	}
	
	
	
	@Test
	public void testWideStepDurringDriving() {
		DistanceStrategy strategy = new DistanceStrategyImpl(distanceService);
		
		TrackPoint lastTrackpoint = new TrackPoint();
		lastTrackpoint.setPoint(new GeoPoint());
		lastTrackpoint.setTime(0); 

		TrackPoint currentTrackPoint = new TrackPoint();
		currentTrackPoint.setPoint(new GeoPoint());
		currentTrackPoint.setTime(60 * 1000L); //60km/h => 1km pro Minute

		when(distanceService.getDistance(any(GeoPoint.class), any(GeoPoint.class))).thenReturn(1000.0);
		
		assertFalse(strategy.positionChangeIsRelevant(lastTrackpoint, currentTrackPoint));

	}
	
	
}
