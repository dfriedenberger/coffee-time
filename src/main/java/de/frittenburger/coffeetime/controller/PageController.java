package de.frittenburger.coffeetime.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class PageController {

 
	private static final Logger logger = LogManager.getLogger(PageController.class);

	
	
	@RequestMapping("/")
	public String welcome(@RequestHeader Map<String, String> headers, Map<String, Object> model, HttpServletRequest request) {
		
	    headers.forEach((key, value) -> {
	    	logger.info(String.format("Header '%s' = %s", key, value));
	    });
	    
	    model.put("header", "Spring Boot Vorlage");
	    model.put("message", "Herzlich Willkommen.");
	    
		return "welcome";
	}
	
	
	
	
	
	
}