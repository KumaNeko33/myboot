package com.shuai.test.designModel.prototype;

import java.io.*;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/9/12
 * @Modified By:
 */
public class Prototype implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private String string;

    private SerializableObject obj;

    // 浅复制
    public Object clone() throws CloneNotSupportedException {
        Prototype proto = (Prototype) super.clone();
        return proto;
    }

    // 深复制
    public Object deepClone() throws IOException, ClassNotFoundException {

        // 写入当前对象的二进制流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);//使用了 装饰器设计模式
        oos.writeObject(this);//将当前对象写入流中成为字节数组

// 改良版：try(ByteArrayOutputStream baos = new ByteArrayOutputStream();ObjectOutputStream oos = new ObjectOutputStream(baos)){
//            oos.writeObject(this);
//         }

        // 读出二进制流产生的新对象
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();//将流中的字节数组读取并转换成对象，然后return返回当前对象的深克隆

// 改良版：try(ByteArrayInputStream bais = new ByteArrayInputStream();ObjectInputStream ois = new ObjectInputStream(bais)){
//            return ois.readObject();
//        }
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public SerializableObject getObj() {
        return obj;
    }

    public void setObj(SerializableObject obj) {
        this.obj = obj;
    }
}
class SerializableObject implements Serializable {
    private static final long serialVersionUID = 1L;
}

