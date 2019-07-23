package org.linuxprobe.luava.json.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.linuxprobe.luava.convert.impl.StringToBoolean;

import java.io.IOException;

/**
 * 自定义字符串反序列号成boolean
 */
public class JsonBooleanDeserializer extends JsonDeserializer<Boolean> {
    private static final StringToBoolean stringToBoolean = new StringToBoolean();

    @Override
    public Boolean deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String dateStr = jp.getText();
        return stringToBoolean.convert(dateStr);
    }
}
