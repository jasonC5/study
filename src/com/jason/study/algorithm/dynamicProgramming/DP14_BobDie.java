package com.jason.study.algorithm.dynamicProgramming;

/**
 * 给定5个参数，N，M，row，col，k
 * 表示在N*M的区域上，醉汉Bob初始在(row,col)位置
 * Bob一共要迈出k步，且每步都会等概率向上下左右四个方向走一个单位
 * 任何时候Bob只要离开N*M的区域，就直接死亡
 * 返回k步之后，Bob还在N*M的区域的概率
 *
 * @author JasonC5
 */
public class DP14_BobDie {
    public static void main(String[] args) {
        System.out.println(livePosibility1(6, 6, 10, 50, 50));
        System.out.println(livePosibility2(6, 6, 10, 50, 50));
    }

    private static double livePosibility2(int a, int b, int k, int N, int M) {
        int[][][] dp = new int[k + 1][N][M];
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                dp[0][row][col] = 1;
            }
        }
        for (int step = 1; step <= k; step++) {
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < M; col++) {
                    int p1 = see(dp, step - 1, row - 1, col, N, M);
                    int p2 = see(dp, step - 1, row + 1, col, N, M);
                    int p3 = see(dp, step - 1, row, col - 1, N, M);
                    int p4 = see(dp, step - 1, row, col + 1, N, M);
                    dp[step][row][col] = p1 + p2 + p3 + p4;
                }
            }
        }

        return (double) dp[k][a][b] / Math.pow(4, k);
    }

    private static int see(int[][][] dp, int step, int row, int col, int N, int M) {
        if (row < 0 || row == N || col < 0 || col == M) {
            return 0;
        }
        return dp[step][row][col];
    }

    private static double livePosibility1(int a, int b, int step, int N, int M) {
        return (double) process(a, b, step, N, M) / Math.pow(4, step);
    }

    private static int process(int row, int col, int step, int N, int M) {
        if (row < 0 || row == N || col < 0 || col == M) {
            return 0;
        }
        if (step == 0) {
            return 1;
        }
        int up = process(row - 1, col, step - 1, N, M);
        int down = process(row + 1, col, step - 1, N, M);
        int left = process(row, col - 1, step - 1, N, M);
        int right = process(row, col + 1, step - 1, N, M);
        return up + down + right + left;
    }


}
