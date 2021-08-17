package com.jason.study.algorithmQuestions.class03;

/**
 * 给定一个只有0和1组成的二维数组
 * 返回边框全是1的最大正方形面积
 * https://leetcode.com/problems/largest-1-bordered-square/
 * --所有正方形【O(n^3)】
 * --验证：【O(1)】
 * --整理出来一个信息：任何一个点：右方有多少个连续的1，下方有多少个连续的1【O(n^2)】
 *
 * @author JasonC5
 */
public class Largest1BorderedSquare {

    public static void main(String[] args) {
//        int[][] grid = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
//        int[][] grid = {{1, 1, 0, 0}};
        int[][] grid = {{0}};

        System.out.println(largest1BorderedSquare(grid));
    }

    public static int largest1BorderedSquare(int[][] grid) {
        //先针对每个点整理2个信息：
        //向右有多少个连续的1、向下有多少个连续的1
        int rowLen = grid.length;
        int colLen = grid[0].length;
        int[][] rightLen = new int[rowLen][colLen];
        int[][] downLen = new int[rowLen][colLen];
        //最后一行【为了不做那么多if else】
        for (int i = colLen - 1; i >= 0; i--) {
            downLen[rowLen - 1][i] = grid[rowLen - 1][i];
            rightLen[rowLen - 1][i] = grid[rowLen - 1][i] == 0 ? 0 : (1 + (i == colLen - 1 ? 0 : rightLen[rowLen - 1][i + 1]));
        }
        //最后一列【为了不做那么多if else】
        for (int i = rowLen - 1; i >= 0; i--) {
            rightLen[i][colLen - 1] = grid[i][colLen - 1];
            downLen[i][colLen - 1] = grid[i][colLen - 1] == 0 ? 0 : (1 + (i == rowLen - 1 ? 0 : downLen[i + 1][colLen - 1]));
        }
        for (int row = rowLen - 2; row >= 0; row--) {
            for (int col = colLen - 2; col >= 0; col--) {
                if (grid[row][col] == 0) {
                    downLen[row][col] = 0;
                    rightLen[row][col] = 0;
                } else {
                    downLen[row][col] = downLen[row + 1][col] + 1;
                    rightLen[row][col] = rightLen[row][col + 1] + 1;
                }
            }
        }
        //有了rightLen和downLen 之后，只需要判断判断三个点的4个if
        int maxLan = 0;
        for (int row = 0; row < rowLen; row++) {
            for (int col = 0; col < colLen; col++) {
                int top = Math.min(rowLen - row, colLen - col);
                for (int len = maxLan + 1; len <= top; len++) {
                    if (downLen[row][col] >= len && rightLen[row][col] >= len && rightLen[row + len - 1][col] >= len && downLen[row][col + len - 1] >= len) {
                        maxLan = len;
                    }
                }
            }
        }
        return maxLan * maxLan;//面积
    }
}
