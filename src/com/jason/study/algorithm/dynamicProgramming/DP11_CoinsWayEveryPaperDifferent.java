package com.jason.study.algorithm.dynamicProgramming;

/**
 * arr是货币数组，其中的值都是正数。再给定一个正数aim。
 * 每个值都认为是一张货币，
 * 即便是值相同的货币也认为每一张都是不同的，
 * 返回组成aim的方法数
 * 例如：arr = {1,1,1}，aim = 2
 * 第0个和第1个能组成2，第1个和第2个能组成2，第0个和第2个能组成2
 * 一共就3种方法，所以返回3
 *
 * @author JasonC5
 */
public class DP11_CoinsWayEveryPaperDifferent {

    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int times = 100;

        for (int i = 0; i < times; i++) {
            int maxLen = 10;
            int maxValue = 10;
            int targetMax = 20;
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * targetMax);
            int ans1 = coinWays1(arr, aim);
            int ans2 = coinWays2(arr, aim);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                return;
            }
        }

        System.out.println("complete!!");
    }

    private static int coinWays2(int[] arr, int aim) {
        if (aim < 0 || arr == null || arr.length == 0) {
            return -1;
        }
        int length = arr.length;
        int[][] dp = new int[length + 1][aim + 1];
        dp[length][0] = 1;
        for (int idx = length - 1; idx >= 0; idx--) {
            for (int surplus = 0; surplus <= aim; surplus++) {
                dp[idx][surplus] = dp[idx + 1][surplus];
                if (surplus - arr[idx] >= 0) {
                    dp[idx][surplus] += dp[idx + 1][surplus - arr[idx]];
                }
            }
        }
        return dp[0][aim];
    }

    private static int coinWays1(int[] arr, int aim) {
        if (aim < 0 || arr == null || arr.length == 0) {
            return -1;
        }
        return process(arr, 0, aim);
    }

    private static int process(int[] arr, int idx, int surplus) {
        if (surplus < 0) {
            return 0;
        }
        if (idx == arr.length) {
            return surplus == 0 ? 1 : 0;
        }
        return process(arr, idx + 1, surplus) + process(arr, idx + 1, surplus - arr[idx]);
    }

}
