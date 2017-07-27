package com.shuai.test.dao;

import com.shuai.test.domain.StoreRebateAudit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author: MiaoHongShuai
 * @Description: 操作审核表的dao
 * @Date: Created on 2017/7/24
 * @Modified By:
 */
public interface StoreDao extends BaseDao<StoreRebateAudit,Long> {
    //使用了where 1=1 后，就不需要判断 查询语句中的参数是否为 null了
    @Query(nativeQuery = true,value = "SELECT SUM(IFNULL(a.stock_in_point,0)+IFNULL(a.stock_out_point,0)) AS 'monthPoint',SUM(IFNULL(a.stock_in_rebate,0)+IFNULL(a.stock_out_rebate,0)) AS 'monthRebate',a.valid_in_quantity FROM t_store_rebate_audit a WHERE 1=1 AND a.store = :storeId AND a.year = :year AND a.quarter = :month")
    List<Object[]> getPointAndRebate(@Param("storeId") Long storeId, @Param("year") Integer year, @Param("month") Integer month);
}
