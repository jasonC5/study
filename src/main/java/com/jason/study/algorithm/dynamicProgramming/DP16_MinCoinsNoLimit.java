package com.jason.study.algorithm.dynamicProgramming;

/**
 * arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
 * 每个值都认为是一种面值，且认为张数是无限的。
 * 返回组成aim的最少货币数
 *
 * @author JasonC5
 */
public class DP16_MinCoinsNoLimit {

    // 为了测试
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

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        System.out.println("start");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("finished");
    }

    public static int minCoins(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    private static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }
        int ans = Integer.MAX_VALUE;
        for (int count = 0; rest >= count * arr[index]; count++) {
            //能不能组成？
            int next = process(arr, index + 1, rest - (count * arr[index]));
            if (next != Integer.MAX_VALUE) {
                ans = Math.min(ans, next + count);
            }
        }
        return ans;
    }


    private static int dp1(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ans = Integer.MAX_VALUE;
                for (int count = 0; rest >= count * arr[index]; count++) {
                    //能不能组成？
                    int next = dp[index + 1][rest - (count * arr[index])];
                    if (next != Integer.MAX_VALUE) {
                        ans = Math.min(ans, next + count);
                    }
                }
                dp[index][rest] = ans;
            }
        }
        return dp[0][aim];
    }


    private static int dp2(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
//                int ans = Integer.MAX_VALUE;
//                for (int count = 0; rest >= count * arr[index]; count++) {
//                    int next = dp[index + 1][rest - (count * arr[index])];
//                    if (next != Integer.MAX_VALUE) {
//                        ans = Math.min(ans, next + count);
//                    }
//                }
//                dp[index][rest] = ans;
                dp[index][rest] = dp[index + 1][rest];
                if (rest - arr[index] >= 0 && dp[index][rest - arr[index]] != Integer.MAX_VALUE) {
                    dp[index][rest] = Math.min(dp[index][rest - arr[index]] + 1, dp[index][rest]);
                }
            }
        }
        return dp[0][aim];
    }

}
