package observer;

/**
 * @Author: MiaoHongShuai
 * @Description: 观察者2
 * @Date: Created on 2017/9/11
 * @Modified By:
 */
public class Observer2 implements Observer{
    @Override
    public void update(ModifyObserver modifyObserver, String value) {
        System.out.println("Observer2 has received " + value + "!");
    }
}
