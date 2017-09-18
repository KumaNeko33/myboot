package com.shuai.test.proxyAndInvocationHandler;


/**
 * @Author: MiaoHongShuai
 * @Description: JDK动态代理生产的另一种方式
 * @Date: Created on 2017/9/18
 * @Modified By:
 */
public class DynamicProxyUpdateTest {
    public static void main(String[] args) {
        //创建被代理的真实对象
        Subject subject = new RealSubject();
        //创建委托类
        DynamicProxyHandlerUpdate handler = new DynamicProxyHandlerUpdate();
        //创建动态代理类
        Subject proxy = (Subject) handler.bind(subject);

        //使用动态代理类 代理执行AOP后的方法
        proxy.rent();
        System.out.println("-------------------------");
        proxy.helloStr("update");
        /*打印结果：
        代理执行方法前
        I want to rent a house!
        代理执行方法后
        -------------------------
        代理执行方法前
        hello:update
        代理执行方法后
         */
    }
}
