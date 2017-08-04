package com.shuai.test.utils;

/**
 * @Title: EncryptionStringUtil.java
 * 
 * @Package net.showcoo.utils
 * 
 * @Description: TODO(加密字符串处理器)
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

public class EncryptionStringUtil {

	private final static String EMAIL_SIGN = "@";

	/**
	 * 禁止序列化
	 */
	private EncryptionStringUtil() {
	}

	public static String email(String email) {
		if (email.indexOf(EMAIL_SIGN) < 0)
			return null;
		String prefix = email.substring(0, email.indexOf(EMAIL_SIGN));
		String suffix = email.substring(email.indexOf(EMAIL_SIGN));

		int length = prefix.length();

		String encry = "";
		switch (length) {
		case 1:
			encry = prefix;
			break;
		case 2:
			encry = prefix.substring(0, 1) + getHidden(1);
			break;
		case 3:
			encry = prefix.substring(0, 1) + getHidden(1) + prefix.substring(1, 2);
			break;
		case 4:
			encry = prefix.substring(0, 2) + getHidden(1) + prefix.substring(3);
			break;

		default:
			encry = prefix.substring(0, 2) + getHidden(length - 4) + prefix.substring(length - 2);
			break;
		}
		return encry + suffix;

	}

	private static String getHidden(int count) {
		StringBuffer sbuffer = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sbuffer.append("*");
		}
		return sbuffer.toString();
	}
}
