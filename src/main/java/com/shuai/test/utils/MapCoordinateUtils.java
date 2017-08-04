package com.shuai.test.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * 地图坐标转换工具
 * 
 * @author javawlb 
 * 日期 	2016-11-02
 */
public class MapCoordinateUtils {

	/**
	 * 根据传入地址，返回坐标
	 * 
	 * @param address
	 * @return map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> getCoordinate(String address) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(address)){
			return resultMap;
		}
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("output", "json");
		parameterMap.put("address", address);
		String result = null;
		try {
			result = HttpUtils.get("http://api.map.baidu.com/geocoder", parameterMap, null);
			Map map = JSON.parseObject(result, Map.class);
			resultMap = JSON.parseObject(JSON.parseObject(map.get("result").toString(), Map.class).get("location").toString(), Map.class);
		} catch (Exception e) {
			System.err.println("未获取到坐标地址："+address);
			System.err.println("return result："+result);
		}
		return resultMap;
	}

	/**
	 * 根据传入的坐标返回省，市，区
	 * 
	 * @param lng
	 * @param lat
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getAddress(BigDecimal lng, BigDecimal lat) {
		Map<String, String> resultMap = new HashMap<String, String>();
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("output", "json");
		parameterMap.put("ak", "21nrWlDpwnAyHnDnm37WiktH");
		parameterMap.put("location", lat + "," + lng);
		try {
			String result = HttpUtils.get("http://api.map.baidu.com/geocoder/v2/", parameterMap, null);
			Map m = JSON.parseObject(result, Map.class);
			Map m1 = JSON.parseObject(m.get("result").toString(), Map.class);
			Map m2 = JSON.parseObject(m1.get("addressComponent").toString(), Map.class);
			resultMap.put("province", m2.get("province").toString());
			resultMap.put("city", m2.get("city").toString());
			resultMap.put("area", m2.get("district").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

}
