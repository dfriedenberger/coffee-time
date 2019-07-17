package de.frittenburger.api.googleplaces.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public interface WebClient {

	InputStream get(URI request) throws IOException;
	
}
