package com.jason.study.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.SynchronousQueue;

public class SynchronusQueueTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        SynchronousQueue synchronousQueue = new SynchronousQueue();
        new Thread(()->{
            try {
                Object take = synchronousQueue.take();
                System.out.println("synchronousQueue take "+take.toString());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

//        System.in.read();
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String in = bufferedReader.readLine();
        synchronousQueue.put(in);
    }
}
