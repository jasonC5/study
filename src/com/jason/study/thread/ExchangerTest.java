package com.jason.study.thread;

import java.util.concurrent.Exchanger;

public class ExchangerTest {
    public static void main(String[] args) {
        Exchanger exchanger = new Exchanger();

        new Thread(()->{
            try {
                Object exchange = exchanger.exchange(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName()+" get exchange Data"+ exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"T1").start();

        new Thread(()->{
            try {
                Object exchange = exchanger.exchange(Thread.currentThread().getName()+"##lock Data");
                System.out.println(Thread.currentThread().getName()+" get exchange Data"+ exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"T2").start();


    }
}
