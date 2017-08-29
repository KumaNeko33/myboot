package TestMethod;

import java.io.Serializable;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/8/29
 * @Modified By:
 */
public class Inner implements Serializable {
    private static final long serialVersionUID = 436583946757211L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Inner{" +
                "name='" + name + '\'' +
                '}';
    }

//    答：JSP有9个内置对象：
//            - request：封装客户端的请求，其中包含来自GET或POST请求的参数；
//            - response：封装服务器对客户端的响应；
//            - pageContext：通过该对象可以获取其他对象；
//            - session：封装用户会话的对象；
//            - application：封装服务器运行环境的对象；
//            - out：输出服务器响应的输出流对象；
//            - config：Web应用的配置对象；
//            - page：JSP页面本身（相当于Java程序中的this）；
//            - exception：封装页面抛出异常的对象。
//
//    补充：如果用Servlet来生成网页中的动态内容无疑是非常繁琐的工作，另一方面，所有的文本和HTML标签都是硬编码，即使做出微小的修改，都需要进行重新编译。
//    JSP解决了Servlet的这些问题，它是Servlet很好的补充，可以专门用作为用户呈现视图（View），
//    而Servlet作为控制器（Controller）专门负责处理用户请求并转发或重定向到某个页面。基于Java的Web开发很多都同时使用了Servlet和JSP。
//    JSP页面其实是一个Servlet，能够运行Servlet的服务器（Servlet容器）通常也是JSP容器，可以提供JSP页面的运行环境，Tomcat就是一个Servlet/JSP容器。
//    第一次请求一个JSP页面时，Servlet/JSP容器首先将JSP页面转换成一个JSP页面的实现类，这是一个实现了JspPage接口或其子接口HttpJspPage的Java类。
//    JspPage接口是Servlet的子接口，因此每个JSP页面都是一个Servlet。转换成功后，容器会编译Servlet类，之后容器加载和实例化Java字节码，
//    并执行它通常对Servlet所做的生命周期操作。对同一个JSP页面的后续请求，容器会查看这个JSP页面是否被修改过，如果修改过就会重新转换并重新编译并执行。
//    如果没有则执行内存中已经存在的Servlet实例。我们可以看一段JSP代码对应的Java程序就知道一切了，而且9个内置对象的神秘面纱也会被揭开。
}
