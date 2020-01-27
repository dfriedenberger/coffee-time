package de.frittenburger.coffeetime.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
public class PageController {

 
	private static final Logger logger = LogManager.getLogger(PageController.class);

	private Map<String, String> map = null;
	
	@PostConstruct
	public void init() {
		try {
			map = new ObjectMapper().readValue(new File("config/app.json"), new TypeReference<HashMap<String, String>>() {});
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	@RequestMapping("/")
	public String welcome(@RequestHeader Map<String, String> headers, Map<String, Object> model, HttpServletRequest request) {
		
	    headers.forEach((key, value) -> {
	    	logger.info(String.format("Header '%s' = %s", key, value));
	    });
	    
	    model.put("header", "Wo ist Dirk?");
	    model.put("deviceId", "DF");
		model.put("mapboxAccessToken", map.get("mapboxAccessToken"));

		return "welcome";
	}
	
	
	
	
	
	
}