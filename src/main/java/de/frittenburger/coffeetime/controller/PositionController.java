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
import de.frittenburger.model.Location;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/")
public class PositionController implements PositionApi {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(PositionController.class);
	
    private static final LocationQueryService queryService = new LocationQueryServiceImpl(new File("data"));

    @Override
    public ResponseEntity<Location> currentPosition(String id) {
    	
    	LocationEvent evnt = queryService.getCurrentLocation(id);
    	if(evnt == null)
			return new ResponseEntity<>( HttpStatus.NOT_FOUND );
    	Location location = new Location();
    	
    	location.setLatitude(evnt.getLatitude());
    	location.setLongitude(evnt.getLongitude());
    	location.setTime(evnt.getTime());
    	
    	
    	return ok(location);
    	
    };
    



	
	
}
