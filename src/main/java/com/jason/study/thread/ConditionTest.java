package com.jason.study.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {

    public static void main(String[] args) {
        char[] a = "ABCDEFGHI".toCharArray();
        char[] b = "123456789".toCharArray();
        Lock lock = new ReentrantLock();
        Condition t1Condition = lock.newCondition();//等待队列
        Condition t2Condition = lock.newCondition();

        new Thread(() -> {
            try {
                lock.lock();
                for (char c : b) {
                    t2Condition.await();
                    System.out.print(c);
                    t1Condition.signal();
                }
                t1Condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();
        new Thread(() -> {
            try {
                lock.lock();
                for (char c : a) {
                    System.out.print(c);
                    t2Condition.signal();
                    t1Condition.await();
                }
                t2Condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();


    }
}
