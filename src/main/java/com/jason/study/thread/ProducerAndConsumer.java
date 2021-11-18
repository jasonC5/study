package com.jason.study.thread;


import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerAndConsumer {
    //notifyAll实现方式，瑕疵：notifyAll的时候可能会叫醒同类型（同消费者或者同生生产者）的线程，此时是无意义的，只有叫醒非同类型的线程才能工作继续
    //
    final private LinkedList<String> lists = new LinkedList();//非线程安全
    private int count = 0;
    final private int capacity = 20;

    private Lock lock = new ReentrantLock();
    private Condition producer = lock.newCondition();//同一把锁，不同的等待队列
    private Condition consumer = lock.newCondition();

    public void put(String item) {
        try {
            lock.lock();
            while (lists.size() == capacity) {
                producer.await();
            }

            lists.add(item);
            ++count;
            consumer.signalAll(); //通知消费者线程进行消费
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public String get() {
        String item = null;
        try {
            lock.lock();
            while (lists.size() == 0) {
                consumer.await();
            }
            item = lists.removeFirst();
            count--;
            producer.signalAll();
            return item;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return item;
    }

    public static void main(String[] args) {
        ProducerAndConsumer obj = new ProducerAndConsumer();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " consume " + obj.get());
            }, "consumer_" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int x = 0; x < 10; x++) {//每次生产5个
                    obj.put(Thread.currentThread().getName() + "@" + x);
                    System.out.println(Thread.currentThread().getName() + " produce " + x);
                }
            }, "producer_" + i).start();
        }
    }

}
