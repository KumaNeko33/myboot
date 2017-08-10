package com.shuai.test.controller;

import com.shuai.test.domain.Store;
import com.shuai.test.dto.StoreDto;
import com.shuai.test.service.StoreService;
import com.shuai.test.utils.DateUtils;
import com.shuai.test.utils.Result;
import com.shuai.test.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/7/24
 * @Modified By:
 */
@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @RequestMapping(value = "/month", method = RequestMethod.GET)
    public Result<Map> getPointAndRebate() {
//        Store store = storeService.getStore();
        StoreDto storeDto = StoreDto.builder()
//                .storeId(16L)
                .build();

        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();
        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);

        Integer year = DateUtils.getYear(new Date());
        Integer month = DateUtils.getMonth(new Date());
        Assert.notNull(storeDto.getStoreId(), "门店用户信息不能为空！");
        Result<Map> pointAndRebate = storeService.getPointAndRebate(storeDto, year, month);
        return pointAndRebate;
    }

    public static Result dataRecheck() {
        Store store = null;
        if (store == null) {
            return ResultGenerator.genFailResult("订单不存在");
        }
        return ResultGenerator.genSuccessResult();
    }

    public static void main(String[] args) {
        Clock clock = Clock.systemDefaultZone();
        //选择排序
        long millis = clock.millis();
        int[] arr = {2, 1, 7, 33, 22, 11, 88, 55, 66, 23, 12};
        selectSort(arr);
        display(arr);   //[1,2,7,11,12,22,23,33,55,66,88]
        long millis2 = clock.millis();
        System.out.println(millis2 - millis);//规模太小，为0,
        System.out.println();
        //插入排序
        millis = clock.millis();
        int[] arr2 = {2, 1, 7, 33, 22, 11, 88, 55, 66, 23, 12};
        insertSort(arr2);   //[1,2,7,11,12,22,23,33,55,66,88]
        display(arr2);
        millis2 = clock.millis();
        System.out.println(millis2 - millis);
        System.out.println();
        //二分查找
        int[] arr3 = {1, 2, 7, 11, 12, 22, 23, 33, 55, 66, 88};
        System.out.println("index of 55 is " + searchSort(arr3, 2));//index of 55 is 1
        System.out.println();
        //快速排序的partition函数：
        // 快速排序算法里的partition函数用来解决这样一个问题：给定一个数组arr[]和数组中任意一个元素a，重排数组使得a左边都小于它，右边都不小于它。
        // arr[]为数组，start、end分别为数组第一个元素和最后一个元素的索引
        int[] arr4 = {2, 1, 7, 33, 22, 11, 88, 55, 66, 23, 12};
        partition(arr4, 0, arr4.length - 1, 3); //将下标为3的元素作为基准：即33进行排序，
        display(arr4);  //[2,1,7,12,22,11,23,33,66,88,55]
        System.out.println();
        //快速排序
        millis = clock.millis();
        int[] arr5 = {2, 1, 7, 33, 22, 11, 88, 55, 66, 23, 12};
        quicksort(arr5,0 ,arr5.length-1);
        display(arr5);
        millis2 = clock.millis();
        System.out.println(millis2 - millis);
        System.out.println();
    }

    /**
     * 选择排序增序,选择过程中只交换数组的下标，每一次交换完成后才进行数组元素的位置交换
     *
     * @param array
     */
    public static void selectSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[index] > array[j]) {
                    index = j;
                }
            }
            if (index != i) {
                swap(i, index, array);
            }
        }
    }

    public static void display(int[] array) {
        System.out.print("[");
        for (int i = 0; i < array.length - 1; i++) {
            System.out.print(array[i] + ",");
        }
        System.out.println(array[array.length - 1] + "]");
    }

    public static void swap(int a, int b, int[] array) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    /**
     * 插入排序：逐渐形成一个有序队列，然后后面的元素不断往这个有序队列里插入对应位置即可
     */
    public static void insertSort(int[] arr) {
        int i, j;
        int insert;
        for (i = 1; i < arr.length; i++) {
            insert = arr[i];
            j = i - 1;
            while (j >= 0 && insert < arr[j]) {//轮询队列，进行插入
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = insert;
//            display(arr);
        }
    }

    /**
     * 二分查找：对有序数组进行查找 元素
     */
    public static int searchSort(int[] array, int x) {
        int max = array.length - 1;
        int min = 0;
        while (min <= max) {
            int middle = (max + min) / 2;
            if (x > array[middle]) {
                //在右半边
                min = middle + 1;
            } else if (x < array[middle]) {
                //在左半边
                max = middle - 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    /**
     * 快速排序：快排，排序算法，对包含n个数的输入数组，最坏情况运行时间为O(n2)。快排通常是用于排序的最佳实用选择，平均性能相当好时间复杂度为O(nlogn),且O(nlogn)记号中隐含的常数因子很小。
     * <p>
     * 时间复杂度：O(NlogN); 空间复杂度：O(1)
     */
    //partition函数：快速排序算法里的partition函数用来解决这样一个问题：给定一个数组arr[]和数组中任意一个元素a，重排数组使得a左边都小于它，右边都不小于它。
    // arr[]为数组，start、end分别为数组第一个元素和最后一个元素的索引
    // povitIndex为数组中任意选中的数的索引
    public static int partition(int[] arr, int start, int end, int pivotIndex) {
        {
            int pivot = arr[pivotIndex];
            swap(pivotIndex, end, arr);//将数组下标为pivotIndex的元素和数组最后一个元素交换位置
            int storeIndex = start;

            //这个循环比一般的写法简洁高效，呵呵维基百科上看到的
            for (int i = start; i < end; i++) {
                if (arr[i] < pivot) {
                    swap(storeIndex, i, arr);
                    storeIndex++;
                }
            }
            swap(storeIndex, end, arr);//经过上面的循环，storeIndex下标左边的元素都比原本下标为pivotIndex的元素值小（现在处于end位置），于是将storeIndex位置元素和end位置元素交换位置，
            //即可得到 storeIndex位置的左边元素都比他小，storeIndex位置右边的元素都比他大。而storeIndex位置的元素就是最先传进来的原本下标为pivotIndex的元素
            return storeIndex;
        }
    }

    //利用partition(分割)函数进行快速排序：
    public static void quicksort(int[] arr, int start, int end) {
        if (start < end) {
            int middle = (start + end) / 2;
            int q = partition(arr, start, end, middle);
            quicksort(arr, start, q - 1);
            quicksort(arr, q + 1, end);
        }
    }
}
