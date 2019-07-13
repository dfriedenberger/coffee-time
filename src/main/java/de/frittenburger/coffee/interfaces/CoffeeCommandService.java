package de.frittenburger.coffee.interfaces;

import de.frittenburger.geo.model.GeoRegion;
import de.frittenburger.geo.model.TrackPoint;

public interface CoffeeCommandService {

	void updateRegion(GeoRegion region);

	void addTrackPoint(TrackPoint trackPoint);

}
