package com.shuai.test.proxyAndMethodIntercepter;

import org.springframework.cglib.proxy.Enhancer;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/9/18
 * @Modified By:
 */
public class DynamicProxyTest {
    public static void main(String[] args) {
        //创建需要被代理的对象，不需要实现接口
        User user = new User();
        //cglib 中加强器，用来创建类的动态代理
        Enhancer enhancer = new Enhancer();
        //设置要被动态代理的类
        enhancer.setSuperclass(user.getClass());
        //设置回调，这里相当于是对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实行intercept()方法进行拦截,而MyMethodIntercepter实现了Callback
        enhancer.setCallback(new MyMethodIntercepter());
        //创建动态代理类
        User userProxy = (User) enhancer.create();

        //通过动态代理类调用 增强过的方法
        userProxy.add();

        /*执行结果：
        代理执行方法: public void com.shuai.test.proxyAndMethodIntercepter.User.add() 前,代码自定.....
        User's add method!
        代理执行方法: public void com.shuai.test.proxyAndMethodIntercepter.User.add() 前,代码自定.....
         */
    }
}
