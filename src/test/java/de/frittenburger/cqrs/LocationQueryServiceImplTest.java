package de.frittenburger.cqrs;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import de.frittenburger.cqrs.impl.LocationQueryServiceImpl;
import de.frittenburger.cqrs.interfaces.LocationQueryService;
import de.frittenburger.cqrs.model.LocationEvent;

public class LocationQueryServiceImplTest {

	@Test
	public void test() {
		LocationQueryService queryService = new LocationQueryServiceImpl(new File("data"));
		
		LocationEvent x = queryService.getCurrentLocation("DF");
		assertNotNull(x); //1580073395
	}

}
