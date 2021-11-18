package com.jason.study.thread.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author JasonC5
 */
public class CompletableFutureTest2 {

    public static void main(String[] args) throws Exception {
        CompletableFuture<Void> completableFuture = CompletableFuture
                .supplyAsync(CompletableFutureTest2::getAll)
                .thenApplyAsync(list -> list.stream().map(CompletableFutureTest2::getUserInfo).collect(Collectors.toList()))
                .thenAccept(infos -> infos.stream().forEach(System.out::println));
        doSomething("干别的事情1");
        doSomething("干别的事情2");
        doSomething("干别的事情3");
        completableFuture.get();
//        CompletableFuture<List<String>> completableFuture2 = completableFuture1.thenApplyAsync(list -> list.stream().map(CompletableFutureTest2::getUserInfo).collect(Collectors.toList()));
//        completableFuture1.thenRun(()->doSomething("做点好事1")).thenRunAsync(()->doSomething("做点好事2")).thenRun(()-> {
//            try {
//                completableFuture2.get().stream().forEach(System.out::println);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        });
//        completableFuture1.get();
//        completableFuture2.get();
    }

    public static void doSomething(String something) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(something);
    }

    public static String getUserInfo(Integer id) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "用户信息"+id;
    }


    public static ArrayList<Integer> getAll() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<Integer>(){
            {
                this.add(1);
                this.add(2);
                this.add(3);
                this.add(4);
                this.add(5);
            }
        };
    }
}
