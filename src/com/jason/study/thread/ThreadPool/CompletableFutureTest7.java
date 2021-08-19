package com.jason.study.thread.ThreadPool;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest7 {

    public static void main(String[] args) {
        for (int i = 0; i < 50 ; i++) {
            CompletableFuture.supplyAsync(() -> 1).thenApply(x -> x + 1).thenApply(x -> x + 1).thenAccept(System.out::println);
            CompletableFuture.supplyAsync(() -> 1).thenApplyAsync(x -> x + 1).thenApplyAsync(x -> x + 1).thenAcceptAsync(System.out::println);
        }
    }
}
