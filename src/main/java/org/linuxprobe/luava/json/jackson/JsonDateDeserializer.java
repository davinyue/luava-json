package org.linuxprobe.luava.json.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.linuxprobe.luava.convert.impl.StringToDate;

import java.io.IOException;
import java.util.Date;

public class JsonDateDeserializer extends JsonDeserializer<Date> {
    private static final StringToDate stringToDate = new StringToDate();

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String dateStr = jp.getText();
        return stringToDate.convert(dateStr);
    }
}
