package observer;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/9/11
 * @Modified By:
 */
public class ObserverTest {
    public static void main(String[] args) {
        Observer observer1 = new Observer1();
        Observer observer2 = new Observer2();

        ModifyObserver modifyObserver = new ModifyObserverImpl();
        modifyObserver.add(observer1);//开始操作观察者
        modifyObserver.add(observer2);//开始操作观察者
        modifyObserver.operation("第一次更新");//更新

        System.out.println();
        modifyObserver.delete(observer1);
        modifyObserver.operation("第二次更新");
    }
}
