package com.shuai.test.controller;

import com.shuai.test.dto.StoreDto;
import com.shuai.test.service.StoreService;
import com.shuai.test.utils.DateUtils;
import com.shuai.test.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/7/24
 * @Modified By:
 */
@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @RequestMapping(value = "/month", method = RequestMethod.GET)
    public Result<Map> getPointAndRebate() {
//        Store store = storeService.getStore();
        StoreDto storeDto = StoreDto.builder()
//                .storeId(16L)
                .build();
        Integer year = DateUtils.getYear(new Date());
        Integer month = DateUtils.getMonth(new Date());
        Assert.notNull(storeDto.getStoreId(),"门店用户信息不能为空！");
        Result<Map> pointAndRebate = storeService.getPointAndRebate(storeDto, year, month);
        return pointAndRebate;
    }
}
