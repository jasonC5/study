package com.jason.study.thread;

import java.util.concurrent.locks.Lock;

public class A1B2C3 {
//    static String[] arr1 = {"A","B","C","D","E","F","G","H"};
//    static String[] arr2 = {"1","2","3","4","5","6","7","9"};

    public synchronized void printA() throws InterruptedException {
        char a = 'A';
        for (int i=0; i<26;i++) {
            System.out.print(a++);
            this.notify();
            this.wait();
        }
    }

    public synchronized void print1() throws InterruptedException {
        for (int i=0; i<26;i++) {
            System.out.print(i+1);
            this.notify();
            this.wait();
        }
    }

    public static void main(String[] args) {
        A1B2C3 a1B2C3 = new A1B2C3();
        new Thread(()->{
            try {
                a1B2C3.printA();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"T1").start();

        new Thread(()->{
            try {
                a1B2C3.print1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"T2").start();
    }
}
