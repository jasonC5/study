package com.jason.study.algorithm.dynamicProgramming;

/**
 * 背包问题
 *
 * @author JasonC5
 */
public class DP3_Knapsack {

    //暴力递归

    private static int maxValue1(int[] wight, int[] value, int bag) {
        if (wight == null || value == null || wight.length == 0 || wight.length != value.length) {
            return 0;
        }
        //从左往右
        return process(wight, value, 0, bag);
    }

    /**
     * @param wight 占用
     * @param value 价值
     * @param index 当前位置
     * @param space 剩余空间
     * @return
     */
    private static int process(int[] wight, int[] value, int index, int space) {
        int length = wight.length;
        if (index == length) {
            return 0;
        }
        if (space < 0) {
            return 0;
        }
        int ans1 = process(wight, value, index + 1, space);
        int ans2 = 0;
        if (space >= wight[index]) {
            ans2 = value[index] + process(wight, value, index + 1, space - wight[index]);
        }
        return ans1 > ans2 ? ans1 : ans2;
    }
    //dp

    private static int maxValue2(int[] wight, int[] value, int bag) {
        if (wight == null || value == null || wight.length == 0 || wight.length != value.length) {
            return 0;
        }
        int length = wight.length;
        int[][] dp = new int[length + 1][bag + 1];
        //最下面一行是0，不用初始化
        for (int i = length - 1; i >= 0; i--) {
            for (int j = 0; j <= bag; j++) {
                int val1 = dp[i + 1][j];
                int val2 = j - wight[i] < 0 ? 0 : value[i] + dp[i + 1][j - wight[i]];
                dp[i][j] = Math.max(val1, val2);
            }
        }
        return dp[0][bag];
    }


    public static void main(String[] args) {
//        int[] wight = {2, 2, 3, 3, 5};
//        int[] value = {1, 2, 5, 8, 10};
//        int bag = 10;
        int[] wight = { 3, 2, 4, 7, 3, 1, 7 };
        int[] value = { 5, 6, 3, 19, 12, 4, 2 };
        int bag = 15;
        int maxValue = maxValue1(wight, value, bag);
        System.out.println(maxValue);
        int maxValue2 = maxValue2(wight, value, bag);
        System.out.println(maxValue2);
    }


}
