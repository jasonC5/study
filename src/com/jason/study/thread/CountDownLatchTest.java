package com.jason.study.thread;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    public static void main(String[] args) throws IOException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new Thread(()->{
            try {
                countDownLatch.await();
                System.out.println("latch over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        for (int i = 0; i < 10; i++) {
            System.in.read();
            System.out.println(i);
            countDownLatch.countDown();
        }
    }
}
