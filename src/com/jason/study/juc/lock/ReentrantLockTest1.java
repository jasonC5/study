package com.jason.study.juc.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author JasonC5
 */
public class ReentrantLockTest1 {
    public static void main(String[] args) {
        ReentrantLock rl = new ReentrantLock();
        try {
            rl.lock();

        } finally {
            rl.unlock();
        }
    }
}
