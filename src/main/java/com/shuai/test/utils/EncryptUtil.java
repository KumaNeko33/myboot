package com.shuai.test.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @Title: EncryptUtil.java
 * 
 * @Package com.zcckj.sso.utils
 * 
 * @Description: md5加密
 * 
 * @author mapengwei
 * 
 * @date 2016年5月10日 下午2:18:26
 * 
 * @version V1.0
 * 
 */
public class EncryptUtil {
	
	/**
	 * 第二次md5 
	 * @param input
	 * @param salt
	 * @return
	 */
	public static String encryptMd5(String input, String salt) {
		input = input.toLowerCase();
		try {
			int middle = input.length() / 2;
			byte[] result = MessageDigest.getInstance("MD5").digest((input.substring(0, middle) + salt + input.substring(middle)).getBytes());
			StringBuilder strBuilder = new StringBuilder(result.length * 2);
			for (byte b : result) {
				String s = Integer.toHexString(b & 0x00FF);
				if (1 == s.length()) {
					strBuilder.append('0');
				}
				strBuilder.append(s);
			}
			return strBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
    /**
     * 密码一次加密
     * @param input 明文密码
     * @return md5后密码
     */
    public static String encryptMd5(String input) {
        try {
            byte[] result = MessageDigest.getInstance("MD5").digest(input.getBytes("UTF-8"));
            StringBuilder strBuilder = new StringBuilder(result.length * 2);
            for (byte b : result) {
                String s = Integer.toHexString(b & 0x00FF);
                if (1 == s.length()) {
                    strBuilder.append('0');
                }
                strBuilder.append(s);
            }
            return strBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
    	System.out.println(encryptMd5("zc852722").toUpperCase());
    	
    	System.out.println(encryptMd5(encryptMd5("zc852722"),"113101302"));
    	
    	System.out.println(encryptMd5(encryptMd5("123456"),"113101302"));
    	String car_no = "吉B'B2369";
    	if(car_no.indexOf("'")>0){
			car_no = car_no.substring(0,car_no.indexOf("'")) + car_no.substring(car_no.indexOf("'")+1,car_no.length());
			System.out.println(car_no);
    	}
    	
    	
    	System.out.println(" 18985853153".split(" ")[1]);
	}
}
 