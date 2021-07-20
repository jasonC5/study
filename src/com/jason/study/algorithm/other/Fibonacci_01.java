package com.jason.study.algorithm.other;

/**
 * @author JasonC5
 */
public class Fibonacci_01 {

    public static int f1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return f1(n - 1) + f1(n - 2);
    }


    public static void main(String[] args) {
        int n = 19;
        System.out.println(f1(n));
        System.out.println(f2(n));
        System.out.println("===");
    }

    private static int f2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int[][] base = {
                {1, 1},
                {1, 0}
        };
        int[][] res = matrixPower(base, n - 2);
        return res[0][0] + res[1][0];
    }

    /**
     * 矩阵的多少次幂
     *
     * @param base
     * @param n
     * @return
     */
    private static int[][] matrixPower(int[][] base, int n) {
        int ans[][] = new int[base.length][base[0].length];
        //单位矩阵
        for (int i = 0; i < base.length; i++) {
            ans[i][i] = 1;
        }
        //此时base是1次幂
        while (n != 0) {
            if ((n & 1) != 0) {
                //矩阵乘法
                ans = muliMatrix(ans, base);
            }
            //几次幂
            base = muliMatrix(base, base);
            n >>>= 1;
        }
        return ans;
    }

    /**
     * 矩阵乘法，抄的，看不懂
     * @param m1
     * @param m2
     * @return
     */
    private static int[][] muliMatrix(int[][] m1, int[][] m2) {
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }
}
