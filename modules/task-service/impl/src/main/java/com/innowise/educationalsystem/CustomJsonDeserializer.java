package com.innowise.educationalsystem;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.innowise.educationalsystem.document.Task;

import java.io.IOException;

public class CustomJsonDeserializer extends JsonDeserializer<Task<?, ?, ?>> {
    @Override
    public Task<?, ?, ?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        //return new SomeTask()
        return null;
    }
}
