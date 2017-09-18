package com.shuai.test.proxyAndInvocationHandler;

import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;


/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/9/18
 * @Modified By:
 */
public class DynamicProxyHandlerUpdate implements InvocationHandler {
    private Subject realSubject;

    //绑定委托对象InvocationHandler
    public Object bind(Subject realSubject) {
        this.realSubject = realSubject;
        //绑定该类实现的所有接口，取得代理类
        return Proxy.newProxyInstance(realSubject.getClass().getClassLoader(), realSubject.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        Object result;
        //这里就可以进行所谓的AOP编程了
        //代理执行方法前
        System.out.println("代理执行方法前");

        //代理执行真实对象的方法
        result = method.invoke(realSubject, args);

        //代理执行方法后
        System.out.println("代理执行方法后");
        return result;
    }
}
