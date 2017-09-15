package com.shuai.test.proxyAndInvocationHandler;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/9/15
 * @Modified By:
 */
public class RealSubject implements Subject{
    @Override
    public void rent() {
        System.out.println("I want to rent a house!");
    }

    @Override
    public void helloStr(String str) {
        System.out.println("hello:" + str);
    }
}
