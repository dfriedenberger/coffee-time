package de.frittenburger.coffee.interfaces;

import de.frittenburger.geo.model.TrackPoint;

public interface DistanceStrategy {

	boolean positionChangeIsRelevant(TrackPoint lastTrackPoint, TrackPoint currentTrackPoint);

}
