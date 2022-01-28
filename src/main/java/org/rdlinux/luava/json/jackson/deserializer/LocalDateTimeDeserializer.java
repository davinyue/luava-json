package org.rdlinux.luava.json.jackson.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.rdlinux.luava.json.jackson.serializer.LocalDateTimeSerializer;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    private static final LocalDateTimeDeserializer instance = new LocalDateTimeDeserializer();

    private LocalDateTimeDeserializer() {
    }

    public static LocalDateTimeDeserializer getInstance() {
        return instance;
    }

    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        String source = jp.getText();
        if (source == null || source.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(source, LocalDateTimeSerializer.dateTimeFormatter);
    }
}
