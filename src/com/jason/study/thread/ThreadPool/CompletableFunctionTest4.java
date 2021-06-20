package com.jason.study.thread.ThreadPool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author JasonC5
 */
public class CompletableFunctionTest4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> action("动作1"));
        //如果上面已经执行完了，线面会直接执行run，否则才会加到栈上，
        completableFuture.thenRun(()-> System.out.println(1));
        completableFuture.thenRun(()-> System.out.println(2));
        completableFuture.thenRun(()-> System.out.println(3));
        completableFuture.thenRun(()-> System.out.println(4));
        completableFuture.get();
    }

    private static void action(String s) {
        System.out.println(s+" start");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(s+" end");
    }
}
