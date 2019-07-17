package de.frittenburger.api.googleplaces.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import de.frittenburger.api.googleplaces.interfaces.WebClient;

public class WebClientImpl implements WebClient {

	private static final Logger logger = LogManager.getLogger(WebClientImpl.class);

	@Override
	public InputStream get(URI request) throws IOException {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(request);

		CloseableHttpResponse response = httpclient.execute(httpGet);

		logger.info("{} => {}",request,response.getStatusLine());
		HttpEntity entity = response.getEntity();

		return entity.getContent();

	}

	
	
	public static void main(String args[]) throws URISyntaxException, IOException
	{
		//Testing
		
		WebClient client = new WebClientImpl();

		try(InputStream is = client.get(new URI("https://www.google.de")))
		{
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
			  buffer.write(data, 0, nRead);
			}

			String page = new String(buffer.toByteArray(),"UTF-8");
			
			System.out.println(page);
		}
		
		
	}
}
