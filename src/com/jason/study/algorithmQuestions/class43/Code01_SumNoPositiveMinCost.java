package com.jason.study.algorithmQuestions.class43;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * // 来自微软面试
 * // 给定一个正数数组arr，长度为n，正数x和y
 * // 你的目标是让arr整体的累加和<=0
 * // 你可以对数组中的数num执行以下两种操作中的一种，且每个数最多能执行一次操作 :
 * // 1）可以选择让num变成0，承担x的代价
 * // 2）可以选择让num变成-num，承担y的代价
 * // 返回你达到目标的最小代价
 * // 数据规模 : 面试时面试官没有说数据规模
 *
 * @author JasonC5
 */
public class Code01_SumNoPositiveMinCost {

    // 贪心（最优解）


    // 为了测试
    public static int[] randomArray(int len, int v) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * v);
        }
        return arr;
    }

    // 为了测试
    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    // 为了测试
    public static void main(String[] args) {
        int n = 12;
        int v = 20;
        int c = 10;
        int testTime = 10000;
        System.out.println("start");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * n);
            int[] arr = randomArray(len, v);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int x = (int) (Math.random() * c);
            int y = (int) (Math.random() * c);
            int ans1 = minOpStep3(arr1, x, y);
            int ans2 = minOpStep2(arr2, x, y);
            if (ans1 != ans2) {
                System.out.println("error!");
                System.out.println("arr:" + Arrays.stream(arr).boxed().map(s -> s + "").collect(Collectors.joining(",")));
                System.out.println("x=" + x + ",y=" + y);
            }
        }
        System.out.println("finished");
    }

    private static int minOpStep2(int[] arr, int x, int y) {
        int length = arr.length;
        if (arr.length == 0) {
            return 0;
        }
        //从大到小排序
        Arrays.sort(arr);
        for (int l = 0, r = length - 1; l <= r; l++, r--) {
            int tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
        }
        //后缀和
        int[] postSum = new int[length];
        postSum[length - 1] = arr[length - 1];
        for (int i = length - 2; i >= 0; i--) {
            postSum[i] = arr[i] + postSum[i + 1];
        }
//            0~leftPoint 执行y操作，leftPoint+1~rightPoint-1执行x操作，rightPoint~length-1不执行任何操作
        int rightPoint = length - 1;
        int leftPoint = -1;
        int preSum = 0;
        int ans = x * length;
        while (leftPoint <= rightPoint) {
            if (leftPoint == rightPoint) {
                //收集答案
                int cost = Math.min(ans, y * (leftPoint + 1));
                ans = Math.min(ans, cost);
                break;
            }
            if (preSum >= postSum[rightPoint]) {
                rightPoint--;
            } else {
                //收集答案
                int cost = Math.min(ans, y * (leftPoint + 1) + x * (rightPoint - leftPoint));
                ans = Math.min(ans, cost);
                preSum += arr[++leftPoint];
            }
        }
        return ans;
    }


    // 动态规划
    public static int minOpStep1(int[] arr, int x, int y) {
        int sum = Arrays.stream(arr).sum();
        return process1(arr, x, y, 0, sum);
    }

    private static int process1(int[] arr, int x, int y, int index, int rest) {
        if (rest <= 0) {
            return 0;
        }
        if (index == arr.length) {
            return Integer.MAX_VALUE;
        }
        //这个位置不变
        int p1 = process1(arr, x, y, index + 1, rest);
        //这个位置执行x操作
        int p2 = Integer.MAX_VALUE;
        int next = process1(arr, x, y, index + 1, rest - arr[index]);
        if (next != Integer.MAX_VALUE) {
            p2 = next + x;
        }
        //这个位置执行y操作
        int p3 = Integer.MAX_VALUE;
        next = process1(arr, x, y, index + 1, rest - 2 * arr[index]);
        if (next != Integer.MAX_VALUE) {
            p3 = next + y;
        }
        return Math.min(p1, Math.min(p2, p3));
    }

    public static int minOpStep3(int[] arr, int x, int y) {
        int sum = Arrays.stream(arr).sum();
        int length = arr.length;
        if (arr.length == 0) {
            return 0;
        }
        int[][] dp = new int[length + 1][sum + 1];
        for (int rest = 1; rest <= sum; rest++) {
            dp[length][rest] = Integer.MAX_VALUE;
        }
        for (int index = length - 1; index >= 0; index--) {
            for (int rest = 0; rest <= sum; rest++) {
                //这个位置不变
                int p1 = dp[index + 1][rest];
                //这个位置执行x操作
                int p2 = Integer.MAX_VALUE;
                int next = (rest - arr[index]) <= 0 ? 0 : dp[index + 1][rest - arr[index]];
                if (next != Integer.MAX_VALUE) {
                    p2 = next + x;
                }
                //这个位置执行y操作
                int p3 = Integer.MAX_VALUE;
                next = (rest - 2 * arr[index]) <= 0 ? 0 : dp[index + 1][rest - 2 * arr[index]];
                if (next != Integer.MAX_VALUE) {
                    p3 = next + y;
                }
                dp[index][rest] = Math.min(p1, Math.min(p2, p3));
            }
        }
        return dp[0][sum];
    }


}
