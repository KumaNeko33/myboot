package com.shuai.test.dao;

import java.util.Comparator;

/***
 * @Description: 排序器接口(策略模式: 将算法封装到具有共同接口的独立的类中使得它们可以相互替换)
 * @author: MiaoHongShuai
 * @Date: 2017/8/28 0028

*/
public interface Sorter {

    /**
     * 排序
     * @param array 待排序的数组
     */
    <T> void sort(T[] array);

    /**
     * 排序
     * @param array 待排序的数组
     * @param comparator 比较两个对象的比较器
     * @param <T>
     */
    <T> void sort(T[] array, Comparator comparator);
}
