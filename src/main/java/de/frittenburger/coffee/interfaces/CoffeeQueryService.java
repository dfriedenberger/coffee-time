package de.frittenburger.coffee.interfaces;

import java.util.Collection;

import de.frittenburger.coffee.model.Device;
import de.frittenburger.geo.model.TrackPoint;

public interface CoffeeQueryService {

	Collection<TrackPoint> getTrackPoints();

	long getUpdateTime();

	Collection<Device> getDevices();

}
