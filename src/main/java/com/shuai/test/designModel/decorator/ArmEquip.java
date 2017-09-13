package com.shuai.test.designModel.decorator;

/**
 * @Author: MiaoHongShuai
 * @Description: 武器，实现装备接口Equip
 * 攻击力20
 * @Date: Created on 2017/9/12
 * @Modified By:
 */
public class ArmEquip implements Equip{
    @Override
    public int caculateAttack() {
        return 20;
    }

    @Override
    public String description() {
        return "屠龙刀";
    }
}
