package com.jason.study.thread.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author JasonC5
 */
public class CompletableFutureTest {

//    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
////        FutureTask<Integer> futureTask = new FutureTask(()->{
////            System.out.println(1);
////            return true;
////        });
//        Future<?> future = executorService.submit(() -> {
//            System.out.println("111");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("end");
//            return "finished";
//        });
//        System.out.println(future.isDone());
////        Thread.sleep(2000);
////        if (!future.isDone()){
////            future.cancel(true);
////        }
//        Object o = null;
//        try {
//            o = future.get(3, TimeUnit.SECONDS);
//            System.out.println(o);
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }
//        executorService.shutdown();
//        executorService.awaitTermination(1,TimeUnit.MILLISECONDS);
//    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 1;
            }
        });
        executorService.execute(futureTask);
        System.out.println(futureTask.get());
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        CompletableFuture<Void> completableFuture =
                CompletableFuture
                        .supplyAsync(CompletableFutureTest::dbSearch)
                        .thenAccept(idList -> idList.stream().map(CompletableFutureTest::findUserName).forEach(System.out::println));
        //Function  Supplier Consumer
//        supplyAsync
//        CompletableFuture<Void> completableFuture = CompletableFuture.allOf();
//
//        completableFuture.thenAccept()
//
//        completableFuture.thenRun(()->);

//        completableFuture.thenRunAsync()

        Void unused = completableFuture.get();
        CompletableFuture.runAsync(()-> System.out.println(1));
//        lambda表达式三个函数式接口： supplier  function  consumer
    }

    public static String findUserName(Integer id) {
        return "user info " + id;
    }

    public static List<Integer> dbSearch() {
        return new ArrayList<Integer>() {
            {
                this.add(1);
                this.add(2);
                this.add(3);
            }
        };
    }
}
