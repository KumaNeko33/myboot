package com.shuai.test.proxyAndMethodIntercepter;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: MiaoHongShuai
 * @Description: 回调，MethodInterceptor实现了Callback
 * @Date: Created on 2017/9/18
 * @Modified By:
 */
public class MyMethodIntercepter implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        //代理执行方法前
        System.out.println("代理执行方法: " + method + " 前,代码自定.....");

        //代理执行方法
        Object result = methodProxy.invokeSuper(o, objects);

        //代理执行方法后
        System.out.println("代理执行方法: " + method + " 前,代码自定.....");
        return result;
    }
}
