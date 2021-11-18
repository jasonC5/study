package com.jason.study.juc.lock;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
        };
        Thread threadA = new Thread(runnable);
        threadA.setName("A");
        Thread threadB = new Thread(runnable);
        threadB.setName("B");
        Thread threadC = new Thread(()->{
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("go go go ");
        });

        threadA.start();
        threadB.start();
        threadC.start();
        latch.await();
        System.out.println("finished");
    }
}
