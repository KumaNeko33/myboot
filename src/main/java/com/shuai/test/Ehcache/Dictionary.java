package com.shuai.test.Ehcache;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: MiaoHongShuai
 * @Description: 公用的字典表对象
 * @Date: Created on 2017/9/12
 * @Modified By:
 */
public class Dictionary implements Serializable {
    /**
     * 需要implements Serializable，因为ehcache需要往硬盘写对象
     */
    private static final long serialVersionUID = 1L;

    private String id;
    private String code_no;
    private String code_name;
    private String code_parent_no;
    private int show_order;
    private Date create_time;
    private String remark;
    private String create_user;
    private String valid;
    private String bak1;
    private String bak2;
    private String bak3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode_no() {
        return code_no;
    }

    public void setCode_no(String code_no) {
        this.code_no = code_no;
    }

    public String getCode_name() {
        return code_name;
    }

    public void setCode_name(String code_name) {
        this.code_name = code_name;
    }

    public String getCode_parent_no() {
        return code_parent_no;
    }

    public void setCode_parent_no(String code_parent_no) {
        this.code_parent_no = code_parent_no;
    }

    public int getShow_order() {
        return show_order;
    }

    public void setShow_order(int show_order) {
        this.show_order = show_order;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getBak1() {
        return bak1;
    }

    public void setBak1(String bak1) {
        this.bak1 = bak1;
    }

    public String getBak2() {
        return bak2;
    }

    public void setBak2(String bak2) {
        this.bak2 = bak2;
    }

    public String getBak3() {
        return bak3;
    }

    public void setBak3(String bak3) {
        this.bak3 = bak3;
    }
}
