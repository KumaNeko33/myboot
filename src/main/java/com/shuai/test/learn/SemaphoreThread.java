package com.shuai.test.learn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author: MiaoHongShuai
 * @Description:java Semaphore 信号量的使用：
在java中，提供了信号量Semaphore的支持。
Semaphore类是一个计数信号量，必须由获取它的线程释放，
通常用于限制可以访问某些资源（物理或逻辑的）线程数目。

一个信号量有且仅有3种操作，且它们全部是原子的：初始化、增加和减少
增加可以为一个进程解除阻塞；
减少可以让一个进程进入阻塞。

信号量维护一个许可集，若有必要，会在获得许可之前阻塞每一个线程：
//从此信号量获取给定数目的许可，在提供这些许可前一直将线程阻塞。
acquireUninterruptibly(int permits){}
每一个release()添加一个许可，从而可能释放一个正在阻塞的获取者。
Semaphore只对可用许可的号码进行计数，并采取相应的行动。

如何获得Semaphore对象？
public Semaphore(int permits,boolean fair)
permits:初始化可用的许可数目。
fair: 若该信号量保证在征用时按FIFO的顺序授予许可，则为true，否则为false；

如何从信号量获得许可？
public void acquire() throws InterruptedException

如何释放一个许可，并返回信号量？
public void release()

代码实例：
20个人去银行存款，但是该银行只有两个办公柜台，有空位则上去存钱，没有空位则只能去排队等待
 * @Date: Created on 2017/9/4
 * @Modified By:
 */
public class SemaphoreThread {
    private int a = 0;

    /**
     * 银行存钱类
     */
    class Bank {
        private int account = 100;

        public int getAccount() {
            return account;
        }

        public void save(int money) {
            account += money;
        }
    }

    /**
     * 线程执行类，每次存10块钱
     */
    class NewThread implements Runnable {
        private Bank bank;
        private Semaphore semaphore;

        public NewThread(Bank bank, Semaphore semaphore) {
            this.bank = bank;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            int b = a++;
            if (semaphore.availablePermits() > 0) {//返回当前信号量还有几个可用的许可
                System.out.println("线程" + b + "启动，进入银行,有位置立即去存钱");
            } else {
                System.out.println("线程" + b + "启动，进入银行,无位置，去排队等待等待");
            }
            try {
                semaphore.acquire();//在当前信号量中获取一个许可.当前线程会一直阻塞直到有一个可用的许可,或被其他线程中断.
                bank.save(10);
                System.out.println(b + "账户余额为：" + bank.getAccount());
                Thread.sleep(1000);
                System.out.println("线程" + b + "存钱完毕，离开银行");
                semaphore.release();//释放一个许可,把让它返回到这个信号量中.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 建立线程，调用内部类，开始存钱
     */
    public void useThread() {
        Bank bank = new Bank();
        // 定义2个新号量
        Semaphore semaphore = new Semaphore(2);//信号量设为2，只允许2个线程同时连接
        // 建立一个缓存线程池
        ExecutorService es = Executors.newCachedThreadPool();
        // 建立10个线程
        for (int i = 0; i < 10; i++) {
            // 执行一个线程
            es.submit(new Thread(new NewThread(bank, semaphore)));
        }
        // 关闭线程池
        es.shutdown();

        // 主线程从信号量中获取两个许可，当前线程会一直阻塞直到有两个可用的许可.Un指Until直到的意思
        semaphore.acquireUninterruptibly(2);
        System.out.println("到点了，工作人员要吃饭了");
        // 释放两个许可，并将其返回给信号量
        semaphore.release(2);
    }

    public static void main(String[] args) {
        SemaphoreThread test = new SemaphoreThread();
        test.useThread();
    }
}
