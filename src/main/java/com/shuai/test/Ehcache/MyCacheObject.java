package com.shuai.test.Ehcache;

import java.io.Serializable;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/9/12
 * @Modified By:
 */
public class MyCacheObject implements Serializable {
    private static final long serialVersionUID = 1L;
    public Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public MyCacheObject() {
    }

    public MyCacheObject(Object object) {
        this.object = (Serializable) object;
    }
}
