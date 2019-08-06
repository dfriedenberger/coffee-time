package de.frittenburger.coffee.impl;

import java.util.List;
import java.util.stream.Collectors;

import de.frittenburger.coffee.interfaces.ProfilStrategy;
import de.frittenburger.geo.interfaces.DistanceService;
import de.frittenburger.geo.model.TrackPoint;

public class ProfilStrategyImpl implements ProfilStrategy {

	
	private final DistanceService distanceService;
	
	public ProfilStrategyImpl(DistanceService distanceService)
	{
		this.distanceService = distanceService;
	}
	
	@Override
	public List<TrackPointCluster> createProfil(List<TrackPoint> trackPoints) {

		List<TrackPointCluster> clusters = trackPoints.stream().map(tp -> new TrackPointCluster(tp)).collect(Collectors.toList());
		
		
		int ix = 1;
		
		while(ix < clusters.size())
		{
			TrackPointCluster tpc1 = clusters.get(ix - 1);
			TrackPointCluster tpc2 = clusters.get(ix);
			
			double distanceKM = distanceService.getDistance(tpc1.getLast().getPoint(), tpc2.getFirst().getPoint());
			double timeSeconds = tpc2.getFirst().getTime() - tpc1.getLast().getTime();
			
			double speed = 0.0;
			if(timeSeconds > 0)
				speed = distanceKM * 3600.0 / timeSeconds;
			
			
			
			String type = speed < 1 ? TrackPointCluster.staying : TrackPointCluster.moving;
			
		
			if(type != null && tpc1.size() == 1 || tpc1.getType().equals(type))
			{
				if(tpc2.size() != 1)
					throw new RuntimeException();
				tpc1.setType(type);
				tpc1.add(tpc2.getFirst());
				clusters.remove(ix);
				continue;
			}
			
			ix++;
			
		}
		//filter
		ix = 0;
		while(ix < clusters.size())
		{
			TrackPointCluster tpc = clusters.get(ix);
			TrackPoint tp1 = tpc.getFirst();
			TrackPoint tp2 = tpc.getLast();
			
			long timeSeconds = tp2.getTime() - tp1.getTime();
			if(timeSeconds < 60)
			{
				clusters.remove(ix);
				continue;
			}
			ix++;
			
		}
		
		
		//compress
		ix = 1;
		while(ix < clusters.size())
		{
			TrackPointCluster tpc1 = clusters.get(ix - 1);
			TrackPointCluster tpc2 = clusters.get(ix);
			if(tpc1.getType().equals(tpc2.getType()))
			{
				tpc1.addAll(tpc2.getAll());
				clusters.remove(ix);
				continue;
			}
			ix++;
		}
		
		
		
		return clusters;
		
		
	
		
		
		
	}

	
}
