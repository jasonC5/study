package com.jason.study.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            int sum = 0;
            for (int i = 0; i < 100; i++) {
                sum += i;
            }
            return sum;
        });
        new Thread(futureTask).start();
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += i;
        }
        System.out.println(sum);
        System.out.println(futureTask.get());
    }
}
