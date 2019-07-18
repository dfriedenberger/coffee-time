package de.frittenburger.coffee.interfaces;


import de.frittenburger.api.googleplaces.model.Town;
import de.frittenburger.geo.model.GeoPoint;

public interface PlaceResolveService {


	Town getNearestTown(GeoPoint point);

	String getNearestAdress(GeoPoint point);

}
