package com.shuai.test.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

/**
 * @Title: JsonUtils.java
 * @Package net.showcoo.utils
 * @Description: TODO(Utils - JSON)
 * 
 * @author ~(≧▽≦)/~ duerlatter@vip.qq.com
 * 
 * @date 2015-06-09 下午16:11:09
 * 
 * @version V1.0
 * 
 * @Signature 怒发冲冠，凭阑处、潇潇雨歇。抬望眼、仰天长啸，壮怀激烈。三十功名尘与土，八千里路云和月。莫等闲，白了少年头，空悲切。 靖康耻，犹未雪；臣子恨，何时灭。驾长车，踏破贺兰山缺。壮志饥餐胡虏肉，笑谈渴饮匈奴血。待从头、收拾旧山河，朝天阙。
 */
public final class JsonUtils {

	/**
	 * 不可实例化
	 */
	private JsonUtils() {
	}

	/**
	 * 将对象转换为JSON字符串
	 * 
	 * @param value
	 *            对象
	 * @return JSOn字符串
	 */
	public static String toJson(Object value) {
		return JSON.toJSONString(value);
	}

	public static <T> String toJsonIncludes(T value, String... properties) {
		SimplePropertyPreFilter spp = new SimplePropertyPreFilter(value.getClass(), properties);
		return JSON.toJSONString(value, spp);
	}

	public static <T> String toJsonExcludes(T value, String... properties) {
		ExcludePropertyPreFilter epp = new ExcludePropertyPreFilter(value.getClass(), properties);
		return JSON.toJSONString(value, epp);
	}

	/**
	 * 将对象转换为JSON字符串
	 * 
	 * @param value
	 * @param arg
	 * @return
	 */
	public static String toJson(Object value, SerializerFeature arg) {
		return JSON.toJSONString(value, arg);
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param valueType
	 *            对象类型
	 * @return 对象
	 */
	public static <T> T toObject(String json, Class<T> valueType) {
		Assert.hasText(json);
		Assert.notNull(valueType);
		return JSON.parseObject(json, valueType);
	}

	/**
	 * 将JSON字符串转换对象数组
	 * 
	 * @param json
	 *            JSON字符串
	 * @param valueType
	 *            对象类型
	 * @return 对象数组
	 */
	public static <T> List<T> toList(String json, Class<T> valueType) {
		Assert.hasText(json);
		Assert.notNull(valueType);
		return JSON.parseArray(json, valueType);
	}

	/**
	 * 将对象转换为JSON流
	 * 
	 * @param writer
	 *            writer
	 * @param value
	 *            对象
	 */
	public static void writeValue(Writer writer, Object value) {
		try {
			writer.write(JSON.toJSONString(value));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查传入的json字符串内的条码是否重复
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @param barCode
	 *            条码
	 * @return true：不重复， false 重复条码
	 */
	public static boolean checkRepeated(String jsonStr, String barCode) {
		Integer number = jsonStr.replaceAll(barCode, "").length();
		if (number == (jsonStr.length() - barCode.length())) {
			return true;
		} else {
			return false;
		}
	}

	static class ExcludePropertyPreFilter implements PropertyPreFilter {
		private final Class<?> clazz;
		private final Set<String> excludes = new HashSet<String>();

		public ExcludePropertyPreFilter(String... properties) {
			this(null, properties);
		}

		public ExcludePropertyPreFilter(Class<?> clazz, String... properties) {
			super();
			this.clazz = clazz;
			for (String item : properties) {
				if (item != null) {
					this.excludes.add(item);
				}
			}
		}

		public Class<?> getClazz() {
			return clazz;
		}

		public boolean addExcludes(String property) {
			return excludes.add(property);
		}

		public Set<String> getExcludes() {
			return excludes;
		}

		public boolean apply(JSONSerializer serializer, Object source, String name) {
			if (source == null) {
				return true;
			}

			if (clazz != null && !clazz.isInstance(source)) {
				return true;
			}

			if (this.excludes.contains(name)) {
				return false;
			}
			return true;
		}
	}
}
