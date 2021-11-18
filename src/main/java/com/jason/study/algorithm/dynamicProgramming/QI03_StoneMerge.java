package com.jason.study.algorithm.dynamicProgramming;

/**
 * 摆放着n堆石子。现要将石子有次序地合并成一堆
 * 规定每次只能选相邻的2堆石子合并成新的一堆，
 * 并将新的一堆石子数记为该次合并的得分
 * 求出将n堆石子合并成一堆的最小得分（或最大得分）合并方案
 * --和切金条问题区别：相邻
 * --dp 范围模型
 * --四边形不等式
 *
 * @author JasonC5
 */
public class QI03_StoneMerge {

    public static int[] randomArray(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }


    public static void main(String[] args) {
        int N = 15;
        int maxValue = 100;
        int testTime = 1000;
        System.out.println("test start");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, maxValue);
            int ans1 = min1(arr);
            int ans2 = min2(arr);
            int ans3 = min3(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
    }


    public static int w(int[] s, int l, int r) {
        return s[r + 1] - s[l];
    }
    //四边形不等式
    private static int min3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int length = arr.length;
        int[] sumArr = sum(arr);
        int[][] dp = new int[length][length];
        int[][] best = new int[length][length];
        for (int i = 0; i < length - 1; i++) {
            best[i][i + 1] = i;
            dp[i][i + 1] = sumArr[i + 2] - sumArr[i];
//            dp[i][i + 1] = w(sumArr, i, i + 1);
        }
//        for (int left = length - 3; left >= 0; left--) {
//            for (int right = left + 2; right < length; right++) {
        for (int right = 2; right < length; right++) {
            for (int left = right - 2; left >= 0; left--) {
                int subCost = Integer.MAX_VALUE;
                int choose = -1;
                for (int i = best[left][right - 1]; i <= best[left + 1][right]; i++) {
                    int curCost = dp[left][i] + dp[i + 1][right];
                    if (curCost <= subCost) {
                        subCost = curCost;
                        choose = i;
                    }
                }
                best[left][right] = choose;
                dp[left][right] = subCost + sumArr[right + 1] - sumArr[left];
//                dp[left][right] = subCost + w(sumArr, left, right);
            }
        }
        return dp[0][length - 1];
    }


    private static int min2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int length = arr.length;
        int[] sumArr = sum(arr);
        int[][] dp = new int[length][length];
        for (int right = 1; right < length; right++) {
            for (int left = right - 1; left >= 0; left--) {
                int subConst = Integer.MAX_VALUE;
                for (int i = left; i < right; i++) {
                    subConst = Math.min(subConst, dp[left][i] + dp[i + 1][right]);
                }
                dp[left][right] = subConst + sumArr[right + 1] - sumArr[left];
            }
        }
        return dp[0][length - 1];
    }


    private static int[] sum(int[] arr) {
        int[] sum = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }
        return sum;
    }

    /**
     * 暴力解
     *
     * @param arr
     * @return
     */
    private static int min1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int length = arr.length;
        int[] sumArr = sum(arr);
        return process(sumArr, 0, length - 1);
    }

    private static int process(int[] sumArr, int left, int right) {
        if (left == right) {
            return 0;
        }
        int subConst = Integer.MAX_VALUE;
        for (int i = left; i < right; i++) {
            subConst = Math.min(subConst, process(sumArr, left, i) + process(sumArr, i + 1, right));
        }
        return subConst + sumArr[right + 1] - sumArr[left];
    }


}
