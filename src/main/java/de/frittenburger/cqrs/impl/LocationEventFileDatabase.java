package de.frittenburger.cqrs.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.cqrs.interfaces.CommandService;
import de.frittenburger.cqrs.model.LocationEvent;

public class LocationEventFileDatabase implements CommandService<LocationEvent> {

	private static final Logger logger = LogManager.getLogger(LocationEventFileDatabase.class);
	private static final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	private final File dataPath;

	public LocationEventFileDatabase(File dataPath) {
		this.dataPath = dataPath;
	}

	@Override
	public void update(LocationEvent evnt) {
		saveData("location",evnt);
	}

	
	private void saveData(String type,Object obj) 
	{
		Date now = new Date();

		try {
			String day = dayFormat.format(now);
			File file = new File(dataPath,day+"-"+type+".utf8.txt");
			String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			
			try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) 
			{
				out.println("#start time="+timestampFormat.format(now)+";class="+obj.getClass().getName()+";type="+type);
				out.println(json);
				out.println("#end");
			}
		} catch(IOException e)
		{
			logger.error(e);
		}
	}
	
}
