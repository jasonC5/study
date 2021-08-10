package com.jason.study.algorithm.dynamicProgramming;

/**
 * 有台奇怪的打印机有以下两个特殊要求：
 * <p>
 * 打印机每次只能打印由 同一个字符 组成的序列。
 * 每次可以在任意起始和结束位置打印新字符，并且会覆盖掉原来已有的字符。
 * 给你一个字符串 s ，你的任务是计算这个打印机打印它需要的最少打印次数。
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "aaabbb"
 * 输出：2
 * 解释：首先打印 "aaa" 然后打印 "bbb"。
 * 示例 2：
 * <p>
 * 输入：s = "aba"
 * 输出：2
 * 解释：首先打印 "aaa" 然后在第二个位置打印 "b" 覆盖掉原来的字符 'a'。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/strange-printer
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class DP24_StrangePrinter {

    //以i为分界线，
    public static void main(String[] args) {
        System.out.println(strangePrinter("aaabbb"));
        System.out.println(strangePrinter2("aaabbb"));
    }

    //一个字符最少打印次数
    //以i为分界线，左边
    public static int strangePrinter(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] arr = s.toCharArray();
        return process(arr, 0, arr.length - 1);
    }

    private static int process(char[] arr, int left, int right) {
        if (left == right) {
            return 1;
        }
        int ans = right - left + 1;
        for (int i = left + 1; i <= right; i++) {
            ans = Math.min(ans, process(arr, left, i - 1) + process(arr, i, right) - (arr[left] == arr[i] ? 1 : 0));
        }
        return ans;
    }


    public static int strangePrinter2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] arr = s.toCharArray();
        int length = arr.length;
        int[][] dp = new int[length][length];
        for (int i = 0; i < length; i++) {
            dp[i][i] = 1;
        }
        for (int left = length - 2; left >= 0; left--) {
            for (int right = left + 1; right < length; right++) {
                int ans = right - left + 1;
                for (int i = left + 1; i <= right; i++) {
                    ans = Math.min(ans, dp[left][i - 1] + dp[i][right] - (arr[left] == arr[i] ? 1 : 0));
                }
                dp[left][right] = ans;
            }
        }
        return dp[0][length - 1];
    }
}
