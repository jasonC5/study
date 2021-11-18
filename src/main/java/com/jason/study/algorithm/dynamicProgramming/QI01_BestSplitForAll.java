package com.jason.study.algorithm.dynamicProgramming;

/**
 * quadrangle inequality
 * 四边形不等式
 * <p>
 * 给定一个非负数组arr，长度为N，
 * 那么有N-1种方案可以把arr切成左右两部分
 * 每一种方案都有，min{左部分累加和，右部分累加和}
 * 求这么多方案中，min{左部分累加和，右部分累加和}的最大值是多少？
 * 整个过程要求时间复杂度O(N)
 * --分开位置往后推，不回退
 */
public class QI01_BestSplitForAll {

    public static int[] randomArray(int len, int max) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        return ans;
    }

    public static void main(String[] args) {
        int N = 20;
        int max = 30;
        int testTime = 1000000;
        System.out.println("test start");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, max);
            int ans1 = bestSplit3(arr);
            int ans2 = bestSplit2(arr);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
    }

    //从左往右
    private static int bestSplit2(int[] arr) {
        int ans = 0;
        int[] sumArr = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            sumArr[i + 1] = sumArr[i] + arr[i];
        }
        for (int i = 0; i < arr.length; i++) {
            ans = Math.max(ans, Math.min(sumArr[i], sumArr[arr.length] - sumArr[i]));
        }
        return ans;
    }

    private static int bestSplit3(int[] arr) {
        int ans = 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        int leftSum = 0;
        for (int i = 0; i < arr.length; i++) {
            leftSum += arr[i];
            ans = Math.max(ans, Math.min(leftSum, sum - leftSum));
        }
        return ans;
    }

    public static int bestSplit1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int ans = 0;
        for (int s = 0; s < N - 1; s++) {
            int sumL = 0;
            for (int L = 0; L <= s; L++) {
                sumL += arr[L];
            }
            int sumR = 0;
            for (int R = s + 1; R < N; R++) {
                sumR += arr[R];
            }
            ans = Math.max(ans, Math.min(sumL, sumR));
        }
        return ans;
    }

}
