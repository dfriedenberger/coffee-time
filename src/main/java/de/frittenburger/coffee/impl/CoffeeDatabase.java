package de.frittenburger.coffee.impl;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.coffee.interfaces.CoffeeCommandService;
import de.frittenburger.coffee.interfaces.CoffeeQueryService;
import de.frittenburger.geo.model.GeoRegion;
import de.frittenburger.geo.model.TrackPoint;

public class CoffeeDatabase implements CoffeeQueryService , CoffeeCommandService {
	
	private static final Logger logger = LogManager.getLogger(CoffeeDatabase.class);
	private static final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	
	private final File dataPath;
	private final Map<String, List<Object>> cache = new HashMap<>();
	private long updateTime = 0;
	
	public CoffeeDatabase(File dataPath)
	{
		this.dataPath = dataPath;
	}
	
	
	@Override
	public void updateRegion(GeoRegion region) {
		logger.info("updateRegion {}",region);		
		saveData("region",region);

	}

	@Override
	public void addTrackPoint(TrackPoint trackPoint) {
		logger.info("addTrackPoint {}",trackPoint);	
		saveData("track",trackPoint);
	}
	
	@Override
	public Collection<TrackPoint> getTrackPoints() {
		return get("track",TrackPoint.class);
	}

	
	@Override
	public long getUpdateTime() {
		return updateTime;
	}

	


	private void saveData(String type,Object obj) 
	{
		Date now = new Date();

		add(type,now,obj);
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


	public void read(int days) {
		
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE, days);  
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		while( cal.getTime().getTime() < now.getTime())  
		{
			String day = dayFormat.format(cal.getTime());
			File[] files =dataPath.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.startsWith(day);
				}});

			for(File f : files)
			{
				try {
					read(f);
				} catch (IOException | ClassNotFoundException | ParseException e) {
					logger.error(e);
				}

			}
			cal.add(Calendar.DATE, 1);
		}
		
		
	}


	private void read(File file) throws IOException, ClassNotFoundException, ParseException {
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
		              Date date = timestampFormat.parse(header.get("time"));
		              System.out.println(header.get("type"));
		              System.out.println(date);
		              System.out.println(obj);
		              add(header.get("type"),date,obj);
		              
		              
		              
		              buffer = null;
			          header = null;
	        		  continue;
	        	  }
	        	  buffer.append(readLine+"\r\n");
	          }
		  }	
	}


	private void add(String type, Date date, Object obj) {
		
		updateTime = new Date().getTime();
		
		if(!cache.containsKey(type))
			cache.put(type, new ArrayList<>());
		cache.get(type).add(obj);
		
	}


	@SuppressWarnings("unchecked")
	private <T> Collection<T> get(String type, Class<T> clazz) {
		if(!cache.containsKey(type))		
			return new ArrayList<>();
		return (Collection<T>) cache.get(type);
	}


	
	

}
