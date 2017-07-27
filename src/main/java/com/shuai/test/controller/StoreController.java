package com.shuai.test.controller;

import com.shuai.test.domain.Store;
import com.shuai.test.service.StoreService;
import com.shuai.test.utils.DateUtils;
import com.shuai.test.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Result<Map<String, Long>> getPointAndRebate() {
//        Store store = storeService.getStore();
        Store store = new Store();
        store.setId(16L);
        Integer year = DateUtils.getYear(new Date());
        Integer month = DateUtils.getMonth(new Date());
        Result<Map<String, Long>> pointAndRebate = storeService.getPointAndRebate(store, year, month);
        return pointAndRebate;
    }
}
