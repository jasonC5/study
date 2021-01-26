package com.jason.study.thread;

import java.util.concurrent.*;

public class Executor_Callable_Future {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(() -> {
            int sum = 0;
            for (int i = 0; i < 10000; i++) {
                sum += i;
            }
            return sum;
        });
        int sum = 0;
        for (int i = 0; i < 10000; i++) {
            sum += i;
        }
        System.out.println(future.get());
        System.out.println(sum);
    }
}
