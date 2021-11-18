package com.jason.study.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

//    public static void main(String[] args) {
//        Thread t = new Thread(() -> {
//            for (int i = 0; i < 10; i++) {
//                System.out.println(i);
//                if (i == 5) {
//                    LockSupport.park();
//                }
//                if (i == 8) {
//                    LockSupport.park();
//                }
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        t.start();
//
//        LockSupport.unpark(t);
//    }
private static final Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new MyThread1());
        Thread t2 = new Thread(new MyThread2());
        Thread t3 = new Thread(new MyThread3());
        t1.start();
        Thread.sleep(50);
        t3.start();
        Thread.sleep(50);
        t2.start();
    }

    static class MyThread1 implements Runnable {

        @Override
        public void run() {
            synchronized (obj) {
                System.out.println("thread1 start");
                try {
                    System.out.println("thread1 synchronized start");
                    obj.wait();
//                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread1 end");
            }
        }
    }


    static class MyThread3 implements Runnable {

        @Override
        public void run() {
            synchronized (obj) {
                System.out.println("thread3 start");
                try {
                    System.out.println("thread3 synchronized start");
                    obj.wait();
//                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread3 end");
            }
        }
    }

    static class MyThread2 implements Runnable {

        @Override
        public void run() {
            System.out.println("thread2 start");
            synchronized (obj) {
                System.out.println("thread2 synchronized start");
                obj.notify();//一次唤醒一个线程
                obj.notify();
//                obj.notifyAll();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread2 end");
            }
        }
    }

}
