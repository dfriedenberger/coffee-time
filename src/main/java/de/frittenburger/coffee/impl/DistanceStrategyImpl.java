package de.frittenburger.coffee.impl;

import de.frittenburger.coffee.interfaces.DistanceStrategy;
import de.frittenburger.geo.interfaces.DistanceService;
import de.frittenburger.geo.model.TrackPoint;

public class DistanceStrategyImpl implements DistanceStrategy {

	private final DistanceService distanceService;

	public DistanceStrategyImpl(DistanceService distanceService) {
		this.distanceService = distanceService;
	}

	@Override
	public boolean positionChangeIsRelevant(TrackPoint lastTrackPoint, TrackPoint currentTrackPoint) {

		
		if(lastTrackPoint == null)
			return true; //No last Trackpoint 
		
		double distance = distanceService.getDistance(lastTrackPoint.getPoint(),currentTrackPoint.getPoint());
		
		double timeH = (currentTrackPoint.getTime() - lastTrackPoint.getTime()) / (1000 * 3600);
		
		if(timeH > 0)
		{
			double speed = distance / (1000 * timeH);
			if(speed < 6)
				return (distance > 500);
			if(speed < 25)
				return (distance > 3000);
			if(speed < 50)
				return (distance > 10000);
			return (distance > 50000); //120 kmh
		}
		return (distance > 500);
		
	}

}
