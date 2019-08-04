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
		
		double distance = distanceService.getDistance(lastTrackPoint.getPoint(),currentTrackPoint.getPoint());
		
		long timeSeconds = (currentTrackPoint.getTime() - lastTrackPoint.getTime());
		
		boolean relevant = (distance > 500);
		double speed = 0;
		if(timeSeconds > 0)
		{
			speed = distance / (timeSeconds / 3.6);
			if(speed < 6)
				relevant =  (distance > 500);
			else if(speed < 25)
				relevant =  (distance > 3000);
			else if(speed < 50)
				relevant = (distance > 10000);
			else 
				relevant = (distance > 50000); //120 kmh
		}
		
		logger.debug("distance {}m time {}s speed {}km/h => relevant {}",distance, timeSeconds, speed, relevant);

		return relevant;

	}

}
