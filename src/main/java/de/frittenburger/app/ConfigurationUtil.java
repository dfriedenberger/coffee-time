package de.frittenburger.app;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;


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



	public <T> T  get(Class<T> clazz) throws IOException, ReflectiveOperationException {

		
		String filename = clazz.getSimpleName().toLowerCase().replace("configuration", "") + ".json";
		File file = new File(configPath,filename);
		
		if(!file.exists())
		{
			new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(file, clazz.newInstance());
		}
		
		return new ObjectMapper().readValue(file,clazz);
		
	}



	public File getDataPath() {
		return dataPath;
	}

}
