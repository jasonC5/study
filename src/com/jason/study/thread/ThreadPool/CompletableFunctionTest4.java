package com.jason.study.thread.ThreadPool;

import java.util.concurrent.*;

/**
 * @author JasonC5
 */
public class CompletableFunctionTest4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> action("动作1"), executorService);
        //如果上面已经执行完了，线面会直接执行run，否则才会加到栈上，
        CompletableFuture<Void> completableFuture1/*dep*/ = completableFuture/*src*/.thenRun(() -> System.out.println(1));
        CompletableFuture<Void> completableFuture2 = completableFuture.thenRun(() -> System.out.println(2));
        CompletableFuture<Void> completableFuture3 = completableFuture.thenRun(() -> System.out.println(3));
        CompletableFuture<Void> completableFuture4 = completableFuture.thenRun(() -> System.out.println(4));
        completableFuture4.thenRun(()-> System.out.println(4.1));
        CompletableFuture<Void> completableFuture5 = completableFuture.thenRun(() -> System.out.println(5));
        completableFuture.get();
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
    }
        //asyncRun
    private static void action(String s) {
        System.out.println(s + " start");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(s + " end");
    }
}
