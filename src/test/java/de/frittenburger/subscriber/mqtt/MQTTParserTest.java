package de.frittenburger.subscriber.mqtt;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import org.junit.Test;

import de.frittenburger.subscriber.mqtt.impl.MQTTParser;
import de.frittenburger.subscriber.mqtt.model.MqttElement;

public class MQTTParserTest {

	@Test
	public void test() throws IOException {
		
		
		MQTTParser parser = new MQTTParser();

		List<String> lines = Files.readAllLines(new File(getClass().getClassLoader().getResource("mqtt/data.txt").getFile()).toPath(), StandardCharsets.ISO_8859_1);

		assertTrue(lines.size() > 0);
		
		for(String line : lines)
		{
			MqttElement obj = parser.parse(line);
			assertNotNull(obj);	
		}
	}

}
