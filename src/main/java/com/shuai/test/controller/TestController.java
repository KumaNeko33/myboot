package com.shuai.test.controller;

import com.shuai.test.domain.Store;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/8/25
 * @Modified By:
 */
@Controller
@RequestMapping("/test")
public class TestController {

    public static void main(String[] args) {
        //普通的获取年月日 时分秒
        Calendar calendar = Calendar.getInstance();
        System.out.print(calendar.get(Calendar.YEAR) + "-");
        System.out.print(calendar.get(Calendar.MONTH) + "-"); //0-11 比当前月少一个月
        System.out.print(calendar.get(Calendar.DAY_OF_MONTH) + " ");
        System.out.print(calendar.get(Calendar.HOUR) + ":");
        System.out.print(calendar.get(Calendar.MINUTE) + ":");
        System.out.println(calendar.get(Calendar.SECOND));
        //java8获取年月日 时分秒
        LocalDateTime ldt = LocalDateTime.now();
        System.out.print(ldt.getYear() + "-");
        System.out.print(ldt.getMonthValue() + "-"); //1-12
        System.out.print(ldt.getDayOfMonth() + " ");
        System.out.print(ldt.getHour() + ":");
        System.out.print(ldt.getMinute() + ":");
        System.out.println(ldt.getSecond());

        //如何取得从1970年1月1日0时0分0秒到现在的毫秒数？
        System.out.println(System.currentTimeMillis());

        //如何取得某月的一共有多少天？
        System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        System.out.println(ldt.minusDays(1));


        List<Store> list = new ArrayList<>();
        list.add(new Store());

        Collections.sort(list, new Comparator<Store>() {
            @Override
            public int compare(Store o1, Store o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
        });
    }
}
