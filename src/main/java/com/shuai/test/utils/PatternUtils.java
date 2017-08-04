package com.shuai.test.utils;

import java.util.regex.Pattern;

/**
 * 
 * @Title: PatternUtils.java
 * 
 * @Package net.showcoo.utils
 * 
 * @Description: TODO(正则工具类)
 * 
 * @author ~(≧▽≦)/~ duerlatter@vip.qq.com
 * 
 * @date 2016-11-12 下午2:08:11
 * 
 * @version V1.0
 * 
 * @Signature 怒发冲冠，凭阑处、潇潇雨歇。抬望眼、仰天长啸，壮怀激烈。三十功名尘与土，八千里路云和月。莫等闲，白了少年头，空悲切。
 *            靖康耻，犹未雪；臣子恨，何时灭。驾长车，踏破贺兰山缺。壮志饥餐胡虏肉，笑谈渴饮匈奴血。待从头、收拾旧山河，朝天阙。
 */
public class PatternUtils {

	/** 条码验证 */
	public static Pattern barCodePattern = Pattern.compile("^[ABCDEFGHIJKL]\\d{1}[A-Z0-9]{9}");
	public static Pattern barCodePattern2 = Pattern.compile("^[a-zA-Z]\\d{10}");
	/** 标签码验证 **/
	public static Pattern barCodePattern3 = Pattern.compile("^\\d{3}[A-L]\\d{8}");
	
	/**
	 * 验证条码是否符合格式
	 * 
	 * @param barCode
	 * 			条码
	 * @return
	 * 			true 符合
	 */
	public static boolean isBarCode(String barCode) {
		return barCodePattern.matcher(barCode).matches();
	}
	
	/**
	 * 验证标签码是否符合格式
	 * 
	 * @param barCode
	 * 			条码
	 * @return
	 * 			true 符合
	 */
	public static boolean isQBarCode(String barCode) {
		return barCodePattern3.matcher(barCode).matches();
	}

}