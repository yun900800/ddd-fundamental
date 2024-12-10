package org.ddd.fundamental.day.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ddd.fundamental.day.Instants;

import java.io.IOException;
import java.time.Instant;

public class CustomInstantSerializer extends JsonSerializer<Instant> {
    @Override
    public void serialize(Instant instant, JsonGenerator json, SerializerProvider provider) throws IOException {
        json.writeString(Instants.FORMATTER.format(instant));
    }
}
