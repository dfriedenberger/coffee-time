package de.frittenburger.coffee.interfaces;

import java.io.IOException;

public interface NotificationService {

	void sendMessage(String message) throws IOException;

}
