package observer;

/**
 * @Author: MiaoHongShuai
 * @Description: 被观察者（主题）的接口
 * @Date: Created on 2017/9/11
 * @Modified By:
 */
public interface ModifyObserver {
    //添加观察者
    void add(Observer observer);

    //删除观察者
    void delete(Observer observer);

    //通知观察者进行更新
    void notifyObserver(String value);

    //自己的操作
    void operation(String value);
}
