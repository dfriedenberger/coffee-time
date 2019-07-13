package de.frittenburger.app;

import java.io.IOException;






import de.frittenburger.api.googleplaces.impl.PlacesClient;
import de.frittenburger.api.googleplaces.model.PlacesClientConfiguration;
import de.frittenburger.coffee.impl.CallExternalTaskNotificationService;
import de.frittenburger.coffee.impl.CoffeeDatabase;
import de.frittenburger.coffee.impl.CoffeeServiceImpl;
import de.frittenburger.coffee.impl.ExternalTaskConfiguration;
import de.frittenburger.coffee.interfaces.NotificationService;
import de.frittenburger.subscriber.mqtt.impl.MQTTSubscriberImpl;
import de.frittenburger.subscriber.mqtt.model.MQTTConfiguration;

public class CoffeeTimeApplication {

	public static void main(String[] args) throws InterruptedException, IOException, ReflectiveOperationException {

		ConfigurationUtil configuration = new ConfigurationUtil(args);
		
		ExternalTaskConfiguration externalTaskConfiguration = configuration.get(ExternalTaskConfiguration.class);
		NotificationService notificationService = new CallExternalTaskNotificationService(externalTaskConfiguration); 
		
		
		CoffeeDatabase coffeedatabase = new CoffeeDatabase(configuration.getDataPath());
		
		coffeedatabase.read(-5);
		
		PlacesClientConfiguration placesClientConfiguration = configuration.get(PlacesClientConfiguration.class);

		CoffeeServiceImpl coffeeService = new CoffeeServiceImpl(coffeedatabase,new PlacesClient(placesClientConfiguration));
		
		
		coffeeService.addNotificationService(notificationService);
		
		
		
		
		
		
		MQTTConfiguration mqttConfiguration = configuration.get(MQTTConfiguration.class);
		MQTTSubscriberImpl mqttSubscriber = new MQTTSubscriberImpl(coffeedatabase,mqttConfiguration);
		
		
		
		// Start things up! 
		Thread coffeeServiceThread = new Thread(coffeeService);
		coffeeServiceThread.start();
		
		Thread mqttSubscriberThread = new Thread(mqttSubscriber);
		mqttSubscriberThread.start();
		
		
		
		//wait for end
		coffeeServiceThread.join();
		mqttSubscriberThread.join();

		
	}

}
