package com.jason.study.algorithm.dynamicProgramming;

/**
 * 最长公共子序列长度
 * <p>
 * https://leetcode-cn.com/problems/longest-common-subsequence/
 *
 * @author JasonC5
 */
public class DP6_LongestCommonSubsequence {

    public static void main(String[] args) {
        String str1 = "123abccc1";
        String str2 = "456abc1223c";
        int max1 = longestCommonSubsequence1(str1, str2);
        System.out.println(max1);
        int max2 = longestCommonSubsequence2(str1, str2);
        System.out.println(max2);
    }

    public static int longestCommonSubsequence1(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();
        return process1(arr1, arr2, arr1.length - 1, arr2.length - 1);
    }

    //arr1 中 0~idx1 和 arr2 中 0~idx2 中 最大公共子序列长度
    private static int process1(char[] arr1, char[] arr2, int idx1, int idx2) {
        if (idx1 == 0 && idx2 == 0) {
            return arr1[0] == arr2[0] ? 1 : 0;
        } else if (idx1 == 0) {
            return arr1[idx1] == arr2[idx2] ? 1 : process1(arr1, arr2, idx1, idx2 - 1);
        } else if (idx2 == 0) {
            return arr1[idx1] == arr2[idx2] ? 1 : process1(arr1, arr2, idx1 - 1, idx2);
        } else {
            int ans1 = process1(arr1, arr2, idx1, idx2 - 1);
            int ans2 = process1(arr1, arr2, idx1 - 1, idx2);
            int ans3 = arr1[idx1] == arr2[idx2] ? (1 + process1(arr1, arr2, idx1 - 1, idx2 - 1)) : 0;
            return Math.max(ans1, Math.max(ans2, ans3));
        }
    }

    public static int longestCommonSubsequence2(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();
        int length1 = arr1.length;
        int length2 = arr2.length;
        int[][] dp = new int[length1][length2];
        //先推第0行，再推第0列
        dp[0][0] = arr1[0] == arr2[0] ? 1 : 0;
        for (int idx2 = 1; idx2 < length2; idx2++) {
            dp[0][idx2] = arr1[0] == arr2[idx2] ? 1 : dp[0][idx2 - 1];
        }
        for (int idx1 = 1; idx1 < length1; idx1++) {
            dp[idx1][0] = arr1[idx1] == arr2[0] ? 1 : dp[idx1 - 1][0];
        }
        //最后依次往下推
        for (int idx1 = 1; idx1 < length1; idx1++) {
            for (int idx2 = 1; idx2 < length2; idx2++) {
                int ans1 = dp[idx1][idx2 - 1];
                int ans2 = dp[idx1 - 1][idx2];
                int ans3 = arr1[idx1] == arr2[idx2] ? (1 + dp[idx1 - 1][idx2 - 1]) : 0;
                dp[idx1][idx2] = Math.max(ans1, Math.max(ans2, ans3));
            }
        }
        return dp[length1 - 1][length2 - 1];
    }

}
