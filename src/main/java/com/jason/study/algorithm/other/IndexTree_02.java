package com.jason.study.algorithm.other;

/**
 * 二维IndexTree
 * https://leetcode-cn.com/problems/range-sum-query-2d-mutable
 */
public class IndexTree_02 {
    private int[][] tree;
    private int[][] nums;
    private int n;
    private int m;

    public IndexTree_02(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        n = matrix.length;
        m = matrix[0].length;
        //前缀和数组，[0][0]弃之不用
        tree = new int[n + 1][m + 1];
        //原数组
        nums = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                update(i, j, matrix[i][j]);
            }
        }
    }

    public void update(int row, int col, int val) {
        if (n == 0 || m == 0) {
            return;
        }
        int add = val - nums[row][col];
        nums[row][col] = val;
        int i = row + 1;
        while (i <= n) {
            int j = col + 1;
            while (j <= m) {
                tree[i][j] += add;
                j += j & -j;
            }
            i += i & -i;
        }
    }

    /**
     * 点到[0][0]的前缀和
     *
     * @param row
     * @param col
     * @return
     */
    private int sum(int row, int col) {

        int ans = 0;
        int i = row + 1;
        while (i > 0) {
            int j = col + 1;
            while (j > 0) {
                ans = tree[i][j];
                j -= j & -j;
            }
            i -= i & -i;
        }
        return ans;
    }

    /**
     * 给定两个点，围成的矩形内部的和
     *
     * @param row1
     * @param col1
     * @param row2
     * @param col2
     * @return
     */
    public int sumRegion(int row1, int col1, int row2, int col2) {
        if (n == 0 || m == 0) {
            return 0;
        }
        return sum(row2, col2) - sum(row2, row1 - 1) - sum(row1 - 1, col2) + sum(row1 - 1, col1 - 1);
    }
}
