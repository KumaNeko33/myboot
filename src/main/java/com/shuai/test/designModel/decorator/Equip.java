package com.shuai.test.designModel.decorator;

/**
 * @Author: MiaoHongShuai
 * @Description: 装备接口
 * @Date: Created on 2017/9/12
 * @Modified By:
 */
public interface Equip {
    /**
     * 计算攻击力
     * @return
     */
    int caculateAttack();

    /**
     * 装备描述
     * @return
     */
    String description();
}
