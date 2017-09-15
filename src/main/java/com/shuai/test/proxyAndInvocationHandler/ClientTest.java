package com.shuai.test.proxyAndInvocationHandler;

import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/9/15
 * @Modified By:
 */
public class ClientTest {
    public static void main(String[] args) {
        //    我们要代理的真实对象
        Subject realSubject = new RealSubject();
        
        Class clazz = realSubject.getClass();
        try {
            Method method = clazz.getDeclaredMethod("rent", null);
            //    创建代理对象，我们要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法的
            InvocationHandler handler = new DynamicProxy(realSubject);
//            handler.invoke(realSubject, method, null);
            /*结果：before house rent
                    Method: public void com.shuai.test.proxyAndInvocationHandler.RealSubject.rent()
                    I want to rent a house!
                    after house rent
            */

        /*
         * 通过Proxy的newProxyInstance方法来创建我们的代理对象，我们来看看其三个参数
         * 第一个参数 handler.getClass().getClassLoader() ，我们这里使用handler这个类的ClassLoader对象来加载我们的代理对象
         * 第二个参数realSubject.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实行的接口，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
         * 第三个参数handler， 我们这里将这个代理对象关联到了上方的 InvocationHandler 这个对象上,注意是spring的cglib包的InvocationHandler,方法的实际调用者是它的内部传入的真实对象
         */
            Subject instance = (Subject)Proxy.newProxyInstance(handler.getClass().getClassLoader(), realSubject.getClass().getInterfaces(), handler);
            System.out.println(instance.getClass().getName());
            System.out.println("------------");
            instance.rent();
            System.out.println("------------");
            instance.helloStr("world");
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
