package com.jason.study.thread.ThreadPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author JasonC5
 */
public class CompletableFutureTest3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
//        CompletableFuture<Void> completableFuture4 =
//                CompletableFuture.supplyAsync(CompletableFutureTest3::supplier)
//                .thenApply(CompletableFutureTest3::function)
//                .thenAccept(CompletableFutureTest3::consumer);
//        CompletableFuture<Void> completableFuture2 =
//                CompletableFuture.supplyAsync(CompletableFutureTest3::supplier)
//                .thenApply(CompletableFutureTest3::function)
//                .thenAccept(CompletableFutureTest3::consumer);
//        CompletableFuture<Void> completableFuture1 =
//                CompletableFuture.supplyAsync(CompletableFutureTest3::supplier)
//                .thenApply(CompletableFutureTest3::function)
//                .thenAccept(CompletableFutureTest3::consumer);
//        CompletableFuture<Void> completableFuture3 =
//                CompletableFuture.supplyAsync(CompletableFutureTest3::supplier)
//                .thenApply(CompletableFutureTest3::function)
//                .thenAccept(CompletableFutureTest3::consumer);
        //4060

//        CompletableFuture<Void> completableFuture1 = CompletableFuture.supplyAsync(CompletableFutureTest3::supplier)
//                .thenApplyAsync(CompletableFutureTest3::function)
//                .thenAcceptAsync(CompletableFutureTest3::consumer);
//        CompletableFuture<Void> completableFuture2 = CompletableFuture.supplyAsync(CompletableFutureTest3::supplier)
//                .thenApplyAsync(CompletableFutureTest3::function)
//                .thenAcceptAsync(CompletableFutureTest3::consumer);
//        CompletableFuture<Void> completableFuture3 = CompletableFuture.supplyAsync(CompletableFutureTest3::supplier)
//                .thenApplyAsync(CompletableFutureTest3::function)
//                .thenAcceptAsync(CompletableFutureTest3::consumer);
//        CompletableFuture<Void> completableFuture4 = CompletableFuture.supplyAsync(CompletableFutureTest3::supplier)
//                .thenApplyAsync(CompletableFutureTest3::function)
//                .thenAcceptAsync(CompletableFutureTest3::consumer);
        //1049

//        completableFuture1.get();
//        completableFuture2.get();
//        completableFuture3.get();
//        completableFuture4.get();

//        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(CompletableFutureTest3::supplierList)
//                .thenApplyAsync(numList -> numList.stream().map((num) -> function(num)).collect(Collectors.toList()))
//                .thenAccept(strings -> strings.stream.)


//        System.out.println(System.currentTimeMillis() - start);

        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> function(1));
        CompletableFuture<String> stringCompletableFuture2 = CompletableFuture.supplyAsync(() -> function(2));
        CompletableFuture<String> stringCompletableFuture3 = CompletableFuture.supplyAsync(() -> function(3));

        String ans = stringCompletableFuture.get();
        String ans2 = stringCompletableFuture2.get();
        String ans3 = stringCompletableFuture3.get();

        System.out.println(System.currentTimeMillis() - start);

    }

    public static void consumer(String s) {
        System.out.println(s);
    }

    public static Integer supplier() {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }

    public static List<Integer> supplierList() {
//        List<Integer> integerList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            int num = (int) ((Math.random() * 9 + 1) * 100000);
//            integerList.add(num);
//        }
        List<Integer> integerList2 = new ArrayList<Integer>() {
            {
                for (int i = 0; i < 10; i++) {
                    int num = (int) ((Math.random() * 9 + 1) * 100000);
                    this.add(num);
                }
            }
        };
        return integerList2;
    }

    public static String function(Integer integer) {
        waitASecend();
        return integer.toString();
    }

    private static void waitASecend() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
