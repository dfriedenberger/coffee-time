package de.frittenburger.geo.interfaces;

import java.util.List;

import de.frittenburger.geo.model.GeoPoint;

public interface GeoFinderStategy {

	GeoPoint findCenter(List<GeoPoint> collect);

}
