package com.shuai.test.Ehcache;

import java.util.List;

/**
 * @Author: MiaoHongShuai
 * @Description: 查询数据的公用dao
 * @Date: Created on 2017/9/12
 * @Modified By:
 */
public interface MyEhcacheDao {
    public List querydictionaryByCodeNo(String codeNo);
}
