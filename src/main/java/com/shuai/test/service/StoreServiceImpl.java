package com.shuai.test.service;

import com.shuai.test.dao.StoreDao;
import com.shuai.test.domain.Store;
import com.shuai.test.utils.Result;
import com.shuai.test.utils.ResultGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/7/24
 * @Modified By:
 */
@Service
public class StoreServiceImpl implements StoreService{

    @Autowired
    private StoreDao storeDao;

    @Override
    @Transactional
    public Result<Map<String,Long>> getPointAndRebate(Store store, Integer year, Integer month) {
        Assert.isNull(store,"门店信息不能为空！");
        HashMap<String, Long> returnMap = new HashMap<>();
        try {
            Long storeId = store.getStoreId();
            List<Object[]> resultList = storeDao.getPointAndRebate(storeId, year, month);
            if(CollectionUtils.isNotEmpty(resultList)) {
                returnMap.put("monthPoint", Long.parseLong(resultList.get(0)[0].toString()));
                returnMap.put("monthRebate", Long.parseLong(resultList.get(0)[1].toString()));
                returnMap.put("validQuantity", Long.parseLong(resultList.get(0)[0].toString()));
            }
        } catch (Exception e) {
            return ResultGenerator.genFailResult("获取积分和返利信息失败！错误信息：" + e.getMessage());
        }
        return ResultGenerator.genSuccessResult(returnMap);
    }
}
