package com.jason.study.thread.ThreadPool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFunctionTest5 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> cf1 = CompletableFuture.runAsync(() -> {
            System.out.println("cf1 start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf1 end");
        });
        CompletableFuture<Void> cf2 = CompletableFuture.runAsync(() -> {
            System.out.println("cf2 start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf2 end");
        });
//        cf1.runAfterBoth(cf2,()-> System.out.println("both end")).get();
//        cf1.runAfterEither(cf2,()-> System.out.println("either end")).get();
        cf1.runAfterEither(cf2,()-> System.out.println("either end")).get();
        Thread.sleep(2000);

        //
    }
}
