package de.frittenburger.coffee.impl;

import java.io.IOException;

import de.frittenburger.coffee.interfaces.NotificationService;

public class CallExternalTaskNotificationService implements NotificationService {

	private final ExternalTaskConfiguration configuration;

	public CallExternalTaskNotificationService(
			ExternalTaskConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public void sendMessage(String message) throws IOException {

		String[] cmdline = { configuration.getCommand() , message }; 
		Runtime.getRuntime().exec(cmdline);
		
	}

}
