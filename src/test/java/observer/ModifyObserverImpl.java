package observer;

import java.util.LinkedList;

/**
 * @Author: MiaoHongShuai
 * @Description: 被观察者
 * @Date: Created on 2017/9/11
 * @Modified By:
 */
public class ModifyObserverImpl implements ModifyObserver {
    //需要通知的观察者集合
    private LinkedList<Observer> list = new LinkedList<>();//这里需要对list进行初始化，不然会报空指针异常

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
