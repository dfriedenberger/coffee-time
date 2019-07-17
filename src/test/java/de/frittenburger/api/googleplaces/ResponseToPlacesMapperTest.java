package de.frittenburger.api.googleplaces;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Function;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.api.googleplaces.impl.ResponseToPlacesMapper;
import de.frittenburger.api.googleplaces.model.Place;

public class ResponseToPlacesMapperTest {

	@Test
	public void test() throws IOException {
		
		
		
		List<String> lines = Files.readAllLines(new File(getClass().getClassLoader().getResource("googleplaces/results.txt").getFile()).toPath());
		
		Function<JsonNode, Place> mapper = new ResponseToPlacesMapper();

		
		for(String line : lines)
		{
			JsonNode node = new ObjectMapper().readTree(line);
			//String text = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(node);
			System.out.println(node.get("types"));

			Place place = mapper.apply(node);
			assertNotNull(place);
		}
	}

}
