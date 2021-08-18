package com.jason.study.algorithmQuestions.class04;

public class SubArrayMaxSumFollowUp {
    public static int subSqeMaxSumNoNext(int[] arr) {
        int length;
        if (arr == null || (length = arr.length) == 0) {
            return 0;
        } else if (length == 1) {
            return arr[0];
        }
        int[] dp = new int[length];
        dp[0] = arr[0];
        dp[1] = Math.max(arr[0], arr[1]);
        for (int i = 2; i < length; i++) {
            dp[i] = Math.max(dp[i - 1], Math.max(arr[i], arr[i] + dp[i - 2]));
        }
        return dp[length - 1];
    }
}
