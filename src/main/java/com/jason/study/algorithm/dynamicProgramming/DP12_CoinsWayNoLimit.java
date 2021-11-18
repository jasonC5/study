package com.jason.study.algorithm.dynamicProgramming;

/**
 * arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
 * 每个值都认为是一种面值，且认为张数是无限的。
 * 返回组成aim的方法数
 * 例如：arr = {1,2}，aim = 4
 * 方法如下：1+1+1+1、1+1+2、2+2
 * 一共就3种方法，所以返回3
 *
 * @author JasonC5
 */
public class DP12_CoinsWayNoLimit {

    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        boolean[] has = new boolean[maxValue + 1];
        for (int i = 0; i < N; i++) {
            do {
                arr[i] = (int) (Math.random() * maxValue) + 1;
            } while (has[arr[i]]);
            has[arr[i]] = true;
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int times = 1000;
        for (int i = 0; i < times; i++) {
            int maxLen = 10;
            int maxValue = 30;

            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinsWay(arr, aim);
//            int ans2 = coinsWay2(arr, aim);
            int ans2 = coinsWay3(arr, aim);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                return;
            }
        }
        System.out.println("complete!!!");
    }

    private static int coinsWay3(int[] arr, int aim) {
        if (aim == 0) {
            return 1;
        }
        int length = arr.length;
        int[][] dp = new int[length + 1][aim + 1];
        dp[length][0] = 1;
        for (int index = length - 1; index >= 0; index--) {
            for (int sur = 0; sur <= aim; sur++) {
                //状态转移，根据画图得知
                dp[index][sur] = dp[index + 1][sur] + ((sur >= arr[index]) ? dp[index][sur - arr[index]] : 0);
            }
        }
        return dp[0][aim];
    }

    private static int coinsWay2(int[] arr, int aim) {
        if (aim == 0) {
            return 1;
        }
        int length = arr.length;
        int[][] dp = new int[length + 1][aim + 1];
        dp[length][0] = 1;
        for (int index = length - 1; index >= 0; index--) {
            for (int sur = 0; sur <= aim; sur++) {
                int ans = 0;
                int sum;
                for (int i = 0; (sum = (arr[index] * i)) <= sur; i++) {
                    ans += dp[index + 1][sur - sum];
                }
                dp[index][sur] = ans;
            }
        }

        return dp[0][aim];
    }

    private static int coinsWay(int[] arr, int aim) {
        if (aim == 0) {
            return 1;
        }
        return process(arr, 0, aim);
    }

    private static int process(int[] arr, int index, int sur) {
        if (sur < 0) {
            return 0;
        }
        if (index == arr.length) {
            return sur == 0 ? 1 : 0;
        }
        int ans = 0;
        int sum;
        for (int i = 0; (sum = (arr[index] * i)) <= sur; i++) {
            ans += process(arr, index + 1, sur - sum);
        }
        return ans;
    }
}
