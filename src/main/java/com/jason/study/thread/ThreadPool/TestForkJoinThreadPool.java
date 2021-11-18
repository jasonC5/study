package com.jason.study.thread.ThreadPool;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 测试joinFork线程池
 *
 * @author admin
 */
public class TestForkJoinThreadPool {

    private static int[] numArray = new int[100000000];

    private final static int MAX_VALUE = 50000;

    private static long sumNumArray = 0L;

    static {
        Random random = new Random();
        for (int i = 0; i < numArray.length; i++) {
            int i1 = random.nextInt(numArray.length);
            numArray[i] = i1;
        }
        long startTime = System.currentTimeMillis();
        for (int j : numArray) {
            sumNumArray += j;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("sumNumArrayNum：" + sumNumArray);
        System.out.println("总时长：" + (endTime - startTime));
        sumNumArray = 0L;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool forkJoinPool2 = new ForkJoinPool();
        SumArrayForkJoinTaskRet sumArrayForkJoinTaskRet = new SumArrayForkJoinTaskRet(0, 1000000);
        forkJoinPool2.execute(sumArrayForkJoinTaskRet);
        Long join = sumArrayForkJoinTaskRet.join();
        System.out.println(join);
        long endTime = System.currentTimeMillis();
        System.out.println("总时长：" + (endTime - startTime));
//        try {
//            System.in.read();
//            ForkJoinPool forkJoinPool = new ForkJoinPool();
//            forkJoinPool.execute(new SumArrayForkJoinTask(0, numArray.length));
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(sumNumArray);

    }

    /**
     * 不带返回值，能无限分裂小任务
     */
    static class SumArrayForkJoinTask extends RecursiveAction {

        private int start;

        private int end;

        public SumArrayForkJoinTask() {

        }

        public SumArrayForkJoinTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            System.out.println("从：" + start + ",开始到" + end + "结束");

            if (end - start <= MAX_VALUE) {
                for (int i = start; i < end; i++) {
//                        System.out.println("从：" + start + ",开始到" + end + "结束");
                    sumNumArray += numArray[i];
                }
            } else {
                int middle = start + (end - start) / 2;
                SumArrayForkJoinTask sumArrayForkJoinTask1 = new SumArrayForkJoinTask(start, middle);
                SumArrayForkJoinTask sumArrayForkJoinTask2 = new SumArrayForkJoinTask(middle, end);
                sumArrayForkJoinTask1.fork();
                sumArrayForkJoinTask2.fork();
            }
        }
    }

    /**
     * 带返回值，能无限分裂小任务
     */
    static class SumArrayForkJoinTaskRet extends RecursiveTask<Long> {

        private int start;

        private int end;

        public SumArrayForkJoinTaskRet() {

        }

        public SumArrayForkJoinTaskRet(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if ((end - start) <= MAX_VALUE) {
                System.out.println("from："+this.start+"\tend："+this.end);
                Long sum = 0L;
                for (int i = start; i < end; i++) {
                    sum += numArray[i];
                }
                return sum;
            } else {
                Integer middle = start + (end - start) / 2;
                SumArrayForkJoinTaskRet sumArrayForkJoinTask1 = new SumArrayForkJoinTaskRet(start, middle);
                SumArrayForkJoinTaskRet sumArrayForkJoinTask2 = new SumArrayForkJoinTaskRet(middle + 1, end);
                sumArrayForkJoinTask1.fork();
                sumArrayForkJoinTask2.fork();
                return sumArrayForkJoinTask1.join() + sumArrayForkJoinTask2.join();
            }
        }
    }
}
