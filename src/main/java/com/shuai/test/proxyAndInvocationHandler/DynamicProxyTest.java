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
public class DynamicProxyTest {
    public static void main(String[] args) {
        //    我们要代理的真实对象
        Subject realSubject = new RealSubject();
        String a = "a";
        
        Class clazz = realSubject.getClass();
        try {
            Method method = clazz.getDeclaredMethod("rent", null);
            //    创建代理对象，我们要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法的
            InvocationHandler handler = new DynamicProxyHandler(realSubject);
//            handler.invoke(realSubject, method, null);
            /*结果：before house rent
                    Method: public void com.shuai.test.proxyAndInvocationHandler.RealSubject.rent()
                    I want to rent a house!
                    after house rent
            */

        /*
         * 通过Proxy的newProxyInstance方法来创建我们的代理对象，我们来看看其三个参数
         * 第一个参数 realSubject.getClass().getClassLoader() ，我们这里使用realSubject这个类的ClassLoader对象来加载我们的代理对象(这个可以是任意对象的类加载器)
         * 第二个参数realSubject.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实现的接口，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
         * 第三个参数handler， 我们这里将这个代理对象关联到了上方的 InvocationHandler 这个对象上,注意是spring的cglib包的InvocationHandler,方法的实际调用者是它的内部传入的真实对象
         */
            Subject instance = (Subject)Proxy.newProxyInstance(realSubject.getClass().getClassLoader(), realSubject.getClass().getInterfaces(), handler);
            System.out.println(instance.getClass().getName()); //org.springframework.cglib.proxy.Proxy$ProxyImpl$$EnhancerByCGLIB$$7f93cbd0
            //通过 Proxy.newProxyInstance 创建的代理对象是在jvm运行时动态生成的一个对象，它并不是我们的InvocationHandler类型，也不是我们定义的那组接口的类型，
            //  而是在运行是动态生成的一个对象，并且命名方式都是这样的形式，以$开头，proxy为中，最后一个数字表示对象的标号。
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
