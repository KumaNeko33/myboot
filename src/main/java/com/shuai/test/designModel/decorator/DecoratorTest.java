package com.shuai.test.designModel.decorator;

/**
 * @Author: MiaoHongShuai
 * @Description: 装饰器模式测试类
 * @Date: Created on 2017/9/12
 * @Modified By:
 */
public class DecoratorTest {
    public static void main(String[] args) {
        //武器
        Equip equip = new ArmEquip();
        //强化后
        Equip decoratorEquip = new BlueGemDecorator(equip);

        //强化后的属性调用：
        System.out.println("攻击力: " + decoratorEquip.caculateAttack());
        System.out.println("装备描述: " + decoratorEquip.description());
    }
}
