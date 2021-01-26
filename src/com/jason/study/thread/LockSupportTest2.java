package com.jason.study.thread;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest2 {

    static Thread t1 = null;
    static Thread t2 = null;
    public static void main(String[] args) {
        char[] a = "ABCDEFGHI".toCharArray();
        char[] b = "123456789".toCharArray();

        t1 = new Thread(()->{
            for (char c: a){
                System.out.print(c);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
            LockSupport.unpark(t2);
        });

        t2 = new Thread(()->{
            for (char c: b){
                LockSupport.park();
                System.out.print(c);
                LockSupport.unpark(t1);
            }
            LockSupport.unpark(t1);
        });

        t1.start();
        t2.start();

    }
}
