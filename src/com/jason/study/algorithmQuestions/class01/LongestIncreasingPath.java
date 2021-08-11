package com.jason.study.algorithmQuestions.class01;

/**
 * 给定一个二维数组matrix，
 * 你可以从任何位置出发，走向上下左右四个方向
 * 返回能走出来的最长的递增链长度
 * --记忆化搜索（自顶向下的动态规划）
 * --https://leetcode-cn.com/problems/longest-increasing-path-in-a-matrix/
 *
 * @author JasonC5
 */
public class LongestIncreasingPath {

    public static void main(String[] args) {
        int[][] matrix = {{9, 9, 4}, {6, 6, 8}, {2, 1, 1}};
        System.out.println(longestIncreasingPath1(matrix));
    }

    public static int longestIncreasingPath1(int[][] matrix) {
        int ans = 0;
        int[][] dp = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                ans = Math.max(ans, process(matrix, i, j, dp));
            }
        }
        return ans;
    }

    private static int process(int[][] matrix, int i, int j, int[][] dp) {
        if (dp[i][j] > 0) {
            return dp[i][j];
        }
        int ans = 0;
        int left = j > 0 && matrix[i][j] < matrix[i][j - 1] ? process(matrix, i, j - 1, dp) : 0;
        int right = j < matrix[0].length - 1 && matrix[i][j] < matrix[i][j + 1] ? process(matrix, i, j + 1, dp) : 0;
        int up = i > 0 && matrix[i][j] < matrix[i - 1][j] ? process(matrix, i - 1, j, dp) : 0;
        int down = i < matrix.length - 1 && matrix[i][j] < matrix[i + 1][j] ? process(matrix, i + 1, j, dp) : 0;
        ans = Math.max(Math.max(left, right), Math.max(up, down)) + 1;
        dp[i][j] = ans;
        return ans;
    }

}
