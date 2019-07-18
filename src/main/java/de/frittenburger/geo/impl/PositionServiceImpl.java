package de.frittenburger.geo.impl;

import java.util.List;

import de.frittenburger.geo.interfaces.PositionService;
import de.frittenburger.geo.model.TrackPoint;

public class PositionServiceImpl implements PositionService {

	@Override
	public TrackPoint getLastPosition(List<TrackPoint> tplist) {
		return tplist.get(tplist.size() - 1);
	}

}
