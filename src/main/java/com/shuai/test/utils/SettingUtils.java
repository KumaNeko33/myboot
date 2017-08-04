//package com.shuai.test.utils;
//
//import net.showcoo.CommonAttributes;
//import net.showcoo.EnumConverter;
//import net.showcoo.entity.Setting;
//import net.showcoo.entity.SystemConfig;
//import net.showcoo.service.RedisService;
//import net.showcoo.service.SystemConfigService;
//import org.apache.commons.beanutils.BeanUtilsBean;
//import org.apache.commons.beanutils.ConvertUtilsBean;
//import org.apache.commons.beanutils.Converter;
//import org.apache.commons.beanutils.converters.ArrayConverter;
//import org.apache.commons.beanutils.converters.DateConverter;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.Date;
//import java.util.List;
//
///**
// * @Title: SettingUtils.java
// *
// * @Package net.showcoo.utils
// *
// * @Description: TODO(Utils - 系统设置)
// *
// * @author ~(≧▽≦)/~ duerlatter@vip.qq.com
// *
// * @date 2014-11-27 下午14:30:09
// *
// * @version V1.0
// *
// * @Signature 怒发冲冠，凭阑处、潇潇雨歇。抬望眼、仰天长啸，壮怀激烈。三十功名尘与土，八千里路云和月。莫等闲，白了少年头，空悲切。
// *            靖康耻，犹未雪；臣子恨，何时灭。驾长车，踏破贺兰山缺。壮志饥餐胡虏肉，笑谈渴饮匈奴血。待从头、收拾旧山河，朝天阙。
// */
//public final class SettingUtils {
//
//	private static final RedisService redis = (RedisService) SpringUtils.getBean("redisServiceImpl");
//	private static final SystemConfigService systemConfigService = (SystemConfigService) SpringUtils.getBean("systemConfigServiceImpl");
//
//	/** BeanUtilsBean */
//	private static final BeanUtilsBean beanUtils;
//
//	static {
//		ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean() {
//			@Override
//			public String convert(Object value) {
//				if (value != null) {
//					Class<?> type = value.getClass();
//					if (type.isEnum() && super.lookup(type) == null) {
//						super.register(new EnumConverter(type), type);
//					} else if (type.isArray() && type.getComponentType().isEnum()) {
//						if (super.lookup(type) == null) {
//							ArrayConverter arrayConverter = new ArrayConverter(type, new EnumConverter(type.getComponentType()), 0);
//							arrayConverter.setOnlyFirstToString(false);
//							super.register(arrayConverter, type);
//						}
//						Converter converter = super.lookup(type);
//						return ((String) converter.convert(String.class, value));
//					}
//				}
//				return super.convert(value);
//			}
//
//			@SuppressWarnings("rawtypes")
//			@Override
//			public Object convert(String value, Class clazz) {
//				if (clazz.isEnum() && super.lookup(clazz) == null) {
//					super.register(new EnumConverter(clazz), clazz);
//				}
//				return super.convert(value, clazz);
//			}
//
//			@SuppressWarnings("rawtypes")
//			@Override
//			public Object convert(String[] values, Class clazz) {
//				if (clazz.isArray() && clazz.getComponentType().isEnum() && super.lookup(clazz.getComponentType()) == null) {
//					super.register(new EnumConverter(clazz.getComponentType()), clazz.getComponentType());
//				}
//				return super.convert(values, clazz);
//			}
//
//			@SuppressWarnings("rawtypes")
//			@Override
//			public Object convert(Object value, Class targetType) {
//				if (super.lookup(targetType) == null) {
//					if (targetType.isEnum()) {
//						super.register(new EnumConverter(targetType), targetType);
//					} else if (targetType.isArray() && targetType.getComponentType().isEnum()) {
//						ArrayConverter arrayConverter = new ArrayConverter(targetType, new EnumConverter(targetType.getComponentType()), 0);
//						arrayConverter.setOnlyFirstToString(false);
//						super.register(arrayConverter, targetType);
//					}
//				}
//				return super.convert(value, targetType);
//			}
//		};
//		DateConverter dateConverter = new DateConverter();
//		dateConverter.setPatterns(CommonAttributes.DATE_PATTERNS);
//		convertUtilsBean.register(dateConverter, Date.class);
//		beanUtils = new BeanUtilsBean(convertUtilsBean);
//	}
//
//	/**
//	 * 不可实例化
//	 */
//	private SettingUtils() {
//	}
//
//	/**
//	 * 获取系统设置
//	 *
//	 * @return 系统设置
//	 */
//	public static Setting get() {
//		byte[] cache = redis.get(Setting.CACHE_NAME.getBytes());
//		Setting setting;
//		if (cache != null) {
//			setting = (Setting) SerializeUtil.unserialize(cache);
//		} else {
//			setting = new Setting();
//			try {
//				List<SystemConfig> list = systemConfigService.findAll();
//				for (SystemConfig config : list) {
//					String name = config.getKey();
//					String value = config.getValue();
//					try {
//						beanUtils.setProperty(setting, name, value);
//					} catch (IllegalAccessException e) {
//						e.printStackTrace();
//					} catch (InvocationTargetException e) {
//						e.printStackTrace();
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			redis.set(Setting.CACHE_NAME, SerializeUtil.serialize(setting));
//		}
//		return setting;
//	}
//
//	/**
//	 * 设置系统设置
//	 *
//	 * @param setting
//	 *            系统设置
//	 */
//	public static void set(Setting setting) {
//		try {
//			List<SystemConfig> list = systemConfigService.findAll();
//			for (SystemConfig config : list) {
//				try {
//					String name = config.getKey();
//					String value = beanUtils.getProperty(setting, name);
//					config.setValue(value);
//				} catch (IllegalAccessException e) {
//				} catch (InvocationTargetException e) {
//				} catch (NoSuchMethodException e) {
//				}
//			}
//			systemConfigService.batchSave(list);
//			redis.set(Setting.CACHE_NAME, SerializeUtil.serialize(setting));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//}
