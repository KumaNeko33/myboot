package com.shuai.test.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/7/24
 * @Modified By:
 */
@Entity
@Table(name="t_store_rebate_audit")
@Data
public class StoreRebateAudit implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    private Long createDate = System.currentTimeMillis();

    private Long modifyDate = System.currentTimeMillis();

    private Integer year;

    private Integer quarter;

//    @Column(name = "valid_in_quantity")
    private Long validInQuantity;

//    @Column(name = "in_quantity")
    private Integer inQuantity;

//    @Column(name = "out_quantity")
    private Integer outQuantity;

    private Store store;

    /** 门头编号 */
    private String storeNo;

//    @Column(name = "store_name")
    private String storeName;

//    @Column(name = "audit_date")
    private Date auditDate;

//    @Column(name = "stock_in_point")
    private Long stockInPoint;

    /** 审核的入库积分 */
    private Long examineInPoint;

//    @Column(name = "stock_in_rebate")
    private Long stockInRebate;

    /** 审核的入库返利 */
    private BigDecimal examineInRebate;

//    @Column(name = "stock_out_point")
    private Long stockOutPoint;

    /** 审核的出库积分 */
    private Long examineOutPoint;

//    @Column(name = "stock_out_rebate")
    private Long stockOutRebate;

    /** 审核的出库返利 */
    private BigDecimal examineOutRebate;

    /**
     * 进货积分上限
     */
    private Long stockInPointMax;

    /**
     * 进货返利上限
     */
    private Long stockInRebateMax;

    /**
     * 销售积分上限
     */
    private Long stockOutPointMax;

    /**
     * 销售返利上限
     */
    private Long stockOutRebateMax;

    /**
     * 当前门店进货指标
     */
    private Long purchasing;

    /** 门店销售指标 **/
    private Long sales;

    /** 操作员 */
    private String operator;

    private Integer timeType;

    /**
     * 积分返利审核状态
     */
    private AuditStatus auditStatus;
    /**
     * 审核状态
     */
    public enum AuditStatus{
        /**待审*/
        pending,
        /**已审*/
        audited
    }

}
