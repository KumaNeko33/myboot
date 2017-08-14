package com.shuai.test.service;

import com.shuai.test.dao.StoreDao;
import com.shuai.test.dto.StoreDto;
import com.shuai.test.utils.Result;
import com.shuai.test.utils.ResultGenerator;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    public Result<Map> getPointAndRebate(StoreDto storeDto, Integer year, Integer month) {
//        Assert.isNull(store,"门店信息不能为空！");

        HashMap returnMap = new HashMap();
        try {
//            Long storeId = storeDto.getStoreId();
            Long storeId = 16L;
            List<Object[]> resultList = storeDao.getPointAndRebate(storeId, year, month);
            if (CollectionUtils.isEmpty(resultList)) {
//                returnMap.put("monthPoint", Long.parseLong(resultList.get(0)[0].toString()));
                returnMap.put("monthPoint", resultList.get(0)[0]);
                returnMap.put("monthRebate", resultList.get(0)[1]);
                returnMap.put("validQuantity", resultList.get(0)[2]);
            }
        } catch (Exception e) {
            throw new ServiceException("获取积分和返利信息失败！错误信息：" + e.getMessage());
        }
        return ResultGenerator.genSuccessResult(returnMap);
    }
}
