package de.frittenburger.cqrs.interfaces;

import de.frittenburger.cqrs.model.LocationEvent;

public interface LocationQueryService {

	LocationEvent getCurrentLocation(String id);

	
}
