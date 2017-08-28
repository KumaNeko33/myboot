package com.shuai.test.controller;

import com.shuai.test.domain.Store;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/8/25
 * @Modified By:
 */
@Controller
@RequestMapping("/test")
public class TestController {

    public static void main(String[] args) {
        //普通的获取年月日 时分秒
        Calendar calendar = Calendar.getInstance();
        System.out.print(calendar.get(Calendar.YEAR) + "-");
        System.out.print(calendar.get(Calendar.MONTH) + "-"); //0-11 比当前月少一个月
        System.out.print(calendar.get(Calendar.DAY_OF_MONTH) + " ");
        System.out.print(calendar.get(Calendar.HOUR) + ":");
        System.out.print(calendar.get(Calendar.MINUTE) + ":");
        System.out.println(calendar.get(Calendar.SECOND));
        //java8获取年月日 时分秒
        LocalDateTime ldt = LocalDateTime.now();
        System.out.print(ldt.getYear() + "-");
        System.out.print(ldt.getMonthValue() + "-"); //1-12
        System.out.print(ldt.getDayOfMonth() + " ");
        System.out.print(ldt.getHour() + ":");
        System.out.print(ldt.getMinute() + ":");
        System.out.println(ldt.getSecond());

        //如何取得从1970年1月1日0时0分0秒到现在的毫秒数？
        System.out.println(System.currentTimeMillis());

        //如何取得某月的一共有多少天？
        System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        System.out.println(ldt.minusDays(1));


        List<Store> list = new ArrayList<>();
        list.add(new Store());

        Collections.sort(list, new Comparator<Store>() {
            @Override
            public int compare(Store o1, Store o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
        });

//        90、简述一下你了解的设计模式。
//        答：所谓设计模式，就是一套被反复使用的代码设计经验的总结（情境中一个问题经过证实的一个解决方案）。使用设计模式是为了可重用代码、让代码更容易被他人理解、保证代码可靠性。设计模式使人们可以更加简单方便的复用成功的设计和体系结构。将已证实的技术表述成设计模式也会使新系统开发者更加容易理解其设计思路。
//        在GoF的《Design Patterns: Elements of Reusable Object-Oriented Software》中给出了三类（创建型[对类的实例化过程的抽象化]、结构型[描述如何将类或对象结合在一起形成更大的结构]、行为型[对在不同的对象之间划分责任和算法的抽象化]）共23种设计模式，包括：Abstract Factory（抽象工厂模式），Builder（建造者模式），Factory Method（工厂方法模式），Prototype（原始模型模式），
//        Singleton（单例模式）；Facade（门面模式），Adapter（适配器模式），Bridge（桥梁模式），Composite（合成模式），Decorator（装饰模式），Flyweight（享元模式），Proxy（代理模式）；Command（命令模式），Interpreter（解释器模式），Visitor（访问者模式），Iterator（迭代子模式），Mediator（调停者模式），Memento（备忘录模式），Observer（观察者模式），State（状态模式），Strategy（策略模式），Template Method（模板方法模式）， Chain Of Responsibility（责任链模式）。
//        面试被问到关于设计模式的知识时，可以拣最常用的作答，例如：
//        - 工厂模式：工厂类可以根据条件生成不同的子类实例，这些子类有一个公共的抽象父类并且实现了相同的方法，但是这些方法针对不同的数据进行了不同的操作（多态方法）。当得到子类的实例后，开发人员可以调用基类中的方法而不必考虑到底返回的是哪一个子类的实例。
//        - 代理模式：给一个对象提供一个代理对象，并由代理对象控制原对象的引用。实际开发中，按照使用目的的不同，代理可以分为：远程代理、虚拟代理、保护代理、Cache代理、防火墙代理、同步化代理、智能引用代理。
//        - 适配器模式：把一个类的接口变换成客户端所期待的另一种接口，从而使原本因接口不匹配而无法在一起使用的类能够一起工作。
//        - 模板方法模式：提供一个抽象类，将部分逻辑以具体方法或构造器的形式实现，然后声明一些抽象方法来迫使子类实现剩余的逻辑。不同的子类可以以不同的方式实现这些抽象方法（多态实现），从而实现不同的业务逻辑。
//        除此之外，还可以讲讲上面提到的门面模式、桥梁模式、单例模式、装潢模式（Collections工具类和I/O系统中都使用装潢模式）等，反正基本原则就是拣自己最熟悉的、用得最多的作答，以免言多必失。
//
//        91、用Java写一个单例类。
//        答：
//        - 饿汉式单例
//
//        public class Singleton {
//            private Singleton(){}//构造函数私有化，无法主动 实例化对象
//            private static Singleton instance = new Singleton();
//            public static Singleton getInstance(){
//                return instance;
//            }
//        }
         public class Singleton {
            private Singleton(){
            }//构造函数私有化，无法主动 实例化对象
            private static Singleton instance = new Singleton();
            public static Singleton getInstance(){
                return instance;
            }
        }
//        懒汉式单例
//        public class Singleton {
//            private static Singleton instance = null;
//            private Singleton() {}
//            public static synchronized Singleton getInstance(){
//                if (instance == null) instance ＝ new Singleton();
//                return instance;
//            }
//        }
        public class Singleton{
             private Singleton(){}
             private static Singleton instance = null;
             public static Singleton getInstance(){
                 synchronized (this){
                     if (instance == null) {
                         instance = new Singleton();
                     }
                     return instance;
                 }
             }
        }
//        注意：实现一个单例有两点注意事项，①将构造器私有，不允许外界通过构造器创建对象；②通过公开的静态方法向外界返回类的唯一实例。这里有一个问题可以思考：Spring的IoC容器可以为普通的类创建单例，它是怎么做到的呢？
//        92、什么是UML？
//        答：UML是统一建模语言（Unified Modeling Language）的缩写，它发表于1997年，综合了当时已经存在的面向对象的建模语言、方法和过程，是一个支持模型化和软件系统开发的图形化语言，为软件开发的所有阶段提供模型化和可视化支持。使用UML可以帮助沟通与交流，辅助应用设计和文档的生成，还能够阐释系统的结构和行为。
    }
}
