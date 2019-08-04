package de.frittenburger.coffee.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.coffee.interfaces.DistanceStrategy;
import de.frittenburger.geo.interfaces.DistanceService;
import de.frittenburger.geo.model.TrackPoint;

public class DistanceStrategyImpl implements DistanceStrategy {

	private static final Logger logger = LogManager.getLogger(DistanceStrategyImpl.class);

	private final DistanceService distanceService;

	public DistanceStrategyImpl(DistanceService distanceService) {
		this.distanceService = distanceService;
	}

	@Override
	public boolean positionChangeIsRelevant(TrackPoint lastTrackPoint, TrackPoint currentTrackPoint) {

		
		if(lastTrackPoint == null)
			return true; //No last Trackpoint 
		
		double distanceKm = distanceService.getDistance(lastTrackPoint.getPoint(),currentTrackPoint.getPoint());
		
		long timeSeconds = (currentTrackPoint.getTime() - lastTrackPoint.getTime());
		
		boolean relevant = (distanceKm > 0.5);
		double speed = 0;
		if(timeSeconds > 0)
		{
			speed = distanceKm * 3600  / timeSeconds;
			if(speed < 6)
				relevant =  (distanceKm > 0.5);
			else if(speed < 25)
				relevant =  (distanceKm > 3.0);
			else if(speed < 50)
				relevant = (distanceKm > 10.0);
			else 
				relevant = (distanceKm > 50.0); //120 kmh
		}
		
		logger.debug("distance {}km time {}s speed {}km/h => relevant {}",distanceKm, timeSeconds, speed, relevant);

		return relevant;

	}

}
