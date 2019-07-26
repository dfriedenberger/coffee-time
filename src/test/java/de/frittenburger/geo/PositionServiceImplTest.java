package de.frittenburger.geo;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.frittenburger.geo.impl.PositionServiceImpl;
import de.frittenburger.geo.interfaces.PositionService;
import de.frittenburger.geo.model.GeoPoint;
import de.frittenburger.geo.model.TrackPoint;

public class PositionServiceImplTest {


	private GeoPoint getPoint(double lat, double lon) {

		GeoPoint point = new GeoPoint();
		point.setLatitude(lat);
		point.setLongitude(lon);
		return point;
	}

	private long getTime(String time) {
		try {
			return new SimpleDateFormat("HH:mm").parse(time).getTime();
		} catch (ParseException e) {
			throw new RuntimeException("not implemented "+time);
		}
		
	}

	private TrackPoint createTrackpoint(GeoPoint point, long time) {
		TrackPoint tp = new TrackPoint();
		
		tp.setAltitude(1);
		tp.setTime(time);
		tp.setDevice("device");
		tp.setPoint(point);
		return tp;
	}
	
	@Test
	public void testStatic() {
		
		PositionService service = new PositionServiceImpl();
		
		
		List<TrackPoint> list = new ArrayList<>();
		
		list.add(createTrackpoint(getPoint(4,4),getTime("08:20")));
		list.add(createTrackpoint(getPoint(3,3),getTime("08:21")));
		list.add(createTrackpoint(getPoint(2,2),getTime("08:22")));
		list.add(createTrackpoint(getPoint(1,1),getTime("08:23")));

		TrackPoint r = service.getLastPosition(list);
		
		assertEquals(1.0,r.getPoint().getLatitude(),0.0);
		assertEquals(1.0,r.getPoint().getLongitude(),0.0);
		assertEquals(getTime("08:23"),r.getTime());

	}
	


	

}
