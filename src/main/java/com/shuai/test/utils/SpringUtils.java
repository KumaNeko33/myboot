package com.shuai.test.utils;

import java.util.Locale;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @Title: SpringUtils.java
 * 
 * @Package  net.showcoo.utils
 * 
 * @Description: TODO(Utils - Spring)
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
@Component("springUtils")
@Lazy(false)
public final class SpringUtils implements ApplicationContextAware, DisposableBean {

	/** applicationContext */
	private static ApplicationContext applicationContext;

	/**
	 * 不可实例化
	 */
	private SpringUtils() {
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringUtils.applicationContext = applicationContext;
	}

	public void destroy() throws Exception {
		applicationContext = null;
	}

	/**
	 * 获取applicationContext
	 * 
	 * @return applicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取实例
	 * 
	 * @param name
	 *            Bean名称
	 * @return 实例
	 */
	public static Object getBean(String name) {
		Assert.hasText(name);
		return applicationContext.getBean(name);
	}

	/**
	 * 获取实例
	 * 
	 * @param name
	 *            Bean名称
	 * @param type
	 *            Bean类型
	 * @return 实例
	 */
	public static <T> T getBean(String name, Class<T> type) {
		Assert.hasText(name);
		Assert.notNull(type);
		return applicationContext.getBean(name, type);
	}

	/**
	 * 获取国际化消息
	 * 
	 * @param code
	 *            代码
	 * @param args
	 *            参数
	 * @return 国际化消息
	 */
	public static String getMessage(String code, Object... args) {
		LocaleResolver localeResolver = getBean("localeResolver", LocaleResolver.class);
		Locale locale = localeResolver.resolveLocale(null);
		return applicationContext.getMessage(code, args, locale);
	}

}
