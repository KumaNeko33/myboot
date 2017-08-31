package com.shuai.test.interceptor;

import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/8/31
 * @Modified By:
 */
public class CacheLockInterceptor implements InvocationHandler{
    private static int ERROR_COUNT = 0;
    private Object proxied;

    public CacheLockInterceptor(Object proxied) {
        this.proxied = proxied;
    }
    /*CacheLockInterceptor实现InvocationHandler接口，在invoke方法中获取注解的方法和参数，在执行注解的方法前加锁，执行被注解的方法后释放锁：*/
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        return null;
    }
}
