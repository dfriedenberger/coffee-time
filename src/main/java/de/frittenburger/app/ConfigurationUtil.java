package de.frittenburger.app;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.coffee.model.MetricServiceConfiguration;


public class ConfigurationUtil {

	private final File configPath;
	private final File dataPath;

	

	public ConfigurationUtil(String[] args) throws IOException {
		configPath = new File("config");
		if(!configPath.isDirectory())
			throw new IOException("Configpath "+configPath+" must create or select other with --config <directory>");	
		
		dataPath = new File("data");
		if(!dataPath.isDirectory())
			throw new IOException("Datapath "+dataPath+" must create or select other with --data <directory>");	
		
		
	}



	public <T> T  get(Class<T> clazz,T defaultObject) throws IOException {

		
		
		String filename = clazz.getSimpleName().toLowerCase().replace("configuration", "") + ".json";
		File file = new File(configPath,filename);
		
		if(!file.exists())
		{
			new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(file, defaultObject);
		}
		
		return new ObjectMapper().readValue(file,clazz);
		
	}

	
	public <T> T  get(Class<T> clazz) throws IOException, ReflectiveOperationException {
		return get(clazz,clazz.newInstance());
	}



	public File getDataPath() {
		return dataPath;
	}



	
}
