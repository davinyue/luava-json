package org.linuxprobe.luava.json.jackson.factory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.linuxprobe.luava.json.jackson.deserializer.JsonBooleanDeserializer;
import org.linuxprobe.luava.json.jackson.deserializer.JsonDateDeserializer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectMapperFactory {
    private static final String defaultDatePattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static Map<String, ObjectMapper> keyMapObjectMapper = new ConcurrentHashMap<>();

    private static void initUniversalConfig(ObjectMapper objectMapper) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Date.class, JsonDateDeserializer.getInstance());
        simpleModule.addDeserializer(Boolean.class, JsonBooleanDeserializer.getInstance());
        objectMapper.registerModule(simpleModule);
    }

    public static ObjectMapper getDefaultObjectMapper() {
        return getDefaultObjectMapper(defaultDatePattern);
    }

    public static ObjectMapper getDefaultObjectMapper(String datePattern) {
        ObjectMapper objectMapper = keyMapObjectMapper.get("default:" + datePattern);
        if (objectMapper == null) {
            synchronized ("loadDefaultObjectMapper:" + datePattern) {
                objectMapper = keyMapObjectMapper.get("default:" + datePattern);
                if (objectMapper == null) {
                    objectMapper = new ObjectMapper();
                    initUniversalConfig(objectMapper);
                    objectMapper.setDateFormat(new SimpleDateFormat(datePattern));
                    keyMapObjectMapper.put("default:" + datePattern, objectMapper);
                }
            }
        }
        return objectMapper;
    }

    public static ObjectMapper getDefaultSnakeCaseObjectMapper() {
        return getDefaultSnakeCaseObjectMapper(defaultDatePattern);
    }

    public static ObjectMapper getDefaultSnakeCaseObjectMapper(String datePattern) {
        ObjectMapper objectMapper = keyMapObjectMapper.get("defaultSnake:" + datePattern);
        if (objectMapper == null) {
            synchronized ("loadDefaultSnakeObjectMapper:" + datePattern) {
                objectMapper = keyMapObjectMapper.get("defaultSnake:" + datePattern);
                if (objectMapper == null) {
                    objectMapper = new ObjectMapper();
                    initUniversalConfig(objectMapper);
                    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
                    objectMapper.setDateFormat(new SimpleDateFormat(datePattern));
                    keyMapObjectMapper.put("defaultSnake:" + datePattern, objectMapper);
                }
            }
        }
        return objectMapper;
    }
}
