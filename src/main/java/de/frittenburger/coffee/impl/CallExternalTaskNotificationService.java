package de.frittenburger.coffee.impl;

import java.io.IOException;

import de.frittenburger.coffee.interfaces.MetricService;
import de.frittenburger.coffee.interfaces.NotificationService;
import de.frittenburger.coffee.model.ExternalTaskConfiguration;
import de.frittenburger.coffee.model.MetricException;

public class CallExternalTaskNotificationService implements NotificationService {

	private final ExternalTaskConfiguration configuration;
	private final MetricService metricService;

	public CallExternalTaskNotificationService(ExternalTaskConfiguration configuration,MetricService metricService) {
		this.configuration = configuration;
		this.metricService = metricService;
	}

	@Override
	public void sendMessage(String message) throws IOException, MetricException {

		metricService.permit(configuration.getName());

		String[] cmdline = { configuration.getCommand() , message }; 
		Runtime.getRuntime().exec(cmdline);
		
	}

}
