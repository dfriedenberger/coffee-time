package de.frittenburger.subscriber.mqtt.impl;


import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import de.frittenburger.cqrs.impl.LocationEventFileDatabase;
import de.frittenburger.cqrs.interfaces.CommandService;
import de.frittenburger.cqrs.model.LocationEvent;
import de.frittenburger.subscriber.mqtt.interfaces.MQTTSubscriber;
import de.frittenburger.subscriber.mqtt.model.MQTTConfiguration;

public class MQTTSubscriberImpl implements MQTTSubscriber {

	private static final Logger logger = LogManager.getLogger(MQTTSubscriberImpl.class);
	private final MQTTConfiguration mqttConfiguration;
	private final File dataPath;
	private boolean running = true;

	public MQTTSubscriberImpl(MQTTConfiguration mqttConfiguration,File dataPath) {
		this.mqttConfiguration = mqttConfiguration;
		this.dataPath = dataPath;
	}

	@Override
	public void stop() {
		running = false;
	}
	@Override
	public void run() {
		
		
		MqttClient client = null;
		CommandService<LocationEvent> commandService = new LocationEventFileDatabase(dataPath);
		try {
			client = new MqttClient(mqttConfiguration.getServer(), mqttConfiguration.getClientId());
	        
			MQTTSubscriberCallback callback = new MQTTSubscriberCallback(commandService);
			client.setCallback(callback);

			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName(mqttConfiguration.getUsername());
			options.setPassword(mqttConfiguration.getPassword().toCharArray());
			
			options.setConnectionTimeout(60);
			options.setKeepAliveInterval(60);
			options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);

		
			while(running)
			{
				try {
					
					if(!client.isConnected())
					{
						logger.info("starting connect the server {}",mqttConfiguration.getServer());
						client.connect(options);
						logger.info("connected!");
						
						Thread.sleep(1000);
			
						logger.info("subscribe to topic {}",mqttConfiguration.getTopic());
			
						client.subscribe(mqttConfiguration.getTopic(),0);
					}
				
					Thread.sleep(1000);
					continue;
				} 
				catch(MqttException e)
				{
					logger.error(e);
					logger.info("wait 10 Seconds before reconnect");
					Thread.sleep(10000);
				}
			}
		}
		catch(InterruptedException e) {
				logger.error(e);
		} catch (MqttException e) {
			logger.error(e);
		}
		finally 
		{
			try {
				client.disconnect();
			} catch (MqttException e) {
				logger.error(e);
			}
			logger.info("disconnected!");
		}		
	}

	

}
