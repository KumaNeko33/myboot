package com.shuai.test.domain;

import java.util.*;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/9/19
 * @Modified By:
 */
public class ListSetConvert {
//        使用list和set之间的转换达到集合元素去重： return new ArrayList<>(new HashSet<LabelEnum>(list));
//        Set与List之间转化：
//        List list = new ArrayList(set);
//        Set set = new HashSet(list);
//但是有一点,转换当中可能要丢失数据,尤其是从list转换到set的时候,因为set不能有重复数据，因为HashSet内部使用HashMap的键key来存值，来达到去重的效果，HashMap中key对应的值是全局常量对象PARSENT，
// private static final Object PRESENT = new Object();
// 还有转换到set之后,他们原先在list上的顺序就没了
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "haha", "xixi", "huihui", "haha", "aaa");//填充
        System.out.println("list:" + list);
        Set<String> set = new HashSet<>(list);//通过构造函数给set填充list中的数据，内部就是初始化后调用的addAll(Collection<T>)
//        set.addAll(list);//或者用addAll(Collection<T>)方法给set填充，这时重复的数据"haha"将只存储第一个
        System.out.println("set:" + set);
        list.clear();//清空list，不然下次把set元素加入此list的时候是在原来的基础上追加元素的
        list.addAll(set);//把set的内容填充给list
        System.out.println("list:" + list);
        /*执行结果：
        list:[haha, xixi, huihui, haha, aaa]   //说明list是有序的，可以存重复数据
        set:[aaa, haha, xixi, huihui]          //说明set是无序的，不可以存重复数据
        list:[aaa, haha, xixi, huihui]
         */
    }
}
