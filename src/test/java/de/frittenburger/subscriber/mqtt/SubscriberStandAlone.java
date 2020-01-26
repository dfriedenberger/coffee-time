package de.frittenburger.subscriber.mqtt;


import java.io.File;
import java.io.IOException;


import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.subscriber.mqtt.impl.MQTTSubscriberImpl;
import de.frittenburger.subscriber.mqtt.interfaces.MQTTSubscriber;
import de.frittenburger.subscriber.mqtt.model.MQTTConfiguration;

public class SubscriberStandAlone {

	
	
	public static void main(String args[]) throws IOException  {
		
		
		MQTTConfiguration mqttConfiguration = new ObjectMapper().readValue(new File("config/mqtt.json"), MQTTConfiguration.class);
		MQTTSubscriber subscriber = new MQTTSubscriberImpl(mqttConfiguration,new File("data"));
		
		subscriber.run();
		
		
	}

}
