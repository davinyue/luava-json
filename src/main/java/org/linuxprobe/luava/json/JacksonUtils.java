package org.linuxprobe.luava.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.linuxprobe.luava.json.jackson.factory.ObjectMapperFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class JacksonUtils {
    /**
     * 获取泛型用于objectMapper转换的javaType
     *
     * @param type 类型, 可使用{@link Class#getGenericSuperclass()}获取
     */
    public static JavaType getJavaType(Type type) {
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            Class<?> rowClass = (Class<?>) ((ParameterizedType) type).getRawType();
            JavaType[] javaTypes = new JavaType[actualTypeArguments.length];
            for (int i = 0; i < actualTypeArguments.length; i++) {
                javaTypes[i] = getJavaType(actualTypeArguments[i]);
            }
            return TypeFactory.defaultInstance().constructParametricType(rowClass, javaTypes);
        } else {
            Class<?> cla = (Class<?>) type;
            return TypeFactory.defaultInstance().constructParametricType(cla, new JavaType[0]);
        }
    }

    private static Object inputStreamToString(Object obj) throws IOException {
        if (obj instanceof InputStream) {
            return StreamUtils.copyToString((InputStream) obj, StandardCharsets.UTF_8);
        } else {
            return obj;
        }
    }

    /**
     * 把数据转换为json字符串
     *
     * @param obj 输入数据
     */
    public static String toJsonString(Object obj) {
        try {
            obj = inputStreamToString(obj);
            return ObjectMapperFactory.getDefaultObjectMapper().writeValueAsString(obj);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 把数据转换为json字符串
     *
     * @param obj         输入数据
     * @param datePattern 时间输出格式
     */
    public static String toJsonString(Object obj, String datePattern) {
        try {
            obj = inputStreamToString(obj);
            return ObjectMapperFactory.getDefaultObjectMapper(datePattern).writeValueAsString(obj);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 把数据转换为蛇形命名json字符串
     *
     * @param obj 输入数据
     */
    public static String toSnakeCaseJsonString(Object obj) {
        try {
            obj = inputStreamToString(obj);
            return ObjectMapperFactory.getDefaultSnakeCaseObjectMapper().writeValueAsString(obj);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 把数据转换为蛇形命名json字符串
     *
     * @param obj         输入数据
     * @param datePattern 时间输出格式
     */
    public static String toSnakeCaseJsonString(Object obj, String datePattern) {
        try {
            obj = inputStreamToString(obj);
            return ObjectMapperFactory.getDefaultSnakeCaseObjectMapper(datePattern).writeValueAsString(obj);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static String conversionBefore(Object data, ObjectMapper objectMapper) throws IOException {
        data = inputStreamToString(data);
        String stringValue = null;
        if (data instanceof String) {
            stringValue = (String) data;
        } else {
            stringValue = objectMapper.writeValueAsString(data);
        }
        return stringValue;
    }

    /**
     * 把一个类型的数据转换为另一个类型的数据
     *
     * @param data 输入数据
     * @param type 转换的目标类型
     */
    public static <T> T conversion(Object data, Class<T> type) {
        ObjectMapper objectMapper = ObjectMapperFactory.getDefaultObjectMapper();
        try {
            String stringValue = conversionBefore(data, objectMapper);
            return objectMapper.readValue(stringValue, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 把一个类型的数据转换为另一个类型的数据
     *
     * @param data        输入数据
     * @param type        转换的目标类型
     *                    用于处理泛型
     * @param datePattern 时间输出格式
     */
    public static <T> T conversion(Object data, Class<T> type, String datePattern) {
        ObjectMapper objectMapper = ObjectMapperFactory.getDefaultObjectMapper(datePattern);
        try {
            String stringValue = conversionBefore(data, objectMapper);
            return objectMapper.readValue(stringValue, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 把一个类型的数据转换为另一个类型的数据, 使用下划线风格
     *
     * @param data 输入数据
     * @param type 转换的目标类型
     */
    public static <T> T snakeCaseConversion(Object data, Class<T> type) {
        ObjectMapper objectMapper = ObjectMapperFactory.getDefaultSnakeCaseObjectMapper();
        try {
            String stringValue = conversionBefore(data, objectMapper);
            return objectMapper.readValue(stringValue, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 把一个类型的数据转换为另一个类型的数据, 使用下划线风格
     *
     * @param data        输入数据
     * @param type        转换的目标类型
     *                    用于处理泛型
     * @param datePattern 时间输出格式
     */
    public static <T> T snakeCaseConversion(Object data, Class<T> type, String datePattern) {
        ObjectMapper objectMapper = ObjectMapperFactory.getDefaultSnakeCaseObjectMapper(datePattern);
        try {
            String stringValue = conversionBefore(data, objectMapper);
            return objectMapper.readValue(stringValue, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 把一个类型的数据转换为另一个类型的数据
     *
     * @param data 输入数据
     * @param type 转换的目标类型,用于处理泛型
     */
    public static <T> T conversion(Object data, JavaType type) {
        ObjectMapper objectMapper = ObjectMapperFactory.getDefaultObjectMapper();
        try {
            String stringValue = conversionBefore(data, objectMapper);
            return objectMapper.readValue(stringValue, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 把一个类型的数据转换为另一个类型的数据
     *
     * @param data        输入数据
     * @param type        转换的目标类型,用于处理泛型
     * @param datePattern 时间输出格式
     */
    public static <T> T conversion(Object data, JavaType type, String datePattern) {
        ObjectMapper objectMapper = ObjectMapperFactory.getDefaultObjectMapper(datePattern);
        try {
            String stringValue = conversionBefore(data, objectMapper);
            return objectMapper.readValue(stringValue, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 把一个类型的数据转换为另一个类型的数据, 使用下划线风格
     *
     * @param data 输入数据
     * @param type 转换的目标类型, 用于处理泛型
     */
    public static <T> T snakeCaseConversion(Object data, JavaType type) {
        ObjectMapper objectMapper = ObjectMapperFactory.getDefaultSnakeCaseObjectMapper();
        try {
            String stringValue = conversionBefore(data, objectMapper);
            return objectMapper.readValue(stringValue, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 把一个类型的数据转换为另一个类型的数据, 使用下划线风格
     *
     * @param data        输入数据
     * @param type        转换的目标类型,用于处理泛型
     * @param datePattern 时间输出格式
     */
    public static <T> T snakeCaseConversion(Object data, JavaType type, String datePattern) {
        ObjectMapper objectMapper = ObjectMapperFactory.getDefaultSnakeCaseObjectMapper(datePattern);
        try {
            String stringValue = conversionBefore(data, objectMapper);
            return objectMapper.readValue(stringValue, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }


    /**
     * 把一个类型的数据转换为另一个类型的数据
     *
     * @param data 输入数据
     * @param type 转换的目标类型, 用于处理泛型, eg: new
     *             TypeReference&lt;List&lt;Integer&gt;&gt;() { },
     *             将返回List&lt;Integer&gt
     */
    public static <T> T conversion(Object data, TypeReference<T> type) {
        ObjectMapper objectMapper = ObjectMapperFactory.getDefaultObjectMapper();
        try {
            String stringValue = conversionBefore(data, objectMapper);
            return objectMapper.readValue(stringValue, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 把一个类型的数据转换为另一个类型的数据
     *
     * @param data        输入数据
     * @param type        转换的目标类型, 用于处理泛型, eg: new
     *                    TypeReference&lt;List&lt;Integer&gt;&gt;() { },
     *                    将返回List&lt;Integer&gt
     * @param datePattern 时间输出格式
     */
    public static <T> T conversion(Object data, TypeReference<T> type, String datePattern) {
        ObjectMapper objectMapper = ObjectMapperFactory.getDefaultObjectMapper(datePattern);
        try {
            String stringValue = conversionBefore(data, objectMapper);
            return objectMapper.readValue(stringValue, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 把一个类型的数据转换为另一个类型的数据
     *
     * @param data 输入数据
     * @param type 转换的目标类型, 用于处理泛型
     */
    public static <T> T conversion(Object data, Type type) {
        return conversion(data, getJavaType(type));
    }

    /**
     * 把一个类型的数据转换为另一个类型的数据
     *
     * @param data        输入数据
     * @param type        转换的目标类型, 用于处理泛型
     * @param datePattern 时间输出格式
     */
    public static <T> T conversion(Object data, Type type, String datePattern) {
        return conversion(data, getJavaType(type), datePattern);
    }

    /**
     * 把一个类型的数据转换为另一个类型的数据, 使用下划线风格
     *
     * @param data 输入数据
     * @param type 转换的目标类型, 用于处理泛型, eg: new
     *             TypeReference&lt;List&lt;Integer&gt;&gt;() { },
     *             将返回List&lt;Integer&gt
     */
    public static <T> T snakeCaseConversion(Object data, TypeReference<T> type) {
        ObjectMapper objectMapper = ObjectMapperFactory.getDefaultSnakeCaseObjectMapper();
        try {
            String stringValue = conversionBefore(data, objectMapper);
            return objectMapper.readValue(stringValue, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 把一个类型的数据转换为另一个类型的数据, 使用下划线风格
     *
     * @param data        输入数据
     * @param type        转换的目标类型, 用于处理泛型, eg: new
     *                    TypeReference&lt;List&lt;Integer&gt;&gt;() { },
     *                    将返回List&lt;Integer&gt
     * @param datePattern 时间输出格式
     */
    public static <T> T snakeCaseConversion(Object data, TypeReference<T> type, String datePattern) {
        ObjectMapper objectMapper = ObjectMapperFactory.getDefaultSnakeCaseObjectMapper(datePattern);
        try {
            String stringValue = conversionBefore(data, objectMapper);
            return objectMapper.readValue(stringValue, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 把一个类型的数据转换为另一个类型的数据, 使用下划线风格
     *
     * @param data 输入数据
     * @param type 转换的目标类型, 用于处理泛型
     */
    public static <T> T snakeCaseConversion(Object data, Type type) {
        return snakeCaseConversion(data, getJavaType(type));
    }

    /**
     * 把一个类型的数据转换为另一个类型的数据, 使用下划线风格
     *
     * @param data        输入数据
     * @param type        转换的目标类型, 用于处理泛型
     * @param datePattern 时间输出格式
     */
    public static <T> T snakeCaseConversion(Object data, Type type, String datePattern) {
        return snakeCaseConversion(data, getJavaType(type), datePattern);
    }
}
