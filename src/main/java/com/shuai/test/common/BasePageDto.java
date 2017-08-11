package com.shuai.test.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author HZH
 * @date 2017/4/13
 */
public class BasePageDto implements Serializable {

    /** 当前页数 */
    @ApiModelProperty(value = "当前页数",required = true)
    private Integer currentPage = 1;
    /** 每页显示记录数 */
    @ApiModelProperty(value = "每页显示记录数",required = true)
    private Integer pageSize = 20;
    /**
     * 排序方式
     * id desc
     */
    @JsonIgnore
    private String sort;

    /** 获取当前页的起始记录位置 */
    @JsonIgnore
    public Integer getOffset(){
        Integer offsetTmp = 0;
        if(null != getCurrentPage() && null != getPageSize()){
            offsetTmp = (getCurrentPage()-1)*getPageSize();
        }
        return offsetTmp <= 0 ? 0 : offsetTmp;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = (currentPage == null || currentPage<=0 ? 1 : currentPage);
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = (pageSize == null || pageSize<=0 ? 1 : pageSize);
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
