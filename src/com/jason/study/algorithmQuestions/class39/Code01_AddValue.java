package com.jason.study.algorithmQuestions.class39;

/**
 * // 来自腾讯
 * // 给定一个只由0和1组成的字符串S，假设下标从1开始，规定i位置的字符价值V[i]计算方式如下 :
 * // 1) i == 1时，V[i] = 1
 * // 2) i > 1时，如果S[i] != S[i-1]，V[i] = 1
 * // 3) i > 1时，如果S[i] == S[i-1]，V[i] = V[i-1] + 1
 * // 你可以随意删除S中的字符，返回整个S的最大价值
 * // 字符串长度<=5000
 * <p>
 * --从左往右尝试模型【保留、不保留】
 * --暴力：lastNum、baseValue
 *
 * @author JasonC5
 */
public class Code01_AddValue {

    public static int max1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] nums = s.toCharArray();
        int[] arr = new int[nums.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = nums[i] == '0' ? 0 : 1;
        }
        return process(arr, 0, 0, 0);
    }

    private static int process(int[] arr, int index, int preNum, int preCount) {
        if (index == arr.length) {
            return 0;
        }
        //两种可能，要或者不要
        int current = arr[index] == preNum ? (preCount + 1) : 1;
        // 不要
        int p1 = process(arr, index + 1, preNum, preCount);
        // 要
        int p2 = current + process(arr, index, arr[index], current);
        return Math.max(p1, p2);
    }


    public static int dp(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int length = s.length();
        char[] nums = s.toCharArray();
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = nums[i] == '0' ? 0 : 1;
        }
        int[][] dp = new int[length][length + 1];
//        int[][] dp = new int[length][length + 1];
//        for (int i = length - 1; i >= 0; i++) {
//            for (int j = length - 1; j >= 0; j++) {
//                int current = arr[index] == preNum ? (preCount + 1) : 1;
//
//            }
//        }
        return dp[0][0];
    }


}
