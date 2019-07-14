package de.frittenburger.app;

import java.io.IOException;










import java.util.HashMap;
import java.util.Map;

import de.frittenburger.api.googleplaces.impl.PlacesClient;
import de.frittenburger.api.googleplaces.model.PlacesClientConfiguration;
import de.frittenburger.coffee.impl.CallExternalTaskNotificationService;
import de.frittenburger.coffee.impl.CoffeeDatabase;
import de.frittenburger.coffee.impl.CoffeeServiceImpl;
import de.frittenburger.coffee.impl.MetricServiceImpl;
import de.frittenburger.coffee.interfaces.MetricService;
import de.frittenburger.coffee.interfaces.NotificationService;
import de.frittenburger.coffee.model.ExternalTaskConfiguration;
import de.frittenburger.coffee.model.MetricLimit;
import de.frittenburger.coffee.model.MetricServiceConfiguration;
import de.frittenburger.geo.impl.GeoDistanceServiceImpl;
import de.frittenburger.subscriber.mqtt.impl.MQTTSubscriberImpl;
import de.frittenburger.subscriber.mqtt.model.MQTTConfiguration;

public class CoffeeTimeApplication {

	private static MetricServiceConfiguration defaultMetric = new MetricServiceConfiguration();
	static {
		Map<String, MetricLimit> metrics = new HashMap<>();
		MetricLimit limit = new MetricLimit();
		metrics.put("google.nearbysearch", limit);
		defaultMetric.setMetrics(metrics);
	}
	public static void main(String[] args) throws InterruptedException, IOException, ReflectiveOperationException {

		ConfigurationUtil configuration = new ConfigurationUtil(args);
		
		
		MetricServiceConfiguration metricServiceConfiguration = configuration.get(MetricServiceConfiguration.class,defaultMetric);
		MetricService metricService = new MetricServiceImpl(metricServiceConfiguration);
		
		ExternalTaskConfiguration externalTaskConfiguration = configuration.get(ExternalTaskConfiguration.class);
		NotificationService notificationService = new CallExternalTaskNotificationService(externalTaskConfiguration,metricService); 
		
		
		CoffeeDatabase coffeedatabase = new CoffeeDatabase(configuration.getDataPath());
		
		coffeedatabase.read(-5);
		
		PlacesClientConfiguration placesClientConfiguration = configuration.get(PlacesClientConfiguration.class);

		CoffeeServiceImpl coffeeService = new CoffeeServiceImpl(coffeedatabase,new GeoDistanceServiceImpl(),new PlacesClient(placesClientConfiguration,metricService));
		
		
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
