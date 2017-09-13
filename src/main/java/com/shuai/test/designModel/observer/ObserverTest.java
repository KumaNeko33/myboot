package com.shuai.test.designModel.observer;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/9/11
 * @Modified By:
 */
public class ObserverTest {
    public static void main(String[] args) {
        Observer observer1 = new Observer1();

        Subject subject = new SubjectImpl();
        subject.add(observer1);//主题主动添加观察者
        Observer observer2 = new Observer2(subject);//观察者主动订阅（构造函数内部调用主题来添加Observer自己） 主题（服务号）
        subject.operation("第一次更新");//更新

        System.out.println();
        subject.delete(observer1);
        subject.operation("第二次更新");
    }
}
