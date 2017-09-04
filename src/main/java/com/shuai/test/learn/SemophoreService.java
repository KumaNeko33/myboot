package com.shuai.test.learn;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/9/4
 * @Modified By:
 */
public class SemophoreService {
    private Semaphore semaphore = new Semaphore(1);

    @SuppressWarnings("unused")
    public void testFair(){
        try {
            if(semaphore.tryAcquire(3, TimeUnit.SECONDS)){//方法参数的前面的3指时间的具体数值，后面的枚举TimeUnit.SECONDS指时间类型为秒，即设置超时时间为3秒
                System.out.println( Thread.currentThread().getName() + "首先进入");
                //做一些耗时操作
                for(int i = 0; i < Integer.MAX_VALUE ; i ++){
                    String newString = new String();
                }
                semaphore.release();
            }else{
                System.out.println(Thread.currentThread().getName() + "没有得到许可");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
