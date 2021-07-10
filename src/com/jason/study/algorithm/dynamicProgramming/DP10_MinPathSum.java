package com.jason.study.algorithm.dynamicProgramming;

/**
 * 给定一个二维数组matrix，一个人必须从左上角出发，最后到达右下角
 * 沿途只可以向下或者向右走，沿途的数字都累加就是距离累加和
 * 返回最小距离累加和
 *
 * @author JasonC5
 */
public class DP10_MinPathSum {
    public static void main(String[] args) {
        int rowSize = 5;
        int colSize = 5;
        int[][] m = generateRandomMatrix(rowSize, colSize);
        print(m);
        System.out.println(minPathSum1(m));
        System.out.println(minPathSum2(m));
    }

    private static void print(int[][] m) {
        for (int[] ints : m) {
            for (int anInt : ints) {
                System.out.print(anInt + "\t");
            }
            System.out.println();
        }
    }

    // for test
    public static int[][] generateRandomMatrix(int rowSize, int colSize) {
        if (rowSize < 0 || colSize < 0) {
            return null;
        }
        int[][] result = new int[rowSize][colSize];
        for (int i = 0; i != result.length; i++) {
            for (int j = 0; j != result[0].length; j++) {
                result[i][j] = (int) (Math.random() * 10);
            }
        }
        return result;
    }

    public static int minPathSum1(int[][] m) {
        if (m == null || m.length == 0 || m[0].length == 0) {
            return -1;
        }
        int row = m.length;
        int col = m[0].length;
        int[][] dp = new int[row][col];
        dp[row - 1][col - 1] = m[row - 1][col - 1];
        for (int rowNum = row - 2; rowNum >= 0; rowNum--) {
            dp[rowNum][col - 1] = m[rowNum][col - 1] + dp[rowNum + 1][col - 1];
        }
        for (int colNum = col - 2; colNum >= 0; colNum--) {
            dp[row - 1][colNum] = m[row - 1][colNum] + dp[row - 1][colNum + 1];
        }

        for (int rowNum = row - 2; rowNum >= 0; rowNum--) {
            for (int colNum = col - 2; colNum >= 0; colNum--) {
                dp[rowNum][colNum] = m[rowNum][colNum] + Math.min(dp[rowNum + 1][colNum], dp[rowNum][colNum + 1]);
            }
        }
        return dp[0][0];
    }

    public static int minPathSum2(int[][] m) {
        if (m == null || m.length == 0 || m[0].length == 0) {
            return -1;
        }
        int row = m.length;
        int col = m[0].length;
        int dp[] = new int[col];
        dp[col - 1] = m[row - 1][col - 1];
        for (int colNum = col - 2; colNum >= 0; colNum--) {
            dp[colNum] = m[row - 1][colNum] + dp[colNum + 1];
        }
        for (int rowNum = row - 2; rowNum >= 0; rowNum--) {
            dp[col - 1] = dp[col - 1] + m[rowNum][col - 1];
            for (int colNum = col - 2; colNum >= 0; colNum--) {
                dp[colNum] = m[rowNum][colNum] + Math.min(dp[colNum], dp[colNum + 1]);
            }
        }
        return dp[0];
    }


}
