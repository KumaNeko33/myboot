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
//    散列冲突：不同的关键字经过散列函数的计算得到了相同的散列地址。  解决办法：链表？
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
//    即使负载因子和Hash算法设计的再合理，也免不了会出现拉链过长的情况，一旦出现拉链过长，则会严重影响HashMap的性能。
//    于是，在JDK1.8版本中，对数据结构做了进一步的优化，引入了红黑树。而当链表长度太长（默认超过8）时，链表就转换为红黑树，
//    利用红黑树快速增删改查的特点提高HashMap的性能，其中会用到红黑树的插入、删除、查找等算法。

//    针对上面的扩展：随着HashMap中元素的数量越来越多，发生碰撞的概率就越来越大，所产生的链表长度就会越来越长，这样势必会影响HashMap的速度
//            (为啥呢，原来是直接找到数组的index就可以直接根据key取到值了，但是冲突严重，也就是说链表长，那就得循环链表了，时间就浪费在循环链表上了，也就慢了)，
//    为了保证HashMap的效率，系统必须要在某个临界点进行扩容处理。该临界点在当HashMap中元素的数量等于table数组长度*加载因子。
//    但是扩容是一个非常耗时的过程，因为它需要重新计算这些数据在新table数组中的位置并进行复制处理。
//    所以如果我们已经预知HashMap中元素的个数，那么预设元素的个数能够有效的提高HashMap的性能。

//    resize()扩容的时候，方法里又将Entry链的顺序反转了

}
