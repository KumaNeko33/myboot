package com.shuai.test.designModel.observer;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: MiaoHongShuai
 * @Description: 被观察者
 * @Date: Created on 2017/9/11
 * @Modified By:
 */
public class SubjectImpl implements Subject {
    //需要通知的观察者集合
    private List<Observer> list = new LinkedList<>();//这里需要对list进行初始化，不然会报空指针异常
    //当操作是在一列数据的后面添加数据而不是在前面或中间,并且需要随机地访问其中的元素时,使用ArrayList会提供比较好的性能；
    //当你的操作是在一列数据的前面或中间添加或删除数据,并且按照顺序访问其中的元素时,就应该使用LinkedList了。

    //添加观察者
    public void add(Observer observer) {
        list.add(observer);
    }

    //删除观察者
    public void delete(Observer observer) {
        list.remove(observer);
    }

    //通知观察者进行更新
    public void notifyObserver(String value) {
        for (Observer observer :
                list) {
            observer.update(this, value);//this可用来获取被观察者的句柄，传给Observer对象
            //value也通知给观察者Observer对象
        }
    }

    //自己的操作，并通知观察者，会引发各个观察者的后续操作
    public void operation(String value) {
        // TODO 操作逻辑
        System.out.println("update self!");
        //通知观察者
        notifyObserver(value);
    }
}
/*
遍历LinkList集合：
for(Node<E> x = first; x != null; x.next){
    if(o.equals(x.item)){
        unlink(x);
        return true;
    }
}

    E unlink(Node<E> x) {
        // assert x != null;
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {//x前结点为空，即x为首结点，移除后，x后一个结点成为首结点
            first = next;
        } else {
            prev.next = next;//x前一个结点的后节点改成x的后节点，
            x.prev = null;//将x结点的前指针指向空，这样x全为空时可有垃圾回收器回收
        }

        if (next == null) {//x后结点为空，即x为尾结点，移除后，x前一个结点成为尾结点
            last = prev;
        } else {
            next.prev = prev;//后一个结点的前节点改成x的前节点，
            x.next = null;//将x结点的后指针指向空，这样x全为空时可有垃圾回收器回收
        }

        x.item = null;//将x结点的结点内容 置为空，这样x全为空时可有垃圾回收器回收
        size--;//集合逻辑长度减1
        modCount++;//操作数加1
        return element;//返回被移除的结点内容item
    }
 */
