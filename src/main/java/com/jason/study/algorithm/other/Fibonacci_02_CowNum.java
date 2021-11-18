package com.jason.study.algorithm.other;

/**
 * 第一年农场有1只成熟的母牛A，往后的每年：
 * 1）每一只成熟的母牛都会生一只母牛
 * 2）每一只新出生的母牛都在出生的第三年成熟
 * 3）每一只母牛永远不会死
 * 返回N年后牛的数量
 *
 * @author JasonC5
 */
public class Fibonacci_02_CowNum {

    //对数器
    public static int c1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        return c1(n - 1) + c1(n - 3);
    }

    public static void main(String[] args) {
        int n = 19;
        System.out.println(c1(n));
        System.out.println(cowNum(n));
        System.out.println("===");
    }

    private static int cowNum(int n) {
        if (n < 1) {
            return 0;
        }
        if (n < 4) {
            return n;
        }
        int[][] base = {
                {1, 1, 0},
                {0, 0, 1},
                {1, 0, 0}
        };
        int[][] res = matrixPower(base, n - 3);
        return 3 * res[0][0] + 2 * res[1][0] + res[2][0];
    }

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
     *
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
