package com.jason.study.algorithm.other;

/**
 * 给定一个数N，想象只由0和1两种字符，组成的所有长度为N的字符串
 * 如果某个字符串,任何0字符的左边都有1紧挨着,认为这个字符串达标
 * 返回有多少达标的字符串
 * 观察得到：初始项为1,2的斐波那契数列
 * F(n) = F(n-1) + F(n-2)
 *
 * @author JasonC5
 */
public class Fibonacci_03_ZeroLeftOne {

    public static int getNum1(int n) {
        if (n < 1) {
            return 0;
        }
        return process(1, n);
    }

    public static int process(int i, int n) {
        if (i == n - 1) {
            return 2;
        }
        if (i == n) {
            return 1;
        }
        return process(i + 1, n) + process(i + 2, n);
    }

    public static void main(String[] args) {
        int i = 10;
        System.out.println(getNum1(i));
        System.out.println(getNum2(i));
    }

    private static int getNum2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n < 3) {
            return n;
        }
        int[][] base = {
                {1, 1},
                {1, 0}
        };
        int[][] res = matrixPower(base, n - 2);
        return 2 * res[0][0] + res[1][0];
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
            //将n转为二进制，计算每个二进制位的幂数，若当前位置是1，则相乘
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
