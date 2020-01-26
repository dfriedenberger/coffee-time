 package de.frittenburger.subscriber.mqtt.impl;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import de.frittenburger.cqrs.interfaces.CommandService;
import de.frittenburger.cqrs.model.LocationEvent;
import de.frittenburger.subscriber.mqtt.model.MqttElement;
import de.frittenburger.subscriber.mqtt.model.MqttLocation;


public class MQTTSubscriberCallback implements MqttCallback {

	private static final Logger logger = LogManager.getLogger(MQTTSubscriberCallback.class);
	private final static MQTTParser parser = new MQTTParser();
	private final static Function<MqttLocation,LocationEvent> mapper = new LocationMapperImpl();
	
	private final CommandService<LocationEvent> commandService;
	
	public MQTTSubscriberCallback(CommandService<LocationEvent> commandService) {
			this.commandService = commandService;
	}

	@Override
	public void connectionLost(Throwable e) {
		logger.error("connectionLost",e.getMessage());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		logger.info("deliveryComplete",token);

	}

	@Override
	public void messageArrived(String topic, MqttMessage message)
			throws Exception {
		logger.info("topic {} msg {}",topic,message);
		
		try {
			String payload = new String(message.getPayload(),StandardCharsets.UTF_8);
			MqttElement obj = parser.parse(payload);
			
			//Convert to Event
			if(obj instanceof MqttLocation)
			{
				MqttLocation mqttLocation = MqttLocation.class.cast(obj);
				
				LocationEvent evnt = mapper.apply(mqttLocation);
				commandService.update(evnt);
			}		
			logger.info(obj);
		}
		catch (Exception e)
		{
			logger.error(e);
		}

	}

	
	/*
	 	@Override

	public void messageArrived(String topic, MqttMessage message) throws Exception {
	
		logger.info("topic {} msg {}",topic,message);

		String payload = new String(message.getPayload(),StandardCharsets.UTF_8);
		
	
		MqttElement obj = parser.parse(payload);
		logger.info(obj);
		
		
	}
	*/
	

	

}
