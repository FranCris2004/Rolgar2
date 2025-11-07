package src.main.java.org.thegoats.rolgar2.game;

import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DifficultyDataLoader {
    public static void load(String path) {
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode data = mapper.readTree(new File(path));
		int healthfloor = data.get("healthFloor").asInt();
	}
}
