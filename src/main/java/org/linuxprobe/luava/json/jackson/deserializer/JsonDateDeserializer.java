package org.linuxprobe.luava.json.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateDeserializer extends JsonDeserializer<Date> {
    private static final JsonDateDeserializer instance = new JsonDateDeserializer();

    private JsonDateDeserializer() {
    }

    public static JsonDateDeserializer getInstance() {
        return instance;
    }

    private SimpleDateFormat getSimpleDateFormat(String dateString) {
        if (dateString.matches("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}T[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}\\.[0-9]{1,3}Z$")) {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        } else if (dateString.matches("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}T[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}\\.[0-9]{1,3}\\+[0-9]{4}$")
                || dateString.matches("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}T[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}\\.[0-9]{1,3}\\sUTC$")) {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        } else if (dateString.matches("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}T[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}\\.[0-9]{1,3}$")) {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        } else if (dateString.matches("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}\\s[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}\\.[0-9]{1,3}$")) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        } else if (dateString.matches("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}\\s[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}$")) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if (dateString.matches("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$")) {
            return new SimpleDateFormat("yyyy-MM-dd");
        } else if (dateString.matches("^[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}$")) {
            return new SimpleDateFormat("MM/dd/yyyy");
        } else if (dateString.matches("^[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}\\s[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}$")) {
            return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        } else {
            return null;
        }
    }

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String source = jp.getText();
        if (source == null || source.trim().isEmpty()) {
            return null;
        }
        if (source.matches("^[0-9]+$")) {
            return new Date(Long.parseLong(source));
        }
        SimpleDateFormat simpleDateFormat = this.getSimpleDateFormat(source);
        if (simpleDateFormat == null) {
            return null;
        }
        try {
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
