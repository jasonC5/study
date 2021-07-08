package com.jason.study.algorithm.dynamicProgramming;

import javax.print.attribute.standard.PrintQuality;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 给定一个数组arr，arr[i]代表第i号咖啡机泡一杯咖啡的时间
 * 给定一个正数N，表示N个人等着咖啡机泡咖啡，每台咖啡机只能轮流泡咖啡
 * 只有一台咖啡机，一次只能洗一个杯子，时间耗费a，洗完才能洗下一杯
 * 每个咖啡杯也可以自己挥发干净，时间耗费b，咖啡杯可以并行挥发
 * 假设所有人拿到咖啡之后立刻喝干净，
 * 返回从开始等到所有咖啡机变干净的最短时间
 *
 * @author JasonC5
 */
public class DP9_CleanCooffieCup {

    // for test
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
        int len = 5;
        int max = 10;
        int testTime = 100;
        //咖啡机数组
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            //有多少个人
            int n = (int) (Math.random() * 7) + 1;
            //洗杯机时间
            int a = (int) (Math.random() * 7) + 1;
            //风干时间
            int b = (int) (Math.random() * 10) + 1;
            int min1, right1;
            if ((min1 = minTime(arr, n, a, b)) != (right1 = right(arr, n, a, b))) {
                System.out.print("arr:");
                for (int i1 : arr) {
                    System.out.print(i1 + " ");
                }
                System.out.println();
                System.out.println("n:" + n + " a:" + a + " b:" + b);
                System.out.println("min1:" + min1 + " right1:" + right1);
            }
            ;
        }
    }

//    public static void main(String[] args) {
//        int[] arr = {4, 5, 4};
//        int i = minTime(arr, 7, 5, 3);
//        System.out.println(i);
//    }

    public static class Mark {
        //开始打咖啡时间
        int start;
        //花费时间
        int cost;

        public Mark(int start, int cost) {
            this.start = start;
            this.cost = cost;
        }
    }

    private static int minTime(int[] arr, int n, int washTime, int dryTime) {
        //1.求出所有人喝完/打完咖啡的时间
        PriorityQueue<Mark> minHeap = new PriorityQueue<Mark>((m1, m2) -> m1.start + m1.cost - m2.start - m2.cost);
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            minHeap.offer(new Mark(0, arr[i]));
        }
        int[] finishTimes = new int[n];
        for (int i = 0; i < n; i++) {
            Mark poll = minHeap.poll();
            poll.start += poll.cost;
            finishTimes[i] = poll.start;
            minHeap.offer(poll);
        }
        //得到了所有人喝完的时间点列表finishTimes
//        int ans = cleanCup(finishTimes, washTime, dryTime);
        int ans = cleanCupDB(finishTimes, washTime, dryTime);
        return ans;
    }

    private static int cleanCupDB(int[] startTimes, int washTime, int dryTime) {
        int length = startTimes.length;
        //index 从 0 到 length
        //先算出 machineFree 的最大值
        int maxMachineFree = 0;
        for (int i = 0; i < length; i++) {
            int start = Math.max(startTimes[i], maxMachineFree);
            maxMachineFree = Math.max(start + washTime, start + dryTime);
        }
        int[][] dp = new int[length + 1][maxMachineFree + 1];
        //杯子都洗完了，自然是0
        //if (index == length) {
        //     return 0;
        //}
        for (int index = length - 1; index >= 0; index--) {
            for (int machineFree = 0; machineFree <= maxMachineFree; machineFree++) {
                int startTime = startTimes[index];
                int washFinished = Math.max(startTime, machineFree) + washTime;
                if (washFinished > maxMachineFree) {
                    continue;
                }
                int nextFinished1 = dp[index + 1][washFinished];
                int finish1 = Math.max(washFinished, nextFinished1);

                int dryFinished = startTime + dryTime;
                int nextFinished2 = dp[index + 1][machineFree];
                int finish2 = Math.max(dryFinished, nextFinished2);

                dp[index][machineFree] = Math.min(finish1, finish2);
            }
        }
        return dp[0][0];
    }

    private static int cleanCup(int[] finishTimes, int washTime, int dryTime) {
        return process(finishTimes, washTime, dryTime, 0, 0);//
    }

    private static int process(int[] finishTimes, int washTime, int dryTime, int index, int machineFree) {
        int length = finishTimes.length;
        if (index == length) {
            return 0;
        }
        int startTime = finishTimes[index];
        int washFinished = Math.max(startTime, machineFree) + washTime;
        int nextFinished1 = process(finishTimes, washTime, dryTime, index + 1, washFinished);
        int finish1 = Math.max(washFinished, nextFinished1);

        int dryFinished = startTime + dryTime;
        int nextFinished2 = process(finishTimes, washTime, dryTime, index + 1, machineFree);
        int finish2 = Math.max(dryFinished, nextFinished2);

        return Math.min(finish1, finish2);
    }


    //对数器：暴力
    public static int right(int[] arr, int n, int a, int b) {
        int[] times = new int[arr.length];
        int[] drink = new int[n];
        return forceMake(arr, times, 0, drink, n, a, b);
    }

    // 每个人暴力尝试用每一个咖啡机给自己做咖啡
    public static int forceMake(int[] arr, int[] times, int kth, int[] drink, int n, int a, int b) {
        if (kth == n) {
            int[] drinkSorted = Arrays.copyOf(drink, kth);
            Arrays.sort(drinkSorted);
            return forceWash(drinkSorted, a, b, 0, 0, 0);
        }
        int time = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int work = arr[i];
            int pre = times[i];
            drink[kth] = pre + work;
            times[i] = pre + work;
            time = Math.min(time, forceMake(arr, times, kth + 1, drink, n, a, b));
            drink[kth] = 0;
            times[i] = pre;
        }
        return time;
    }


    public static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
        if (index == drinks.length) {
            return time;
        }
        // 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;
        int ans1 = forceWash(drinks, a, b, index + 1, wash, Math.max(wash, time));

        // 选择二：当前index号咖啡杯，选择自然挥发
        int dry = drinks[index] + b;
        int ans2 = forceWash(drinks, a, b, index + 1, washLine, Math.max(dry, time));
        return Math.min(ans1, ans2);
    }
}
