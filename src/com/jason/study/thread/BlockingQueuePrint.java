package com.jason.study.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueuePrint {

    public static void main(String[] args) {
        char[] a = "ABCDEFGHI".toCharArray();
        char[] b = "123456789".toCharArray();
        BlockingQueue bq1 = new ArrayBlockingQueue(1);
        BlockingQueue bq2 = new ArrayBlockingQueue(1);
        new Thread(()->{
            try {
                for (char c : b) {
                    Object take = bq1.take();
                    System.out.print(take);
                    bq2.put(c);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                for (char c : a) {
                    bq1.put(c);
                    Object take = bq2.take();
                    System.out.print(take);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
