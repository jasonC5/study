package com.jason.study.juc.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author JasonC5
 */
public class ReentrantReadWriteLockTest1 {

    public static int a = 0;

    public static void read() {
        System.out.println(a);
    }

    public static void write() {
        a++;
    }

    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();


    }

}
