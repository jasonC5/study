package com.jason.study.thread.ThreadPool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolTest {
    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(()->{
            System.out.println(1);
        });
        forkJoinPool.execute(()->{
            System.out.println(1);
        });
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.SECONDS);
    }
}
