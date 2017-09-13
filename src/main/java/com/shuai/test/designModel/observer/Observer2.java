package com.shuai.test.designModel.observer;

/**
 * @Author: MiaoHongShuai
 * @Description: 观察者2
 * @Date: Created on 2017/9/11
 * @Modified By:
 */
public class Observer2 implements Observer{
    private Subject subject;

    public Observer2(Subject subject) {
        this.subject = subject;
        subject.add(this);
    }

    @Override
    public void update(Subject subject, String value) {
        System.out.println("Observer2 has received " + value + "!");
    }
}
