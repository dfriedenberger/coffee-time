package de.frittenburger.coffee.interfaces;

import java.util.List;

import de.frittenburger.coffee.model.ProfilLink;
import de.frittenburger.geo.model.TrackPoint;

public interface ProfilStrategy {

	
	List<ProfilLink> createProfil(List<TrackPoint> trackPoints);
	
}
