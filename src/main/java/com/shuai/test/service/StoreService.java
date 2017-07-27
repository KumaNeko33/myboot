package com.shuai.test.service;

import com.shuai.test.domain.Store;
import com.shuai.test.utils.Result;

import java.util.Map;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/7/26
 * @Modified By:
 */
public interface StoreService {
    Result<Map<String,Long>> getPointAndRebate(Store store, Integer year, Integer month);
}
