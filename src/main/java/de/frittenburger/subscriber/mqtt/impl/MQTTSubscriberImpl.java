package de.frittenburger.subscriber.mqtt.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import de.frittenburger.coffee.interfaces.CoffeeCommandService;
import de.frittenburger.subscriber.mqtt.interfaces.MQTTSubscriber;
import de.frittenburger.subscriber.mqtt.model.MQTTConfiguration;

public class MQTTSubscriberImpl implements MQTTSubscriber , Runnable {

	private static final Logger logger = LogManager.getLogger(MQTTSubscriberImpl.class);
	private final CoffeeCommandService coffeeCommandService;
	private final MQTTConfiguration mqttConfiguration;

	public MQTTSubscriberImpl(CoffeeCommandService coffeeCommandService,MQTTConfiguration mqttConfiguration) {
		this.coffeeCommandService = coffeeCommandService;
		this.mqttConfiguration = mqttConfiguration;
	}

	@Override
	public void run() {
		
		
		MqttClient client = null;
		try {
			client = new MqttClient(mqttConfiguration.getServer(), "2");
	        
			client.setCallback(new MQTTSubscriberCallback(coffeeCommandService));

			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName(mqttConfiguration.getUsername());
			options.setPassword(mqttConfiguration.getPassword().toCharArray());
			
			options.setConnectionTimeout(60);
			options.setKeepAliveInterval(60);
			options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);

		

			logger.info("starting connect the server {}",mqttConfiguration.getServer());
			client.connect(options);
			logger.info("connected!");
			
			Thread.sleep(1000);

			logger.info("subscribe to topic {}",mqttConfiguration.getTopic());

			client.subscribe(mqttConfiguration.getTopic(),0);
				
			while(true)
			{
				Thread.sleep(1000);
			}

		} catch (MqttException e) {
			logger.error(e);
		} catch (Exception e) {
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
