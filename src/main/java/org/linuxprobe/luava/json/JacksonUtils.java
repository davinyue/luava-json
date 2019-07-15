package org.linuxprobe.luava.json;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JacksonUtils {
	private static ObjectMapper defaultObjectMapper = new ObjectMapper();
	private static ObjectMapper defaultSnameCaseObjectMapper = new ObjectMapper();
	static {
		initUniversalConfig(defaultObjectMapper);
		initUniversalConfig(defaultSnameCaseObjectMapper);
		defaultSnameCaseObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
	}

	private static void initUniversalConfig(ObjectMapper objectMapper) {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
	}

	/**
	 * 把数据转换为json字符串
	 * 
	 * @param obj 输入数据
	 */
	public static String toJsonString(Object obj) {
		if (obj instanceof String) {
			return (String) obj;
		}
		try {
			if (obj instanceof InputStream) {
				Map<?, ?> map = defaultObjectMapper.readValue((InputStream) obj, Map.class);
				obj = map;
			}
			return defaultObjectMapper.writeValueAsString(obj);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 把数据转换为json字符串
	 * 
	 * @param obj 输入数据
	 */
	public static String toJsonString(Object obj, JsonFactory jf, DefaultSerializerProvider sp,
			DefaultDeserializationContext dc) {
		if (obj instanceof String) {
			return (String) obj;
		}
		ObjectMapper objectMapper = new ObjectMapper(jf, sp, dc);
		try {
			if (obj instanceof InputStream) {
				Map<?, ?> map = objectMapper.readValue((InputStream) obj, Map.class);
				obj = map;
			}
			return objectMapper.writeValueAsString(obj);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 把数据转换为json字符串
	 * 
	 * @param obj 输入数据
	 */
	public static String toJsonString(Object obj, JsonFactory jf) {
		if (obj instanceof String) {
			return (String) obj;
		}
		ObjectMapper objectMapper = new ObjectMapper(jf);
		try {
			if (obj instanceof InputStream) {
				Map<?, ?> map = objectMapper.readValue((InputStream) obj, Map.class);
				obj = map;
			}
			return objectMapper.writeValueAsString(obj);
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
		if (obj instanceof String) {
			return (String) obj;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		initUniversalConfig(objectMapper);
		objectMapper.setDateFormat(new SimpleDateFormat(datePattern));
		try {
			if (obj instanceof InputStream) {
				Map<?, ?> map = objectMapper.readValue((InputStream) obj, Map.class);
				obj = map;
			}
			return objectMapper.writeValueAsString(obj);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 把数据转换为蛇形命名json字符串
	 * 
	 * @param obj 输入数据
	 */
	public static String toSnameCaseJsonString(Object obj) {
		if (obj instanceof String) {
			return (String) obj;
		}
		try {
			if (obj instanceof InputStream) {
				Map<?, ?> map = defaultSnameCaseObjectMapper.readValue((InputStream) obj, Map.class);
				obj = map;
			}
			return defaultSnameCaseObjectMapper.writeValueAsString(obj);
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
	public static String toSnameCaseJsonString(Object obj, String datePattern) {
		if (obj instanceof String) {
			return (String) obj;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		initUniversalConfig(objectMapper);
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		objectMapper.setDateFormat(new SimpleDateFormat(datePattern));
		try {
			if (obj instanceof InputStream) {
				Map<?, ?> map = objectMapper.readValue((InputStream) obj, Map.class);
				obj = map;
			}
			return objectMapper.writeValueAsString(obj);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 把一个类型的数据转换为另一个类型的数据
	 * 
	 * @param data 输入数据
	 * @param type 转换的目标类型, 可传入com.fasterxml.jackson.databind.JavaType类型, 用于处理泛型
	 */
	public static <T> T conversion(Object data, Class<T> type) {
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = defaultObjectMapper.readValue((InputStream) data, Map.class);
				data = defaultObjectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = defaultObjectMapper.writeValueAsString(data);
			}
			T newData = defaultObjectMapper.readValue(stringValue, type);
			return newData;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 把一个类型的数据转换为另一个类型的数据
	 * 
	 * @param data        输入数据
	 * @param type        转换的目标类型, 可传入com.fasterxml.jackson.databind.JavaType类型,
	 *                    用于处理泛型
	 * @param datePattern 时间输出格式
	 */
	public static <T> T conversion(Object data, Class<T> type, String datePattern) {
		ObjectMapper objectMapper = new ObjectMapper();
		initUniversalConfig(objectMapper);
		objectMapper.setDateFormat(new SimpleDateFormat(datePattern));
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = objectMapper.readValue((InputStream) data, Map.class);
				data = objectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = objectMapper.writeValueAsString(data);
			}
			T newData = objectMapper.readValue(stringValue, type);
			return newData;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 把一个类型的数据转换为另一个类型的数据
	 * 
	 * @param data 输入数据
	 * @param type 转换的目标类型, 可传入com.fasterxml.jackson.databind.JavaType类型, 用于处理泛型
	 */
	public static <T> T conversion(Object data, Class<T> type, JsonFactory jf, DefaultSerializerProvider sp,
			DefaultDeserializationContext dc) {
		ObjectMapper objectMapper = new ObjectMapper(jf, sp, dc);
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = objectMapper.readValue((InputStream) data, Map.class);
				data = objectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = objectMapper.writeValueAsString(data);
			}
			T newData = objectMapper.readValue(stringValue, type);
			return newData;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 把一个类型的数据转换为另一个类型的数据
	 * 
	 * @param data 输入数据
	 * @param type 转换的目标类型, 可传入com.fasterxml.jackson.databind.JavaType类型, 用于处理泛型
	 */
	public static <T> T conversion(Object data, Class<T> type, JsonFactory jf) {
		ObjectMapper objectMapper = new ObjectMapper(jf);
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = objectMapper.readValue((InputStream) data, Map.class);
				data = objectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = objectMapper.writeValueAsString(data);
			}
			T newData = objectMapper.readValue(stringValue, type);
			return newData;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 把一个类型的数据转换为另一个类型的数据, 使用下划线风格
	 * 
	 * @param data 输入数据
	 * @param type 转换的目标类型, 可传入com.fasterxml.jackson.databind.JavaType类型, 用于处理泛型
	 */
	public static <T> T snameCaseConversion(Object data, Class<T> type) {
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = defaultSnameCaseObjectMapper.readValue((InputStream) data, Map.class);
				data = defaultSnameCaseObjectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = defaultSnameCaseObjectMapper.writeValueAsString(data);
			}
			T newData = defaultSnameCaseObjectMapper.readValue(stringValue, type);
			return newData;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 把一个类型的数据转换为另一个类型的数据, 使用下划线风格
	 * 
	 * @param data        输入数据
	 * @param type        转换的目标类型, 可传入com.fasterxml.jackson.databind.JavaType类型,
	 *                    用于处理泛型
	 * @param datePattern 时间输出格式
	 */
	public static <T> T snameCaseConversion(Object data, Class<T> type, String datePattern) {
		ObjectMapper objectMapper = new ObjectMapper();
		initUniversalConfig(objectMapper);
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		objectMapper.setDateFormat(new SimpleDateFormat(datePattern));
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = objectMapper.readValue((InputStream) data, Map.class);
				data = objectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = objectMapper.writeValueAsString(data);
			}
			T newData = objectMapper.readValue(stringValue, type);
			return newData;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 把一个类型的数据转换为另一个类型的数据
	 * 
	 * @param data 输入数据
	 * @param type 转换的目标类型, 可调用org.linuxprobe.luava.json.JacksonUtils.getJavaType获取,
	 *             用于处理泛型
	 */
	public static <T> T conversion(Object data, JavaType type) {
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = defaultObjectMapper.readValue((InputStream) data, Map.class);
				data = defaultObjectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = defaultObjectMapper.writeValueAsString(data);
			}
			T newData = defaultObjectMapper.readValue(stringValue, type);
			return newData;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 把一个类型的数据转换为另一个类型的数据
	 * 
	 * @param data        输入数据
	 * @param type        转换的目标类型,
	 *                    可调用org.linuxprobe.luava.json.JacksonUtils.getJavaType获取,
	 *                    用于处理泛型
	 * @param datePattern 时间输出格式
	 */
	public static <T> T conversion(Object data, JavaType type, String datePattern) {
		ObjectMapper objectMapper = new ObjectMapper();
		initUniversalConfig(objectMapper);
		objectMapper.setDateFormat(new SimpleDateFormat(datePattern));
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = objectMapper.readValue((InputStream) data, Map.class);
				data = objectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = objectMapper.writeValueAsString(data);
			}
			T newData = objectMapper.readValue(stringValue, type);
			return newData;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 把一个类型的数据转换为另一个类型的数据
	 * 
	 * @param data 输入数据
	 * @param type 转换的目标类型, 可调用org.linuxprobe.luava.json.JacksonUtils.getJavaType获取,
	 *             用于处理泛型
	 */
	public static <T> T conversion(Object data, JavaType type, JsonFactory jf, DefaultSerializerProvider sp,
			DefaultDeserializationContext dc) {
		ObjectMapper objectMapper = new ObjectMapper(jf, sp, dc);
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = objectMapper.readValue((InputStream) data, Map.class);
				data = objectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = objectMapper.writeValueAsString(data);
			}
			T newData = objectMapper.readValue(stringValue, type);
			return newData;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 把一个类型的数据转换为另一个类型的数据
	 * 
	 * @param data 输入数据
	 * @param type 转换的目标类型, 可调用org.linuxprobe.luava.json.JacksonUtils.getJavaType获取,
	 *             用于处理泛型
	 */
	public static <T> T conversion(Object data, JavaType type, JsonFactory jf) {
		ObjectMapper objectMapper = new ObjectMapper(jf);
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = objectMapper.readValue((InputStream) data, Map.class);
				data = objectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = objectMapper.writeValueAsString(data);
			}
			T newData = objectMapper.readValue(stringValue, type);
			return newData;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 把一个类型的数据转换为另一个类型的数据, 使用下划线风格
	 * 
	 * @param data 输入数据
	 * @param type 转换的目标类型, 可调用org.linuxprobe.luava.json.JacksonUtils.getJavaType获取,
	 *             用于处理泛型
	 */
	public static <T> T snameCaseConversion(Object data, JavaType type) {
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = defaultSnameCaseObjectMapper.readValue((InputStream) data, Map.class);
				data = defaultSnameCaseObjectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = defaultSnameCaseObjectMapper.writeValueAsString(data);
			}
			T newData = defaultSnameCaseObjectMapper.readValue(stringValue, type);
			return newData;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 把一个类型的数据转换为另一个类型的数据, 使用下划线风格
	 * 
	 * @param data        输入数据
	 * @param type        转换的目标类型,
	 *                    可调用org.linuxprobe.luava.json.JacksonUtils.getJavaType获取,
	 *                    用于处理泛型
	 * @param datePattern 时间输出格式
	 */
	public static <T> T snameCaseConversion(Object data, JavaType type, String datePattern) {
		ObjectMapper objectMapper = new ObjectMapper();
		initUniversalConfig(objectMapper);
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		objectMapper.setDateFormat(new SimpleDateFormat(datePattern));
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = objectMapper.readValue((InputStream) data, Map.class);
				data = objectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = objectMapper.writeValueAsString(data);
			}
			T newData = objectMapper.readValue(stringValue, type);
			return newData;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 获取泛型用于objectMapper转换的javaType
	 * 
	 * @param parametrized     类型
	 * @param parameterClasses 泛型类型
	 */
	public static JavaType getJavaType(Class<?> parametrized, Class<?>... parameterClasses) {
		return TypeFactory.defaultInstance().constructParametricType(parametrized, parameterClasses);
	}

	/**
	 * 获取泛型用于objectMapper转换的javaType
	 * 
	 * @param rawType        类型
	 * @param parameterTypes JavaType构造的类型
	 */
	public static JavaType getJavaType(Class<?> rawType, JavaType... parameterTypes) {
		return TypeFactory.defaultInstance().constructParametricType(rawType, parameterTypes);
	}

	/**
	 * 把一个类型的数据转换为另一个类型的数据
	 * 
	 * @param data 输入数据
	 * @param type 转换的目标类型, 用于处理泛型, eg: new
	 *             TypeReference&lt;List&lt;Integer&gt;&gt;() { },
	 *             将返回List&lt;Integer&gt
	 */
	public static <T> T conversion(Object data, TypeReference<?> type) {
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = defaultObjectMapper.readValue((InputStream) data, Map.class);
				data = defaultObjectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = defaultObjectMapper.writeValueAsString(data);
			}
			T newData = defaultObjectMapper.readValue(stringValue, type);
			return newData;
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
	public static <T> T conversion(Object data, TypeReference<?> type, String datePattern) {
		ObjectMapper objectMapper = new ObjectMapper();
		initUniversalConfig(objectMapper);
		objectMapper.setDateFormat(new SimpleDateFormat(datePattern));
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = objectMapper.readValue((InputStream) data, Map.class);
				data = objectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = objectMapper.writeValueAsString(data);
			}
			T newData = objectMapper.readValue(stringValue, type);
			return newData;
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
	public static <T> T conversion(Object data, TypeReference<?> type, JsonFactory jf, DefaultSerializerProvider sp,
			DefaultDeserializationContext dc) {
		ObjectMapper objectMapper = new ObjectMapper(jf, sp, dc);
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = objectMapper.readValue((InputStream) data, Map.class);
				data = objectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = objectMapper.writeValueAsString(data);
			}
			T newData = objectMapper.readValue(stringValue, type);
			return newData;
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
	public static <T> T conversion(Object data, TypeReference<?> type, JsonFactory jf) {
		ObjectMapper objectMapper = new ObjectMapper(jf);
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = objectMapper.readValue((InputStream) data, Map.class);
				data = objectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = objectMapper.writeValueAsString(data);
			}
			T newData = objectMapper.readValue(stringValue, type);
			return newData;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 把一个类型的数据转换为另一个类型的数据, 使用下划线风格
	 * 
	 * @param data 输入数据
	 * @param type 转换的目标类型, 用于处理泛型, eg: new
	 *             TypeReference&lt;List&lt;Integer&gt;&gt;() { },
	 *             将返回List&lt;Integer&gt
	 */
	public static <T> T snameCaseConversion(Object data, TypeReference<?> type) {
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = defaultSnameCaseObjectMapper.readValue((InputStream) data, Map.class);
				data = defaultSnameCaseObjectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = defaultSnameCaseObjectMapper.writeValueAsString(data);
			}
			T newData = defaultSnameCaseObjectMapper.readValue(stringValue, type);
			return newData;
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
	public static <T> T snameCaseConversion(Object data, TypeReference<?> type, String datePattern) {
		ObjectMapper objectMapper = new ObjectMapper();
		initUniversalConfig(objectMapper);
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		objectMapper.setDateFormat(new SimpleDateFormat(datePattern));
		try {
			if (data instanceof InputStream) {
				Map<?, ?> map = objectMapper.readValue((InputStream) data, Map.class);
				data = objectMapper.writeValueAsString(map);
			}
			String stringValue = null;
			if (data instanceof String) {
				stringValue = (String) data;
			} else {
				stringValue = objectMapper.writeValueAsString(data);
			}
			T newData = objectMapper.readValue(stringValue, type);
			return newData;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
