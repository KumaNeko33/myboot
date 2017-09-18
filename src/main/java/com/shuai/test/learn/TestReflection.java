package com.shuai.test.learn;
import java.lang.reflect.Method;

/**
 * @Author: MiaoHongShuai
 * @Description: 反射机制的测试
 *  注意事项：由于反射会额外消耗一定的系统资源，因此如果不需要动态地创建一个对象，那么就不需要用反射。
    另外，反射调用方法时可以忽略权限检查，因此可能会破坏封装性而导致安全问题。
 * @Date: Created on 2017/9/15
 * @Modified By:
 */
public class TestReflection {

    public static void main(String[] args) {
        //反射获取类的方法一：类名.class
        Class c = MethodClass.class;

        try {
            //通过类 直接获取实例对象:由于默认返回的是Object类型，需要类型转换：
            MethodClass mt = (MethodClass) c.newInstance();

            //通过类 获取类中的不包括继承的所有方法：
            Method[] methods = c.getDeclaredMethods();
            System.out.println("getDeclaredMethods获取的方法：");
            for(Method m:methods){
                System.out.println(m);
            }
            System.out.println("----------------------------------");
            //通过类 获取类中的包括继承的所有public方法：
            Method[] publicMethods = c.getMethods();
            System.out.println("getMethods获取的方法：");
            for(Method m:publicMethods){
                System.out.println(m);
            }
            System.out.println("----------------------------------");
            //通过类 获取类中指定的方法，需要输入方法参数的类型.class
            Method namedMethod = c.getDeclaredMethod("add", int.class, int.class);
            System.out.println("getDeclaredMethod获取指定名称为‘add’的方法：" + namedMethod);
            System.out.println("----------------------------------");

            //通过类 获取方法后，即可用Method的invoke()方法来调用这些方法：需要类的 实例对象和方法参数（如果需要的话）
            Object addResult = namedMethod.invoke(mt, 1, 4);//这里调用类MethodClass的public权限的add方法，传入参数为1和4,
            //invoke()方法里会进行权限检验，如果override=true表示忽略权限校验，直接调用方法，若override=false，继续校验：若方法的public则权限校验通过，
            // 不然继续校验（这时可能会打破private的封装性）：如果传入的实例method的类型Class caller==通过这个nameMethod方法获得的类型Class clazz，则权限校验通过，
//            若未通过，则创建一个缓存，中间再进行一堆检查（比如检验是否为protected属性）。
//            如果上面的所有权限检查都未通过，那么将执行更详细的检查
//            用Reflection.ensureMemberAccess方法继续检查权限，若检查通过就更新缓存，这样下一次同一个类调用同一个方法时就不用执行权限检查了，
//            这是一种简单的缓存机制。由于JMM的happens-before规则能够保证缓存初始化能够在写缓存之前发生，因此两个cache不需要声明为volatile。
//            到这里，前期的权限检查工作就结束了。如果没有通过检查则会抛出异常，如果通过了检查则会到下一步。
            System.out.println(addResult);//打印结果为null，原因？
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class MethodClass{
    public MethodClass(){
    }
    public void add(int a, int b){
        System.out.println("public add method result = " + (a + b));
    }
    private void remove(){
        System.out.println("private remove method");
    }
}