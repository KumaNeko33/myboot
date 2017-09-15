package com.shuai.test.controller;

import com.shuai.test.dao.Sorter;
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

import java.io.*;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Comparator;
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
public class StoreController implements Sorter{

    @Autowired
    private StoreService storeService;

    @RequestMapping(value = "/month", method = RequestMethod.GET)
    public Result<Map> getPointAndRebate() {
//        Store store = storeService.getStore();
        StoreDto storeDto = StoreDto.builder()
                .storeId(16L)
                .build();
        //普通的获取年月日 时分秒
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH)); //0-11
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println(calendar.get(Calendar.MINUTE));
        System.out.println(calendar.get(Calendar.SECOND));
        //java8获取年月日 时分秒
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt.getYear());
        System.out.println(ldt.getMonthValue()); //1-12
        System.out.println(ldt.getDayOfMonth());
        System.out.println(ldt.getHour());
        System.out.println(ldt.getMinute());
        System.out.println(ldt.getSecond());
        try {
//            怎样将GB2312编码的字符串转换为ISO-8859-1编码的字符串？
            String name = "哈哈";
            String newName = new String(name.getBytes("GB2312"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        //冒泡排序
        int[] arr0 = {2, 1, 7, 33, 22, 11, 88, 55, 66, 23, 12};
        bubbleSort(arr0, arr0.length - 1, arr0.length - 1);
        display(arr0);
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
     * 递归实现冒泡排序，从小到大
     * @param array
     * @param m
     * @param n
     */
    private static void bubbleSort(int[] array, int m, int n) {
        int temp;
        if (m == 0 || n == 0) {
            return;
        }
        if (m > 0) {
            if (array[n - 1] > array[n]) {
                temp = array[n - 1];
                array[n - 1] = array[n];
                array[n] = temp;
            }
            bubbleSort(array, n - 1, m);//这个递归一圈后，数组第一个元素变成最小
//            bubbleSort(array, m, n - 1);//这个递归一圈后，数组第一个元素变成最小
        }
        bubbleSort(array, n, m - 1);
    }

    //产生10个100到200 之间的随机数进行测试
//    void main()
//    {
//
//        time_t tms;
//        int num[10];
//        int i;
//
//        srand((unsigned int)time(&tms));
//
//        for(i=0;i<10;i++)
//        {
//            *(num+i) = 100 + rand()%100;
//            printf("%d\n",*(num+i));
//        }
//        sort(num,9,9); //调用递归排序
//
//        printf("After sorted\n");
//        for(i=0;i<10;i++)
//        {
//            printf("%d\n",*(num+i));
//        }
//        getchar();
//    }
    /**
     * 冒泡排序高级版
     */
    @Override
    public <T> void sort(T[] array) {
        boolean flag = true;
        for (int i = 1,len = array.length; i < len && flag; i++) {
            flag = false;
            for (int j = 0; j < len - i; j++) {
//                if (array[j].compareTo(array[j + 1]) > 0) {
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j+1] = temp;
                    flag = true;
//                }
            }
        }
    }
    @Override
    public <T> void sort(T[] list, Comparator comparator) {
        boolean swapped = true;
        for (int i = 1, len = list.length; i < len && swapped; ++i) {
            swapped = false;
            for (int j = 0; j < len - i; ++j) {
                if (comparator.compare(list[j], list[j + 1]) > 0) {
                    T temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                    swapped = true;
                }
            }
        }
    }

    /**
     * 选择排序增序,选择过程中只交换数组的下标，每一次交换完成后才进行数组元素的位置交换(从小到大)，时间复杂度O(n^2)
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
     * 插入排序：（从左到右，若左小于右则左右交换）逐渐形成一个有序队列，然后后面的元素不断往这个有序队列里插入对应位置即可,时间复杂度O(n^2)
     */
    public static void insertSort(int[] arr) {
        int i, j;
        int insert;
        for (i = 1; i < arr.length; i++) {
            insert = arr[i];
            j = i - 1;
            while (j >= 0 && insert < arr[j]) {//轮询队列，左大于右的话，将左的值赋给右位置，保证右边的比左边大，直到相等或小于时进行插入
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = insert;
//            display(arr);
        }
    }

    public static void insertSort2(int[] arr) {
//        int in = 1;
        int insert;
        for (int in = 1; in < arr.length; in++) {
            insert = arr[in];
            while (in-1 >= 0 && insert < arr[in - 1]) {
                arr[in] = arr[in - 1];
                in--;
            }
            arr[in] = insert;
//            swap(index, in, arr);
        }
    }

    /**
     * 二分查找：对有序数组进行查找 元素，
     * 第一次循环，在N/2的范围内查找
     * 第二次循环，在N/(2^2)的范围内查找
     * 考虑最坏情况，N/(2^x)=1时才找到，即x=log2(N),即时间复杂度O(logN)
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
     * 快速排序：快排，排序算法，对包含n个数的输入数组，最坏情况运行时间为O(n^2)。快排通常是用于排序的最佳实用选择，平均性能相当好时间复杂度为O(nlogn),且O(nlogn)记号中隐含的常数因子很小。
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

    //递归利用partition(分割)函数进行快速排序：
    public static void quicksort(int[] arr, int start, int end) {
        if (start < end) {
            int middle = (start + end) / 2;
            int q = partition(arr, start, end, middle);
            quicksort(arr, start, q - 1);
            quicksort(arr, q + 1, end);
        }
    }

    //二叉 查找/排序 树：二叉搜索树又称为二叉查找树，二叉排序树BST（Binary Search/Sort Tree），是满足以下条件的二叉树：
    // 1.左子树上的所有节点值均小于根节点值，
    // 2右子树上的所有节点值均不小于根节点值，
    // 3，左右子树也满足上述两个条件。（左小右大）
    //    如果二叉排序树是平衡的，则n个节点的二叉排序树的高度为Log2n+1,其查找效率为O(log2n)，即O(logN)近似于折半查找。
//    如果二叉排序树完全不平衡，则其深度可达到n，查找效率为O(n)，退化为顺序查找。
//    一般的，二叉排序树的查找性能在O(logN)到O(n)之间。因此，为了获得较好的查找性能，就要构造一棵平衡的二叉排序树。

    //二叉搜索树的后序遍历序列
/*    【题目】输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。
    后序遍历的顺序：左右根

    二叉搜索树左子树的值小于结点值，右子树的值大于结点值

    最后一个为根结点，最重要是找到左字数和右字数的分界

    举例：{5，7，6，9，11，10，8}*/
    public static Boolean verifySequenceOfBST(int[] sequence) {
        if (sequence.length == 0) {
            return false;
        }
        if (sequence.length == 1) {
            return true;
        }
        return verigy(sequence, 0, sequence.length - 1);
    }

    private static Boolean verigy(int[] sequence, int start, int end) {
        //后序遍历，最后一个数8为根节点，思路：递归，每个子树从倒数第二个后往前，比根节点大的都是右子树遍历的结果，直到比根节点小的，开始都是左子树遍历的结果，这时如果出现比根节点大的
        //则不符合二叉搜索树的后序遍历规则，返回false
        int index = end - 1;
        while (index >= 0 && sequence[index] > sequence[end]) {
            index--;
        }
        for (int i = start; i < index; i++) {
            if (sequence[i] > sequence[end]) {
                return false;
            }
        }
        return true;
    }

//    private static Boolean verify(int[] arr, int start, int end) {
//        int root = arr[end];
//        int idx = end - 1;
//        while (idx >= 0 && arr[idx] > root) {
//            idx--;
//        }
//        for (int i = start; i < idx; i++) {
//            if (arr[i] > root) {
//                return false;
//            }
//        }
//        return true;
//    }

    //B-Tree(多叉平衡查找树 balanced tree)中的m指层数：
//    M为树的阶数，B-树或为空树，否则满足下列条件：
//    定义任意非叶子结点最多只有M个儿子；且M>2；
//            2.根结点的儿子数为[2, M]；
//            3.除根结点以外的非叶子结点的儿子数为[M/2, M]；
//            4.每个结点存放至少M/2-1（取上整）和至多M-1个关键字；（至少2个关键字,根节点至少一个关键字）；
//            5.非叶子结点的关键字个数=指向儿子的指针个数-1；
//            6.非叶子结点的关键字：K[1], K[2], …, K[m-1]，m<M+1；且K[i]< K[i+1] ；
//            7.非叶子结点的指针：P[1], P[2], …, P[m]；其中P[1]指向关键字小于K[1]的子树，P[m]指向关键字大于K[m-1]的子树，其它P[i]指向关键字属于(K[i-1], K[i])的子树；
//            8.所有叶子结点位于同一层；
//    如：（M=3）
//    M=3的B-树
//            M=3的B-树
//    B-树的搜索，从根结点开始，对结点内的关键字（有序）序列进行二分查找，如果命中则结束，否则进入查询关键字所属范围的儿子结点；重复，直到所对应的儿子指针为空，或已经是叶子结点。


    //哈希表：
//    1.Hash主要用于信息安全领域中加密算法，它把一些不同长度的信息转化成杂乱的128位的编码,这些编码值叫做Hash值.

//    2.查找：哈希表，又称为散列，是一种更加快捷的查找技术。我们之前的查找，都是这样一种思路：
//    集合中拿出来一个元素，看看是否与我们要找的相等，如果不等，缩小范围，继续查找。
//    而哈希表是完全另外一种思路：当我知道key值以后，我就可以直接计算出这个元素在集合中的位置，根本不需要一次又一次的查找！
//    举一个例子，假如我的数组A中，第i个元素里面装的key就是i，那么数字3肯定是在第3个位置，数字10肯定是在第10个位置。
//    哈希表就是利用利用这种基本的思想，建立一个从key到位置的函数，然后进行直接计算查找。
//    哈希函数/散列函数：元素特征转变为数组下标的方法。也可以说，散列函数就是找到一种数据内容和数据存放地址之间的映射关系。
//    我想大家都在想一个很严重的问题：“如果两个字符串在哈希表中对应的位置相同怎么办？”,毕竟一个数组容量是有限的，这种可能性很大。
// 解决该问题的方法很多，我首先想到的就是用“链表”。我遇到的很多算法都可以转化成链表来解决，只要在哈希表的每个入口挂一个链表，保存所有对应的字符串就OK了。

//    3.哈希表/散列表的查找步骤：
//    当存储记录时，通过散列函数计算出记录（数据内容key）的散列地址（数据存放地址）
//    当查找记录时，我们通过同样的是散列函数计算记录的散列地址，并按此散列地址访问该记录

//    4.关键字——散列函数（哈希函数）——散列地址
//    优点：一对一的查找效率很高；
//    缺点：一个关键字可能对应多个散列地址；需要查找一个范围时，效果不好。
//    散列冲突：不同的关键字经过散列函数的计算得到了相同的散列地址。  解决办法：数组加链表
//    好的散列函数=计算简单+分布均匀（计算得到的散列地址分布均匀）
//    哈希表是种数据结构，它可以提供快速的插入操作和查找操作。

//    5.优缺点
//    优点：不论哈希表中有多少数据，查找、插入、删除（有时包括删除）只需要接近常量的时间即0(1）的时间级。实际上，这只需要几条机器指令。
//    哈希表运算得非常快，在计算机程序中，如果需要在一秒种内查找上千条记录通常使用哈希表（例如拼写检查器)哈希表的速度明显比树快，树的操作通常需要O(N)的时间级。
//    哈希表不仅速度快，编程实现也相对容易。
//    如果不需要有序遍历数据，并且可以提前预测数据量的大小。那么哈希表在速度和易用性方面是无与伦比的。
//
//    缺点：它是基于数组的，数组创建后难于扩展，某些哈希表被基本填满时，性能下降得非常严重，
//    所以程序员必须要清楚表中将要存储多少数据（或者准备好定期地把数据转移到更大的哈希表中，这是个费时的过程）。

//哈西冲突：
//    哈西冲突的解决办法：平方探测再散列中：当bucketIndex-1^2 < 0时，会放到哈希表Entry[] table最后一个位置
//    哈希表为解决冲突，可以采用开放地址法和链地址法等来解决问题，Java中HashMap采用了链地址法。链地址法，简单来说，就是数组加链表的结合。
//    哈希表Entry[] table在java1.8中，改成Node[] table
// static class Node<K,V> implements Map.Entry<K,V> {
//    final int hash;    //用来定位数组索引位置
//    final K key;
//    V value;
//    Node<K,V> next;   //链表的下一个node
//
//    Node(int hash, K key, V value, Node<K,V> next) { ... }
//    public final K getKey(){ ... }
//    public final V getValue() { ... }
//    public final String toString() { ... }
//    public final int hashCode() { ... }
//    public final V setValue(V newValue) { ... }
//    public final boolean equals(Object o) { ... }
//}
//      在每个数组元素上都一个链表结构，当数据被Hash后，得到数组下标，把数据放在对应下标元素的链表上。例如程序执行下面代码：
//            map.put("美团","小美");
//      系统将调用"美团"这个key的hashCode()方法得到其hashCode 值（该方法适用于每个Java对象），
//    然后再通过Hash算法的后两步运算（高位运算和取模运算，下文有介绍）来定位该键值对的存储位置，有时两个key会定位到相同的位置，表示发生了Hash碰撞。
//    当然Hash算法计算结果越分散均匀，Hash碰撞的概率就越小，map的存取效率就会越高。
//
//      如果哈希桶数组很大，即使较差的Hash算法也会比较分散，但是空间成本增加；如果哈希桶数组数组很小，即使好的Hash算法也会出现较多碰撞，碰撞越多所产生的链表长度就会越来越长，这样势必会影响HashMap的速度
//    所以就需要在空间成本和时间成本之间权衡，其实就是在根据实际情况确定哈希桶数组的大小，并在此基础上设计好的hash算法减少Hash碰撞。
//    那么通过什么方式来控制map使得Hash碰撞的概率又小，哈希桶数组（Node[] table）占用空间又少呢？答案就是好的Hash算法和扩容机制。
//    即使负载因子和Hash算法设计的再合理，也免不了会出现拉链过长的情况，一旦出现拉链过长，则会严重影响HashMap的性能,因为链表的查询速度比较慢，链表长,就需要循环链表。
//    于是，在JDK1.8版本中，对数据结构做了进一步的优化，引入了红黑树。而当链表长度太长（默认超过8）时，链表就转换为红黑树，
//    利用红黑树快速增删改查的特点O(logN)提高HashMap的性能，其中会用到红黑树的插入、删除、查找等算法。

//    针对上面的扩展：随着HashMap中元素的数量越来越多，发生碰撞的概率就越来越大，所产生的链表长度就会越来越长，这样势必会影响HashMap的速度
//            (为啥呢，原来是直接找到数组的index就可以直接根据key取到值了，但是冲突严重，也就是说链表长，那就得循环链表了，时间就浪费在循环链表上了，也就慢了)，
//    为了保证HashMap的效率，系统必须要在某个临界点进行扩容处理。该临界点在当HashMap中元素的数量等于table数组长度*加载因子。
//    但是扩容是一个非常耗时的过程，因为它需要重新计算这些数据在新table数组中的位置并进行复制处理。
//    所以如果我们已经预知HashMap中元素的个数，那么预设元素的个数能够有效的提高HashMap的性能。

//    resize()扩容的时候，方法里又将Entry链的顺序反转了

}


//.一道关于飞机加油的问题，已知：
//        每个飞机只有一个油箱，
//        飞机之间可以相互加油（注意是相互，没有加油机）
//        一箱油可供一架飞机绕地球飞半圈，
//        问题：
//        为使至少一架飞机绕地球一圈回到起飞时的飞机场，至少需要出动几架飞机？
//        （所有飞机从同一机场起飞，而且必须安全返回机场，不允许中途降落，中间没有飞机场）
//    飞1/8时；a、b、c剩3/8，c分a、b各1/8，a、b剩4/8,4/8， 飞1/4时，3/8,3/8,b给a1/8，飞回， a4/8  飞到3/4，a飞到1/2时，b、c装满油倒飞回来1/8位置，bc剩3/8,c给b加油2/8后飞回，b剩5/8,这时a剩1/4油，然后a飞到3/4，b倒飞到1/4，a0油，b1/2，分给a1/4，刚好两架飞机飞回
//            假定让飞机a，飞回终点，邮箱最多能装1/2路程的油，就是说飞机a飞到1/2路程时，后面还需要加1/2油
//故三架飞机即可

/*
5、原型模式（Prototype）
原型模式虽然是创建型的模式，但是与工程模式没有关系，从名字即可看出，该模式的思想就是将一个对象作为原型，对其进行复制、克隆，产生一个和原对象类似的新对象。本小结会通过对象的复制，进行讲解。在Java中，复制对象是通过clone()实现的，先创建一个原型类：

public class Prototype implements Cloneable {
    public Object clone() throws CloneNotSupportedException {
        Prototype proto = (Prototype) super.clone();
        return proto;
    }
}
很简单，一个原型类，只需要实现Cloneable接口，覆写clone方法，此处clone方法可以改成任意的名称，因为Cloneable接口是个空接口，你可以任意定义实现类的方法名，
如cloneA或者cloneB，因为此处的重点是super.clone()这句话，super.clone()调用的是Object的clone()方法，而在Object类中，clone()是native的，具体怎么实现，
我会在另一篇文章中，关于解读Java中本地方法的调用，此处不再深究。在这儿，我将结合对象的浅复制和深复制来说一下，首先需要了解对象深、浅复制的概念：

浅复制：将一个对象复制后，基本数据类型的变量都会重新创建，而引用类型，指向的还是原对象所指向的。

深复制：将一个对象复制后，不论是基本数据类型还有引用类型，都是重新创建的。简单来说，就是深复制进行了完全彻底的复制，而浅复制不彻底。

此处，写一个深浅复制的例子：

public class Prototype implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    private String string;

    private SerializableObject obj;

    // 浅复制
    public Object clone() throws CloneNotSupportedException {
        Prototype proto = (Prototype) super.clone();
        return proto;
    }

    // 深复制
    public Object deepClone() throws IOException, ClassNotFoundException {

        // 写入当前对象的二进制流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);//使用了 装饰器设计模式
        oos.writeObject(this);//将当前对象写入流中成为字节数组

// 改良版：try(ByteArrayOutputStream baos = new ByteArrayOutputStream();ObjectOutputStream oos = new ObjectOutputStream(baos)){
//            oos.writeObject(this);
//         }

        // 读出二进制流产生的新对象
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();//将流中的字节数组读取并转换成对象，然后return返回当前对象的深克隆

// 改良版：try(ByteArrayInputStream bais = new ByteArrayInputStream();ObjectInputStream ois = new ObjectInputStream(bais)){
//            return ois.readObject();
//        }
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public SerializableObject getObj() {
        return obj;
    }

    public void setObj(SerializableObject obj) {
        this.obj = obj;
    }
}

class SerializableObject implements Serializable {
    private static final long serialVersionUID = 1L;
}

要实现深复制，需要采用流的形式读入当前对象的二进制输入，再写出二进制数据对应的对象。
 */

/*
7、装饰模式（Decorator）:可以有效的替代继承，装饰对象

顾名思义，装饰模式就是给一个对象增加一些新的功能，而且是动态的，要求装饰对象和被装饰对象实现同一个接口，装饰对象持有被装饰对象的实例，关系图如下：

这里写图片描述

Source类是被装饰类，Decorator类是一个装饰类，可以为Source类动态的添加一些功能，代码如下：

已存在：
public interface SourceAble {
    public void method();
}
public class Source implements SourceAble{
    @Override
    public void method(){
        System.out.println("the original method!");
    }
}
装饰类:与被装饰类实现同一接口：装饰Source类，扩展该类的方法，替代继承。
public class Decorator implements SourceAble{
    private SourceAble source; //被装饰类对象变量
    public Decorator(SourceAble source){ //通过构造函数传入被装饰类对象
        super();
        this.source = source; //令 本地的被装饰类对象 等于 传入的被装饰类对象
    }
    @Override
    public void method(){ //装饰 传入的被装饰类对象的方法
        before();
        source.method();
        after();
    }

    public void before(){
        System.out.println("before Decorator!");
    }
    public void after(){
        System.out.println("after Decorator!");
    }
}
````
测试类：
public class DecoratorTest {
    public static void main(String[] args) {
        Sourceable source = new Source();//创建被装饰类对象
        Sourceable obj = new Decorator(source);//通过传入被装饰类对象 创建装饰类对象
        obj.method();//调用装饰类对象的装饰方法
    }
}
输出：

before decorator!
the original method!
after decorator!

装饰器模式的应用场景：

1、需要扩展一个类的功能。

2、动态的为一个对象增加功能，而且还能动态撤销。（继承不能做到这一点，继承的功能是静态的，不能动态增删。）

缺点：产生过多相似的对象，不易排错！

实际运用：字节、字符流操作对象InputStream，BufferReader,FileInputStream等
 */
/*
8、代理模式（Proxy）:代理类

其实每个模式名称就表明了该模式的作用，代理模式就是多一个代理类出来，替原对象进行一些操作，比如我们在租房子的时候回去找中介，为什么呢？因为你对该地区房屋的信息掌握的不够全面，希望找一个更熟悉的人去帮你做，此处的代理就是这个意思。再如我们有的时候打官司，我们需要请律师，因为律师在法律方面有专长，可以替我们进行操作，表达我们的想法。先来看看关系图：

根据上文的阐述，代理模式就比较容易的理解了，我们看下代码：

public interface Sourceable {
    public void method();
}
public class Source implements Sourceable {
    @Override
    public void method() {
        System.out.println("the original method!");
    }
}
public class Proxy implements Sourceable {
    private Source source;
    public Proxy(){//代理模式的构造函数不需要传入被装饰类，这点是和装饰模式的最大不同
        super();
        this.source = new Source();//直接创建需要代理的类 的对象即可
    }
    @Override
    public void method() {
        before();
        source.method();
        after();
    }
    private void after() {
        System.out.println("after proxy!");
    }
    private void before() {
        System.out.println("before proxy!");
    }
}
测试类：
public class ProxyTest {
    public static void main(String[] args) {
        Sourceable source = new Proxy();//不依赖被代理类的已创建对象，只要有个被代理类即可
        source.method();
    }
}
输出：
before proxy!
the original method!
after proxy!

代理模式的应用场景：

如果已有的方法在使用的时候需要 对原有的方法进行改进 ，此时有两种办法：

1、修改原有的方法来适应。这样违反了“对扩展开放，对修改关闭”的原则。

2、就是采用一个代理类 调用原有的方法，且对产生的结果进行控制。这种方法就是代理模式。

使用代理模式，可以将功能划分的更加清晰，有助于后期维护！

装饰模式：装饰  对象
代理模式：代理  类
 */

/*
9、外观模式（Facade，也称门面模式）
外观模式是为了解决类与类之间的依赖关系的，像spring一样，可以将类和类之间的关系配置到配置文件中，而外观模式就是将他们的关系放在一个Facade类中，
降低了类类之间的耦合度，该模式中没有涉及到接口，看下类图：（我们以一个计算机的启动过程为例）

我们先看下实现类：

public class CPU {
    public void startup(){
        System.out.println("cpu startup!");
    }

    public void shutdown(){
        System.out.println("cpu shutdown!");
    }
}

public class Memory {
    public void startup(){
        System.out.println("memory startup!");
    }

    public void shutdown(){
        System.out.println("memory shutdown!");
    }
}

public class Disk {
    public void startup(){
        System.out.println("disk startup!");
    }

    public void shutdown(){
        System.out.println("disk shutdown!");
    }
}

public class Computer {
    private CPU cpu;
    private Memory memory;
    private Disk disk;

    public Computer(){
        cpu = new CPU();
        memory = new Memory();
        disk = new Disk();
    }

    public void startup(){
        System.out.println("start the computer!");
        cpu.startup();
        memory.startup();
        disk.startup();
        System.out.println("start computer finished!");
    }

    public void shutdown(){
        System.out.println("begin to close the computer!");
        cpu.shutdown();
        memory.shutdown();
        disk.shutdown();
        System.out.println("computer closed!");
    }
}
User类如下：
public class User {

    public static void main(String[] args) {
        Computer computer = new Computer();
        computer.startup();
        computer.shutdown();
    }
}
输出：

start the computer!
cpu startup!
memory startup!
disk startup!
start computer finished!
begin to close the computer!
cpu shutdown!
memory shutdown!
disk shutdown!
computer closed!

如果我们没有Computer类，那么，CPU、Memory、Disk他们之间将会相互持有实例，产生关系，这样会造成严重的依赖，修改一个类，可能会带来其他类的修改，
这不是我们想要看到的，有了Computer类，他们之间的关系被放在了Computer类里，这样就起到了解耦的作用，这，就是外观（门面）模式！
 */

/*
13、策略模式（strategy）

策略模式定义了一系列算法，并将每个算法封装起来，使他们可以相互替换，且算法的变化不会影响到使用算法的客户，算法也相当于组成客户（人)的肢体（肢体通过接口和实现类来构造，客户中已经存在了肢体的槽位，只要在构造客户）。需要设计一个接口，为一系列实现类提供统一的方法，
多个实现类实现该接口，设计一个抽象类（可有可无，属于辅助类），提供辅助函数，关系图如下：

图中ICalculator提供同意的方法，
AbstractCalculator是辅助类，提供辅助方法，接下来，依次实现下每个类：

首先统一接口：
public interface ICalculator {
    public int calculate(String exp);
}
辅助类：
public abstract class AbstractCalculator {
    public int[] split(String exp,String opt){
        String array[] = exp.split(opt);
        int arrayInt[] = new int[2];
        arrayInt[0] = Integer.parseInt(array[0]);
        arrayInt[1] = Integer.parseInt(array[1]);
        return arrayInt;
    }
}
三个实现类：
public class Plus extends AbstractCalculator implements ICalculator {
    @Override
    public int calculate(String exp) {
        int arrayInt[] = split(exp,"\\+");
        return arrayInt[0]+arrayInt[1];
    }
}

public class Minus extends AbstractCalculator implements ICalculator {
@Override
public int calculate(String exp) {
        int arrayInt[] = split(exp,"-");
        return arrayInt[0]-arrayInt[1];
    }
}

public class Multiply extends AbstractCalculator implements ICalculator {
@Override
    public int calculate(String exp) {
        int arrayInt[] = split(exp,"\\*");
        return arrayInt[0]*arrayInt[1];
    }
}
简单的测试类：
public class StrategyTest {
    public static void main(String[] args) {
        String exp = "2+8";
        ICalculator cal = new Plus();
        int result = cal.calculate(exp);
        System.out.println(result);
    }
}
输出：10

策略模式的决定权在用户，系统本身提供不同算法的实现，新增或者删除算法，对各种算法做封装。因此，策略模式多用在算法决策系统中，外部用户只需要决定用哪个算法即可。
 */

/*
15、观察者模式（Observer）：对复杂依赖的管理

包括这个模式在内的接下来的四个模式，都是类和类之间的关系，不涉及到继承，学的时候应该 记得归纳，记得本文最开始的那个图。
观察者模式很好理解，类似于邮件订阅和RSS订阅，当我们浏览一些博客或wiki时，经常会看到RSS图标，就这的意思是，当你订阅了该文章，如果后续有更新，会及时通知你。
其实，简单来讲就一句话：当一个对象变化时，其它依赖该对象的对象都会收到通知，并且随着变化！对象之间是一种一对多的关系。先来看看关系图：

我解释下这些类的作用：MySubject类就是我们的主对象，Observer1和Observer2是依赖于MySubject的对象，当MySubject变化时，Observer1和Observer2必然变化。
AbstractSubject类中定义着需要监控的对象列表，可以对其进行修改：增加或删除被监控对象，且当MySubject变化时，负责通知在列表内存在的对象。我们看实现代码：

一个Observer接口：

public interface Observer {
    public void update();
}
两个实现类：

public class Observer1 implements Observer {
    @Override
    public void update() {
        System.out.println("observer1 has received!");
    }
}
public class Observer2 implements Observer {
    @Override
    public void update() {
        System.out.println("observer2 has received!");
    }
}

Subject接口及实现类：
public interface Subject {
    //增加观察者
    public void add(Observer observer);

    //删除观察者
    public void del(Observer observer);

    //通知所有的观察者
    public void notifyObservers();

    //自身的操作
    public void operation();
}
public abstract class AbstractSubject implements Subject {
    private Vector<Observer> vector = new Vector<Observer>();
    @Override
    public void add(Observer observer) {
        vector.add(observer);
    }

    @Override
    public void del(Observer observer) {
        vector.remove(observer);
    }

    @Override
    public void notifyObservers() {
        Enumeration<Observer> enumo = vector.elements();
        while(enumo.hasMoreElements()){
            enumo.nextElement().update();
        }
    }
}
public class MySubject extends AbstractSubject {
    @Override
    public void operation() {
        System.out.println("update self!");
        notifyObservers();
    }
}

测试类：
public class ObserverTest {
    public static void main(String[] args) {
        Subject sub = new MySubject();
        sub.add(new Observer1());
        sub.add(new Observer2());

        sub.operation();
    }
}
输出：

        update self!
        observer1 has received!
        observer2 has received!

        这些东西，其实不难，只是有些抽象，不太容易整体理解，建议读者：根据关系图，新建项目，自己写代码（或者参考我的代码）,按照总体思路走一遍，这样才能体会它的思想，理解起来容易！
 */