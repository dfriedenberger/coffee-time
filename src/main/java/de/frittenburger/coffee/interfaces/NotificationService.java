package de.frittenburger.coffee.interfaces;

import java.io.IOException;

import de.frittenburger.coffee.model.MetricException;

public interface NotificationService {

	void sendMessage(String message) throws IOException, MetricException;

}
