package TestMethod;


import java.io.*;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/8/29
 * @Modified By:
 */
public class Outer implements Serializable {
    private static final long serialVersionUID = 4567823459871231L;

    public Outer() {
    }

    //浅度克隆
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    //深度克隆
    private Inner inner;

    public Outer myClone() {
        Outer outer = new Outer();
        try {
            // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(outer);
            //将流 序列化成对象
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            outer = (Outer)ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Outer outer1 = new Outer();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())){
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(outer1);

            ObjectInputStream ois = new ObjectInputStream(bais);
            outer1 = (Outer)ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return outer;
    }
}
