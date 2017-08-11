package com.shuai.test.dto;

import java.util.Date;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/8/7
 * @Modified By:
 */
public class StoreActivitySearchDto {
    private Date createDateBegin;
    private Date createDateEnd;
    private Date startDateBegin;
    private Date startDateEnd;
    private Date endDateBegin;
    private Date endDateEnd;
    /** 活动名称 */
    private String activityName;
    /** 活动内容 */
    private String activityContent;
    /** status：活动状态：true:上架，false:下架 */
    private Boolean status;
    /** showStatus：活动隐藏状态：true:隐藏，false:非隐藏 */
    private Boolean showStatus;
    private String storeName;

    public Date getCreateDateBegin() {
        return createDateBegin;
    }

    public void setCreateDateBegin(Date createDateBegin) {
        this.createDateBegin = createDateBegin;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }

    public Date getStartDateBegin() {
        return startDateBegin;
    }

    public void setStartDateBegin(Date startDateBegin) {
        this.startDateBegin = startDateBegin;
    }

    public Date getStartDateEnd() {
        return startDateEnd;
    }

    public void setStartDateEnd(Date startDateEnd) {
        this.startDateEnd = startDateEnd;
    }

    public Date getEndDateBegin() {
        return endDateBegin;
    }

    public void setEndDateBegin(Date endDateBegin) {
        this.endDateBegin = endDateBegin;
    }

    public Date getEndDateEnd() {
        return endDateEnd;
    }

    public void setEndDateEnd(Date endDateEnd) {
        this.endDateEnd = endDateEnd;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Boolean showStatus) {
        this.showStatus = showStatus;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
