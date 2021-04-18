package com.jason.study.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CountDownLatchTest2 {
    public static void main(String[] args) throws InterruptedException {
        int kernelCount = Runtime.getRuntime().availableProcessors();//机器核心数
        ThreadPoolExecutor initializerStorePool =
                new ThreadPoolExecutor(kernelCount, kernelCount * 2, 10, TimeUnit.SECONDS,
                        //工作队列
                        new ArrayBlockingQueue<>(1024),
                        //线程工厂
                        new ThreadFactory() {
                            @Override
                            public Thread newThread(Runnable r) {
                                Thread thread = new Thread(r, "DUOLAO_WD_INIT_THREAD_" + r.hashCode());//自定义线程名，方便排查
                                thread.setUncaughtExceptionHandler((Thread t, Throwable e)->{
                                    e.printStackTrace();
                                });
                                return thread;
                            }
                        },
                        //拒绝策略
                        new ThreadPoolExecutor.AbortPolicy()
                );
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        for (Integer num : list) {
            initializerStorePool.execute(()->{
                try{
                    Thread.sleep(1000);
                    System.out.println(num);
                    countDownLatch.countDown();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        initializerStorePool.shutdown();
        initializerStorePool.awaitTermination(2,TimeUnit.SECONDS);
        countDownLatch.await();
        System.out.println("完成");
    }
}
