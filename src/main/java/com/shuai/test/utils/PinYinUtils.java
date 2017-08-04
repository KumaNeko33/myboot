package com.shuai.test.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * @Title: PinYinUtils.java
 * 
 * @Package net.showcoo.utils
 * 
 * @Description: TODO(拼音)
 * 
 * @author ~(≧▽≦)/~ duerlatter@vip.qq.com
 * 
 * @date 2015年11月12日 下午7:50:21
 * 
 * @version V1.0
 * 
 * @Signature 怒发冲冠，凭阑处、潇潇雨歇。抬望眼、仰天长啸，壮怀激烈。三十功名尘与土，八千里路云和月。莫等闲，白了少年头，空悲切。
 *            靖康耻，犹未雪；臣子恨，何时灭。驾长车，踏破贺兰山缺。壮志饥餐胡虏肉，笑谈渴饮匈奴血。待从头、收拾旧山河，朝天阙。
 */
public class PinYinUtils {

	private PinYinUtils() {
	}

	public static String getFirst(String arg) {
		try {
			String[] pinyins = getFirsts(arg);
			return pinyins[0].substring(0, 1);
		} catch (Exception e) {
			System.err.println(arg);
			return arg.substring(0, 1);
		}

	}

	public static String[] getFirsts(String arg) {
		try {
			char c = arg.charAt(0);
			HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
			format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
			format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
			return PinyinHelper.toHanyuPinyinStringArray(c, format);
		} catch (Exception e) {
			return null;
		}

	}
}
