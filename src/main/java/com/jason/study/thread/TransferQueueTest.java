package com.jason.study.thread;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * 交替打印
 */
public class TransferQueueTest {

    public static void main(String[] args) {
        char []a = {'a','b','c','d','e','f','g','h','i'};
        int []b = {1,2,3,4,5,6,7,8,9};
        TransferQueue transferQueueA = new LinkedTransferQueue();
        TransferQueue transferQueueB = new LinkedTransferQueue();
        new Thread(()->{
            for (int i=0;i<a.length;i++) {
                try {
//                    transferQueueA.put(a[i]);
                    transferQueueA.transfer(a[i]);
                    Object take = transferQueueB.take();
                    System.out.print(take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"TA").start();

        new Thread(()->{
            for (int i=0;i<b.length;i++) {
                try {
                    Object take = transferQueueA.take();//等先打印a再往下走，阻塞住
//                    transferQueueB.put(b[i]);
                    transferQueueB.transfer(b[i]);
                    System.out.print(take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"TB").start();
    }
}
