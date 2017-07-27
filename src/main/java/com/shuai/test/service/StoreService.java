package com.shuai.test.service;

import com.shuai.test.dto.StoreDto;
import com.shuai.test.utils.Result;

import java.util.Map;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/7/26
 * @Modified By:
 */
public interface StoreService {
    Result<Map> getPointAndRebate(StoreDto storeDto, Integer year, Integer month);
}
