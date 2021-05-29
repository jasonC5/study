package com.jason.study.thread.ThreadPool;

import java.util.concurrent.*;

public class ExecutorThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,4,30, TimeUnit.SECONDS,
                new LinkedBlockingQueue(8),    //任务队列
                Executors.defaultThreadFactory(),       //创建线程 factory
                new ThreadPoolExecutor.AbortPolicy()    //拒绝策略
        );
        threadPoolExecutor.shutdown();
        threadPoolExecutor.awaitTermination(1,TimeUnit.SECONDS);
        System.exit(0);
    }
}
