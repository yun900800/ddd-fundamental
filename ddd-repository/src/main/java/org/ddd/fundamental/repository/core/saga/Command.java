package org.ddd.fundamental.repository.core.saga;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Command extends Message {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Class<? extends Command> from(String json) throws JsonProcessingException, ClassNotFoundException {
        JsonNode jsonNode = objectMapper.readValue(json, JsonNode.class);
        String className = jsonNode.get("name").asText();
        Command command = (Command)objectMapper.readValue(json, Class.forName(className));
        return command.getClass();
    }
}
