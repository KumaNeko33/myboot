package com.shuai.test.designModel.decorator;

/**
 * @Author: MiaoHongShuai
 * @Description: 蓝宝石装饰实现类，用于装饰装备Equip
 * @Date: Created on 2017/9/12
 * @Modified By:
 */
public class BlueGemDecorator implements EquipDecorator {
    private Equip equip;

    public BlueGemDecorator(Equip equip) {
        this.equip = equip;
    }
    @Override
    public int caculateAttack() {
        return 15 + equip.caculateAttack();
    }

    @Override
    public String description() {
        return equip.description() + "+蓝宝石";
    }
}
