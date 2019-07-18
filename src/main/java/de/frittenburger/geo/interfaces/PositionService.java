package de.frittenburger.geo.interfaces;

import java.util.List;

import de.frittenburger.geo.model.TrackPoint;

public interface PositionService {

	TrackPoint getLastPosition(List<TrackPoint> list);

}
