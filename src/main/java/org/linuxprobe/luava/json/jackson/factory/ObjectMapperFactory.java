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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ObjectMapperFactory {
    private static final String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";
    private static final String defaultSnakeKey = "defaultSnakeCase:";
    private static final String defaultKey = "default:";
    private static volatile ConcurrentMap<String, ObjectMapper> keyMapObjectMapper = new ConcurrentHashMap<>();

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
        ObjectMapper objectMapper = keyMapObjectMapper.get(defaultKey + datePattern);
        if (objectMapper == null) {
            synchronized ("loadDefaultObjectMapper:" + datePattern) {
                objectMapper = keyMapObjectMapper.get(defaultKey + datePattern);
                if (objectMapper == null) {
                    objectMapper = new ObjectMapper();
                    initUniversalConfig(objectMapper);
                    objectMapper.setDateFormat(new SimpleDateFormat(datePattern));
                    keyMapObjectMapper.put(defaultKey + datePattern, objectMapper);
                }
            }
        }
        return objectMapper;
    }

    public static ObjectMapper getDefaultSnakeCaseObjectMapper() {
        return getDefaultSnakeCaseObjectMapper(defaultDatePattern);
    }

    public static ObjectMapper getDefaultSnakeCaseObjectMapper(String datePattern) {
        ObjectMapper objectMapper = keyMapObjectMapper.get(defaultSnakeKey + datePattern);
        if (objectMapper == null) {
            synchronized ("loadDefaultSnakeObjectMapper:" + datePattern) {
                objectMapper = keyMapObjectMapper.get(defaultSnakeKey + datePattern);
                if (objectMapper == null) {
                    objectMapper = new ObjectMapper();
                    initUniversalConfig(objectMapper);
                    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
                    objectMapper.setDateFormat(new SimpleDateFormat(datePattern));
                    keyMapObjectMapper.put(defaultSnakeKey + datePattern, objectMapper);
                }
            }
        }
        return objectMapper;
    }
}
