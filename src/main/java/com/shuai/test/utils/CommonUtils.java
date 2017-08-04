package com.shuai.test.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.showcoo.HttpUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * @Title: CommonUtils.java
 * 
 * @Package net.showcoo.utils
 * 
 * @Description: 公共工具类
 * 
 * @author mapengwei
 * 
 * @date 2015年11月17日 上午11:53:43
 * 
 * @version V1.0
 * 
 */
public class CommonUtils {

	/**
	 * 校验银行卡卡号是否正确
	 * 
	 * @param bankNum
	 *            银行卡卡号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isBankCardNum(String bankNum) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("_input_charset", "utf-8");
		parameterMap.put("cardNo", bankNum);
		parameterMap.put("cardBinCheck", true);
		String res = HttpUtils.get("https://ccdcapi.alipay.com/validateAndCacheCardInfo.json", parameterMap);
		Map<String, Object> info = JsonUtils.toObject(res, Map.class);
		if (null != info && info.get("validated") != null && "true".equals(info.get("validated").toString())) {
			return true;
		}
		return false;
	}

	/**
	 * 获取汉字拼音首字母
	 * 
	 * @param arg
	 *            要获取拼音的汉字
	 * @param index
	 *            多音字选择，选择第index的拼音，下标从0开始
	 * @return
	 */
	public static String getInitials(String arg, int index) {
		try {
			if (arg.matches("[a-zA-Z]+")) {
				return arg.substring(0, 1).toUpperCase();
			}
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
			Matcher m = p.matcher(arg);
			if (!m.find()) {
				return arg.substring(0, 1);
			}
			index = index > 0 ? index - 1 : index;
			return getCharactersPinyin(arg)[index].substring(0, 1);
		} catch (NullPointerException e) {
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取第一个汉字拼音，返回所有发音集合
	 * 
	 * @param arg
	 *            输入汉字
	 * @return 返回所有发音集合（包含多音字）
	 */
	public static String[] getCharactersPinyin(String arg) {
		try {
			char c = arg.charAt(0);
			HanyuPinyinOutputFormat format = getPinyinOutPutFormat(HanyuPinyinCaseType.UPPERCASE, HanyuPinyinToneType.WITHOUT_TONE, HanyuPinyinVCharType.WITH_V);
			String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c, format);
			return pinyins;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 初始化拼音输出格式化实例
	 * 
	 * @param caseType
	 *            大小写格式
	 * @param toneType
	 *            声调格式 :
	 *            <p>
	 *            WITH_TONE_NUMBER 用数字表示声调，例如：liu2
	 *            </p>
	 *            <p>
	 *            WITHOUT_TONE 无声调表示，例如：liu
	 *            </p>
	 *            <p>
	 *            WITH_TONE_MARK 用声调符号表示，例如：liú
	 *            </p>
	 * @param vCharType
	 *            特殊拼音ü的显示格式 :
	 *            <p>
	 *            WITH_U_AND_COLON 以U和一个冒号表示该拼音，例如：lu
	 *            </p>
	 *            <p>
	 *            WITH_V 以V表示该字符，例如：lv
	 *            </p>
	 *            <p>
	 *            WITH_U_UNICODE 以ü表示
	 *            </p>
	 * @return
	 */
	private static HanyuPinyinOutputFormat getPinyinOutPutFormat(HanyuPinyinCaseType caseType, HanyuPinyinToneType toneType, HanyuPinyinVCharType vCharType) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(caseType);
		format.setToneType(toneType);
		format.setVCharType(vCharType);
		return format;
	}

	/**
	 * 格式化货币
	 * 
	 * @param money
	 *            金额
	 * @param count
	 *            长度
	 * @return
	 */
	public static String BigDecimalFormat(BigDecimal money, Integer count) {
		if (money == null) {
			return "0.00";
		}
		if (count == null || count < 1) {
			count = 2;
		}
		return money.setScale(count, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	/**
	 * 将null替换为-
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceNull(String str, String rep){
		if(str == null){
			return rep;
		}
		return str;
	}

}