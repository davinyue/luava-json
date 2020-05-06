package org.linuxprobe.luava.json.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * 自定义字符串反序列号成boolean
 */
public class JsonBooleanDeserializer extends JsonDeserializer<Boolean> {
    private static JsonBooleanDeserializer instance = new JsonBooleanDeserializer();

    private JsonBooleanDeserializer() {
    }

    public static JsonBooleanDeserializer getInstance() {
        return instance;
    }

    @Override
    public Boolean deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String source = jp.getText();
        if (source == null) {
            return null;
        } else if (source.toLowerCase().equals("yes") || source.toLowerCase().equals("true") || source.toLowerCase().equals("是") || source.equals("1")) {
            return true;
        } else if (source.toLowerCase().equals("no") || source.toLowerCase().equals("false") || source.toLowerCase().equals("否") || source.equals("0")) {
            return false;
        } else {
            throw new IllegalArgumentException(source + " cat not case to boolean");
        }
    }
}
