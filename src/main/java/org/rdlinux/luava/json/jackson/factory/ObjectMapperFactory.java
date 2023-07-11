package org.rdlinux.luava.json.jackson.factory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.rdlinux.luava.json.jackson.deserializer.JsonBooleanDeserializer;
import org.rdlinux.luava.json.jackson.deserializer.JsonDateDeserializer;
import org.rdlinux.luava.json.jackson.deserializer.LocalDateTimeDeserializer;
import org.rdlinux.luava.json.jackson.serializer.LocalDateTimeSerializer;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ObjectMapperFactory {
    private static final String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";
    private static final String defaultSnakeKey = "defaultSnakeCase:";
    private static final String defaultKey = "default:";
    private static final ConcurrentMap<String, ObjectMapper> keyMapObjectMapper = new ConcurrentHashMap<>();

    private static void initUniversalConfig(ObjectMapper objectMapper) {
        objectMapper.findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Date.class, JsonDateDeserializer.getInstance());
        simpleModule.addDeserializer(Boolean.class, JsonBooleanDeserializer.getInstance());
        simpleModule.addSerializer(LocalDateTime.class, LocalDateTimeSerializer.getInstance());
        simpleModule.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.getInstance());
        objectMapper.registerModule(simpleModule);
    }

    public static ObjectMapper getDefaultObjectMapper() {
        return getDefaultObjectMapper(defaultDatePattern);
    }

    public static ObjectMapper getDefaultObjectMapper(String datePattern) {
        String key = defaultKey + datePattern;
        ObjectMapper objectMapper = keyMapObjectMapper.get(key);
        if (objectMapper == null) {
            synchronized (ObjectMapperFactory.class) {
                objectMapper = keyMapObjectMapper.get(key);
                if (objectMapper == null) {
                    objectMapper = new ObjectMapper();
                    initUniversalConfig(objectMapper);
                    objectMapper.setDateFormat(new SimpleDateFormat(datePattern));
                    keyMapObjectMapper.put(key, objectMapper);
                }
            }
        }
        return objectMapper;
    }

    public static ObjectMapper getDefaultSnakeCaseObjectMapper() {
        return getDefaultSnakeCaseObjectMapper(defaultDatePattern);
    }

    public static ObjectMapper getDefaultSnakeCaseObjectMapper(String datePattern) {
        String key = defaultSnakeKey + datePattern;
        ObjectMapper objectMapper = keyMapObjectMapper.get(key);
        if (objectMapper == null) {
            synchronized (ObjectMapperFactory.class) {
                objectMapper = keyMapObjectMapper.get(key);
                if (objectMapper == null) {
                    objectMapper = new ObjectMapper();
                    initUniversalConfig(objectMapper);
                    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
                    objectMapper.setDateFormat(new SimpleDateFormat(datePattern));
                    keyMapObjectMapper.put(key, objectMapper);
                }
            }
        }
        return objectMapper;
    }
}
