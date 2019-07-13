package de.frittenburger.geo.interfaces;

import de.frittenburger.geo.model.GeoPoint;

public interface DistanceService {

	double getDistance(GeoPoint point, GeoPoint point2);

}
