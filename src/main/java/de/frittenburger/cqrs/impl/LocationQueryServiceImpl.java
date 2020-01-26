package de.frittenburger.cqrs.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.cqrs.interfaces.LocationQueryService;
import de.frittenburger.cqrs.model.LocationEvent;

public class LocationQueryServiceImpl implements LocationQueryService {

	private static final Logger logger = LogManager.getLogger(LocationQueryServiceImpl.class);

	private File dataPath;

	private Set<LocationEvent> locations = new HashSet<>();

	public LocationQueryServiceImpl(File dataPath) {

		this.dataPath = dataPath;
	
		update();
	
	}

	private void update()  {

		String[] files = dataPath.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		} );
		Arrays.sort(files);
		
		if(files.length == 0) return;
		
		File f = new File(dataPath, files[files.length - 1]);
		try {
			read(f);
		} catch (ClassNotFoundException | IOException e) {
			logger.error(e);
		} 
		
	}

	
	private void read(File file) throws IOException, ClassNotFoundException {
		logger.info("read {} type={}",file.getName());		
		
		  try(BufferedReader in = new BufferedReader(
				   new InputStreamReader(
		                      new FileInputStream(file), "UTF8")))
		  {

	          String readLine = "";
	          StringBuffer buffer = null;
	          Map<String,String> header = null;
	          
	          while ((readLine = in.readLine()) != null) {

	        	  if(readLine.startsWith("#start"))
	        	  {
	        		  header = new HashMap<>();
	        		  //Dataset Start
	        		  for(String infopart : readLine.substring("#start".length()).split(";"))
	        		  {
	        			  String p[] = infopart.split("=");
	        			  if(p.length != 2) throw new IOException(infopart);
	        			  
	        			  header.put(p[0].trim(),p[1].trim());
	        		  }
	        		  
	        		  buffer = new StringBuffer();
	        		  continue;
	        	  }
	        	  if(readLine.startsWith("#end"))
	        	  {
	        		  //Dataset End
		              JsonNode tree = new ObjectMapper().readTree(buffer.toString());
		              Object obj = new ObjectMapper().treeToValue(tree, Class.forName(header.get("class")));
		              
		              if(obj instanceof LocationEvent)
		              {
			              locations.add(LocationEvent.class.cast(obj));
		              }
		              
		              buffer = null;
			          header = null;
	        		  continue;
	        	  }
	        	  buffer.append(readLine+"\r\n");
	          }
		  }	
	}

	
	
	@Override
	public LocationEvent getCurrentLocation(String id) {

		update();
		
		
		List<LocationEvent> result = locations.stream()
				.filter(ev -> ev.getId().equals(id))
				.sorted((a,b) -> (int)(b.getTime() -  a.getTime())) //reverse
				.collect(Collectors.toList());
		
		if(result.size() == 0)
			return null;
		return result.get(0);
		
	}

}
