package com.shuai.test.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/7/25
 * @Modified By:
 */
@Entity
@Table(name = "t_store")
@Data
public class Store implements Serializable {

    @Id
    @GeneratedValue()
    private Long id;
    /**
     * 登录用户名
     */
//    @Column(name = "user_name")
    private String username;

    /**
     * 用户手机号码
     */
    private String phone;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 盐
     **/
    private String salt;
    /**
     * 最后登录时间
     */
//    @Column(name = "login_date")
    private Date loginDate;
    /**
     * 最后ip地址
     */
    private String ip;
    /**
     * 是否禁用
     */
//    @Column(name = "is_enabled")
    private Boolean isEnabled;
    /**
     * 是否锁定
     */
//    @Column(name = "is_locked")
    private Boolean isLocked;

    /**
     * 连续登录失败次数
     */
//    @Column(name = "login_failure_count")
    private Integer loginFailureCount;

    /**
     * 锁定日期
     */
//    @Column(name = "locked_date")
    private Date lockedDate;

    /**
     * 门店id（来自创建该门店应用中主键id）
     */
//    @Column(name = "store_id")
    private Long storeId;
//    @Column(name = "create_date")
    /** 创建日期 */
    private Long createDate = System.currentTimeMillis();

    /** 修改日期 */
    private Long modifyDate = System.currentTimeMillis();
}
