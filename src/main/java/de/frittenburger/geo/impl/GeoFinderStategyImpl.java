package de.frittenburger.geo.impl;

import java.util.List;

import de.frittenburger.geo.interfaces.GeoFinderStategy;
import de.frittenburger.geo.model.GeoPoint;

public class GeoFinderStategyImpl implements GeoFinderStategy {

	@Override
	public GeoPoint findCenter(List<GeoPoint> points) {

		
		double minlatitude = Double.MAX_VALUE;
		double maxlatitude = Double.MIN_VALUE;

		double minlongitude = Double.MAX_VALUE;
		double maxlongitude = Double.MIN_VALUE;
		
		for(GeoPoint p : points)
		{
			minlatitude = Math.min(minlatitude, p.getLatitude());
			maxlatitude = Math.max(maxlatitude, p.getLatitude());
			minlongitude = Math.min(minlongitude, p.getLongitude());
			maxlongitude = Math.max(maxlongitude, p.getLongitude());
		}
		
		GeoPoint res = new GeoPoint();
		
		res.setLatitude((maxlatitude + minlatitude) / 2);
		res.setLongitude((maxlongitude + minlongitude) / 2);
		
		return res;
	}

}
