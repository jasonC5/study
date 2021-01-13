package com.jason.study.thread;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2021/1/12.
 */
public class Test_0112 {
    final private LinkedList<String> lists = new LinkedList();//线程安全
    private int count = 0;
    final private int capacity = 20;

    public synchronized void put(String item){
        while (lists.size() >= capacity) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lists.add(item);
        count ++;
        this.notifyAll();
    }
//public synchronized void put(String t) {
//    while(lists.size() == capacity) { //想想为什么用while而不是用if？
//        try {
//            this.wait(); //effective java
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    lists.add(t);
//    ++count;
//    this.notifyAll(); //通知消费者线程进行消费
//}

    public synchronized String get(){
        String item = null;
        while (lists.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        item = lists.removeFirst();
        count--;
        this.notifyAll();
        return item;
    }

//    public synchronized String get() {
//        String t = null;
//        while(lists.size() == 0) {
//            try {
//                this.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        t = lists.removeFirst();
//        count --;
//        this.notifyAll(); //通知生产者进行生产
//        return t;
//    }

    public static void main(String[] args) {
        Test_0112 obj = new Test_0112();
        //生产者
//        Runnable producer = new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i<5; i++) {//每次生产5个
//                    obj.put(Thread.currentThread().getName()+"@"+i);
//                    System.out.println(Thread.currentThread().getName() +" produce "+i);
//                }
//            }
//        };
//        Runnable consumer = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName()+" consume "+obj.get());
//            }
//        };
        for (int i = 0;i<10;i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+" consume "+obj.get());
            },"consumer_"+i).start();
        }
//        for(int i=0; i<10; i++) {
//            new Thread(()->{
//                for(int j=0; j<5; j++) System.out.println("消费"+obj.get());
//            }, "c" + i).start();
//        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0;i<2;i++) {
            new Thread(()->{
                for (int x = 0; x<50; x++) {//每次生产5个
                obj.put(Thread.currentThread().getName()+"@"+x);
                System.out.println(Thread.currentThread().getName() +" produce "+x);
                }
            },"producer_"+i).start();
        }
//        for(int i=0; i<2; i++) {
//            new Thread(()->{
//                for(int j=0; j<25; j++) obj.put(Thread.currentThread().getName() + " " + j);
//            }, "p" + i).start();
//        }

    }

}
