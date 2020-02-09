package de.frittenburger.coffeetime.controller;


import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;










import de.frittenburger.api.PositionApi;
import de.frittenburger.cqrs.impl.LocationQueryServiceImpl;
import de.frittenburger.cqrs.interfaces.LocationQueryService;
import de.frittenburger.cqrs.model.LocationEvent;
import de.frittenburger.model.Position;
import de.frittenburger.model.Position.ConnectionEnum;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/")
public class PositionController implements PositionApi {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(PositionController.class);
	
    private static final LocationQueryService queryService = new LocationQueryServiceImpl(new File("data"));

    @Override
    public ResponseEntity<Position> currentPosition(String id) {
    	
    	LocationEvent evnt = queryService.getCurrentLocation(id);
    	if(evnt == null)
			return new ResponseEntity<>( HttpStatus.NOT_FOUND );
    	Position location = new Position();
    	
    	location.setLatitude(evnt.getLatitude());
    	location.setLongitude(evnt.getLongitude());
    	location.setTime(evnt.getTime());
    	location.setBattery(evnt.getBattery());
    	
    	switch(evnt.getConnection())
    	{
    	case Unknown:
        	location.setConnection(ConnectionEnum.MOBILEDATA);
        	break;

    	case Offline:
        	location.setConnection(ConnectionEnum.OFFLINE);
        	break;

    	case WiFi:
        	location.setConnection(ConnectionEnum.WIFI);
        	break;

    	case MobileData:
        	location.setConnection(ConnectionEnum.MOBILEDATA);
        	break;

    	}
    	
    	
    	return ok(location);
    	
    };
    



	
	
}
