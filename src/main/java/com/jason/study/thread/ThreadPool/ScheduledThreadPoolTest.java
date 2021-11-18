package com.jason.study.thread.ThreadPool;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolTest {

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        /*scheduledThreadPoolExecutor.schedule(()->{
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(1);
        },100, TimeUnit.MILLISECONDS);*/
        /*scheduledThreadPoolExecutor.scheduleAtFixedRate(()->{
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println(1);
            System.out.println(new Date());
        },100L,2000L,TimeUnit.MILLISECONDS);*/  //从开始任务时计时

        scheduledThreadPoolExecutor.scheduleWithFixedDelay(()->{
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println(1);
            System.out.println(new Date());
        },100L,2000L,TimeUnit.MILLISECONDS);//从任务完成之后开始下一次计时

//        scheduledThreadPoolExecutor.schedule()
//        scheduledThreadPoolExecutor.shutdown();
    }
}
