package com.shuai.test.designModel.observer;

/**
 * @Author: MiaoHongShuai
 * @Description: 观察者（通知）接口
 * @Date: Created on 2017/9/11
 * @Modified By:
 */
public interface Observer {
    void update(Subject subject, String value);//得到通知后调用的方法
}
