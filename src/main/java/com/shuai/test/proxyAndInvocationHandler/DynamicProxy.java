package com.shuai.test.proxyAndInvocationHandler;

import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * @Author: MiaoHongShuai
 * @Description: 有点像适配器？
 * @Date: Created on 2017/9/15
 * @Modified By:
 */
public class DynamicProxy implements InvocationHandler {

    //需要代理的真实对象的引用
    private Object subject;
    //传入真实对象实例，让引用指向它
    public DynamicProxy(Object subject) {
        this.subject = subject;
    }
    //调用真实对象实例的 方法，原理：使用反射的method.invoke()方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //　　在代理真实对象前我们可以添加一些自己的操作
        System.out.println("before house rent");
        System.out.println("Method: " + method);

        //    当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        method.invoke(subject, args);

        //    在代理真实对象后我们也可以添加一些自己的操作
        System.out.println("after house rent");
        return null;
    }
}
