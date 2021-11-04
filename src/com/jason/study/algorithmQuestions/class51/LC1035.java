package com.jason.study.algorithmQuestions.class51;

public class LC1035 {

    public static int longestCommonSubsequence2(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        char[] nums1 = str1.toCharArray();
        char[] nums2 = str2.toCharArray();
        int length1 = nums1.length;
        int length2 = nums2.length;
        int[][] dp = new int[length1][length2];
        //先推第0行，再推第0列
        dp[0][0] = nums1[0] == nums2[0] ? 1 : 0;
        for (int idx2 = 1; idx2 < length2; idx2++) {
            dp[0][idx2] = nums1[0] == nums2[idx2] ? 1 : dp[0][idx2 - 1];
        }
        for (int idx1 = 1; idx1 < length1; idx1++) {
            dp[idx1][0] = nums1[idx1] == nums2[0] ? 1 : dp[idx1 - 1][0];
        }
        //最后依次往下推
        for (int idx1 = 1; idx1 < length1; idx1++) {
            for (int idx2 = 1; idx2 < length2; idx2++) {
                int ans1 = dp[idx1][idx2 - 1];
                int ans2 = dp[idx1 - 1][idx2];
                int ans3 = nums1[idx1] == nums2[idx2] ? (1 + dp[idx1 - 1][idx2 - 1]) : 0;
                dp[idx1][idx2] = Math.max(ans1, Math.max(ans2, ans3));
            }
        }
        return dp[length1 - 1][length2 - 1];
    }
    // 其实就是求最长公共子序列
    // 动态规划
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        int[][] dp = new int[length1][length2];
        //先推第0行，再推第0列
        dp[0][0] = nums1[0] == nums2[0] ? 1 : 0;
        for (int idx2 = 1; idx2 < length2; idx2++) {
            dp[0][idx2] = nums1[0] == nums2[idx2] ? 1 : dp[0][idx2 - 1];
        }
        for (int idx1 = 1; idx1 < length1; idx1++) {
            dp[idx1][0] = nums1[idx1] == nums2[0] ? 1 : dp[idx1 - 1][0];
        }
        //最后依次往下推
        for (int idx1 = 1; idx1 < length1; idx1++) {
            for (int idx2 = 1; idx2 < length2; idx2++) {
                int ans1 = dp[idx1][idx2 - 1];
                int ans2 = dp[idx1 - 1][idx2];
                int ans3 = nums1[idx1] == nums2[idx2] ? (1 + dp[idx1 - 1][idx2 - 1]) : 0;
                dp[idx1][idx2] = Math.max(ans1, Math.max(ans2, ans3));
            }
        }
        return dp[length1 - 1][length2 - 1];
    }

}
