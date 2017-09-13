package com.shuai.test.Ehcache;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/9/12
 * @Modified By:
 */
public class MyEhcacheDaoImpl implements MyEhcacheDao {
    public List querydictionaryByCodeNo(String codeNo) {
        List list = new ArrayList();
        if (codeNo == null || codeNo.trim().equals("")) {
            System.out.println("参数codeNo为空，查询所有值。");
            String hql = "select d from Dictionary d ";
//            list=this.queryListHql(hql);
        } else {
            String hql = "select d from Dictionary d where d.code_no like '" + codeNo.trim() + "%'";
//            list=this.queryListHql(hql);
        }
        return list;
    }
}
