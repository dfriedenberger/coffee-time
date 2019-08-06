package de.frittenburger.coffee.interfaces;

import java.util.List;

import de.frittenburger.coffee.impl.TrackPointCluster;
import de.frittenburger.geo.model.TrackPoint;

public interface ProfilStrategy {

	
	List<TrackPointCluster> createProfil(List<TrackPoint> trackPoints);
	
}
