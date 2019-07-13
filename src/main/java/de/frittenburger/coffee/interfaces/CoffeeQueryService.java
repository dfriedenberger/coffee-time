package de.frittenburger.coffee.interfaces;

import java.util.Collection;

import de.frittenburger.geo.model.TrackPoint;

public interface CoffeeQueryService {

	Collection<TrackPoint> getTrackPoints();

}
