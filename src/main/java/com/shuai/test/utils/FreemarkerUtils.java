package com.shuai.test.utils;

/**
 * @Title: FreemarkerUtils.java
 * 
 * @Package  net.showcoo.utils
 * 
 * @Description: TODO(Utils - Freemarker)
 * 
 * @author ~(≧▽≦)/~ duerlatter@vip.qq.com
 * 
 * @date 2015-06-09 下午16:11:09
 * 
 * @version V1.0
 * 
 * @Signature 怒发冲冠，凭阑处、潇潇雨歇。抬望眼、仰天长啸，壮怀激烈。三十功名尘与土，八千里路云和月。莫等闲，白了少年头，空悲切。
 *            靖康耻，犹未雪；臣子恨，何时灭。驾长车，踏破贺兰山缺。壮志饥餐胡虏肉，笑谈渴饮匈奴血。待从头、收拾旧山河，朝天阙。
 */
@SuppressWarnings("unchecked")
public final class FreemarkerUtils {

//	/** ConvertUtilsBean */
//	private static final ConvertUtilsBean convertUtils;
//
//	static {
//		convertUtils = new ConvertUtilsBean() {
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
//
//		DateConverter dateConverter = new DateConverter();
//		dateConverter.setPatterns(CommonAttributes.DATE_PATTERNS);
//		convertUtils.register(dateConverter, Date.class);
//	}
//
//	/**
//	 * 不可实例化
//	 */
//	private FreemarkerUtils() {
//	}
//
//	/**
//	 * 解析字符串模板
//	 *
//	 * @param template
//	 *            字符串模板
//	 * @param model
//	 *            数据
//	 * @return 解析后内容
//	 */
//	public static String process(String template, Map<String, ?> model) throws IOException, TemplateException {
//		Configuration configuration = null;
//		ApplicationContext applicationContext = SpringUtils.getApplicationContext();
//		if (applicationContext != null) {
//			FreeMarkerConfigurer freeMarkerConfigurer = SpringUtils.getBean("freeMarkerConfigurer", FreeMarkerConfigurer.class);
//			if (freeMarkerConfigurer != null) {
//				configuration = freeMarkerConfigurer.getConfiguration();
//			}
//		}
//		return process(template, model, configuration);
//	}
//
//	/**
//	 * 解析字符串模板
//	 *
//	 * @param template
//	 *            字符串模板
//	 * @param model
//	 *            数据
//	 * @param configuration
//	 *            配置
//	 * @return 解析后内容
//	 */
//	public static String process(String template, Map<String, ?> model, Configuration configuration) throws IOException, TemplateException {
//		if (template == null) {
//			return null;
//		}
//		if (configuration == null) {
//			configuration = new Configuration();
//		}
//		StringWriter out = new StringWriter();
//		new Template("template", new StringReader(template), configuration).process(model, out);
//		return out.toString();
//	}
//
//	/**
//	 * 获取参数
//	 *
//	 * @param name
//	 *            名称
//	 * @param type
//	 *            类型
//	 * @param params
//	 *            参数
//	 * @return 参数,若不存在则返回null
//	 */
//	public static <T> T getParameter(String name, Class<T> type, Map<String, TemplateModel> params) throws TemplateModelException {
//		Assert.hasText(name);
//		Assert.notNull(type);
//		Assert.notNull(params);
//		TemplateModel templateModel = params.get(name);
//		if (templateModel == null) {
//			return null;
//		}
//		Object value = DeepUnwrap.unwrap(templateModel);
//		return (T) convertUtils.convert(value, type);
//	}
//
//	/**
//	 * 获取变量
//	 *
//	 * @param name
//	 *            名称
//	 * @param env
//	 *            Environment
//	 * @return 变量
//	 */
//	public static TemplateModel getVariable(String name, Environment env) throws TemplateModelException {
//		Assert.hasText(name);
//		Assert.notNull(env);
//		return env.getVariable(name);
//	}
//
//	/**
//	 * 设置变量
//	 *
//	 * @param name
//	 *            名称
//	 * @param value
//	 *            变量值
//	 * @param env
//	 *            Environment
//	 */
//	public static void setVariable(String name, Object value, Environment env) throws TemplateException {
//		Assert.hasText(name);
//		Assert.notNull(env);
//		if (value instanceof TemplateModel) {
//			env.setVariable(name, (TemplateModel) value);
//		} else {
//			env.setVariable(name, ObjectWrapper.BEANS_WRAPPER.wrap(value));
//		}
//	}
//
//	/**
//	 * 设置变量
//	 *
//	 * @param variables
//	 *            变量
//	 * @param env
//	 *            Environment
//	 */
//	public static void setVariables(Map<String, Object> variables, Environment env) throws TemplateException {
//		Assert.notNull(variables);
//		Assert.notNull(env);
//		for (Entry<String, Object> entry : variables.entrySet()) {
//			String name = entry.getKey();
//			Object value = entry.getValue();
//			if (value instanceof TemplateModel) {
//				env.setVariable(name, (TemplateModel) value);
//			} else {
//				env.setVariable(name, ObjectWrapper.BEANS_WRAPPER.wrap(value));
//			}
//		}
//	}

}