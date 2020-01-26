package de.frittenburger.coffeetime;

import java.io.File;
import java.io.IOException;

import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.subscriber.mqtt.impl.MQTTSubscriberImpl;
import de.frittenburger.subscriber.mqtt.interfaces.MQTTSubscriber;
import de.frittenburger.subscriber.mqtt.model.MQTTConfiguration;

@SpringBootApplication
public class CoffeeTimeApplication {

	

	private static final Logger logger = LogManager.getLogger(CoffeeTimeApplication.class);
	private static MQTTSubscriber mqttSubscriber;
	private static Thread mqttSubscriberThread;
	public static void main(String[] args) throws IOException, InterruptedException {
		
		
		MQTTConfiguration mqttConfiguration = new ObjectMapper().readValue(new File("config/mqtt.json"), MQTTConfiguration.class);
		mqttSubscriber = new MQTTSubscriberImpl(mqttConfiguration,new File("data"));
		
		
		Thread mqttSubscriberThread = new Thread(mqttSubscriber);
		//
		
				
		
		 SpringApplication application = new SpringApplication(CoffeeTimeApplication.class);
		 application.addListeners((ApplicationListener<ApplicationStartedEvent>) event -> {
		      
		      logger.info("--------- start subscriber thread --------");
		      mqttSubscriberThread.start();
		   });
		 application.run(args);
		
	  
	}
	
	 @PreDestroy
	  public void onExit() {
		 mqttSubscriber.stop();
		 try {
		    logger.info("--------- wait for end --------");
			mqttSubscriberThread.join();
		} catch (InterruptedException e) {
			logger.error(e);
		}	
		 logger.info("--------- subscriber thread stopped --------");

	  }
}
