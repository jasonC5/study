package com.jason.study.algorithm.dynamicProgramming;

/**
 * // 测试链接：https://leetcode.com/problems/longest-palindromic-subsequence/
 * <p>
 * 给定一个字符串str，返回这个字符串的最长回文子序列长度
 * 比如 ： str = “a12b3c43def2ghi1kpm”
 * 最长回文子序列是“1234321”或者“123c321”，返回长度7
 *
 * @author JasonC5
 */
public class DP7_PalindromeSubsequence {

    public static void main(String[] args) {
//        String s = "cbbd";
        String s = "bbbab";
        System.out.println(longestPalindromeSubseq1(s));
        System.out.println(longestPalindromeSubseq2(s));
    }

    public static int longestPalindromeSubseq1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        return process(chars, 0, chars.length - 1);
    }

    private static int process(char[] chars, int left, int right) {

        if (left == right) {
            return 1;
        } else if (left > right) {
            return 0;
        }
        int p1 = process(chars, left + 1, right);
        int p2 = process(chars, left, right - 1);
        int p3 = chars[left] != chars[right] ? 0 : (2 + process(chars, left + 1, right - 1));
        return Math.max(Math.max(p1, p2), p3);
    }

    public static int longestPalindromeSubseq2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int[][] dp = new int[chars.length][chars.length];
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = 1;
        }
        int length = s.length();
        for (int i = length - 2; i >= 0; i--) {
            for (int j = i + 1; j < length; j++) {
                int p1 = dp[i + 1][j];
                int p2 = dp[i][j - 1];
                int p3 = chars[i] != chars[j] ? 0 : (2 + dp[i + 1][j - 1]);
                dp[i][j] = Math.max(Math.max(p1, p2), p3);
            }
        }
        return dp[0][length - 1];
    }

}
