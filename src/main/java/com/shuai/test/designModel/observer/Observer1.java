package com.shuai.test.designModel.observer;

/**
 * @Author: MiaoHongShuai
 * @Description: 观察者1
 * @Date: Created on 2017/9/11
 * @Modified By:
 */
public class Observer1 implements Observer {
    @Override
    public void update(Subject subject, String value) {
        System.out.println("Observer1 has received " + value + "!");
    }
}
