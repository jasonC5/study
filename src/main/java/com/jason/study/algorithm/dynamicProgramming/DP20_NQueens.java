package com.jason.study.algorithm.dynamicProgramming;

/**
 * N皇后问题是指在N*N的棋盘上要摆N个皇后，
 * 要求任何两个皇后不同行、不同列， 也不在同一条斜线上
 * 给定一个整数n，返回n皇后的摆法有多少种。  n=1，返回1
 * n=2或3，2皇后和3皇后问题无论怎么摆都不行，返回0
 * n=8，返回92
 *
 * @author JasonC5
 */
public class DP20_NQueens {

    public static void main(String[] args) {
        int n = 15;
        long start = System.currentTimeMillis();
        System.out.println(nQueens(n));
        long end1 = System.currentTimeMillis();
        System.out.println(end1-start + "ms");
        System.out.println(nQueens2(n));
        long end2 = System.currentTimeMillis();
        System.out.println(end2-end1 + "ms");
    }

    public static int nQueens(int n) {
        int[] tag = new int[n];
        return process(tag, 0, n);
    }

    private static int process(int[] tag, int i, int n) {
        if (i == n) {
            return 1;
        }
        int count = 0;
        for (int j = 0; j < n; j++) {
            if (canPut(tag, i, j)) {
                tag[i] = j;
                count += process(tag, i + 1, n);
            }
        }
        return count;
    }

    private static boolean canPut(int[] tag, int i, int j) {
        for (int k = 0; k < i; k++) {
            if (tag[k] == j || Math.abs(k - i) == Math.abs(tag[k] - j)) {
                return false;
            }
        }
        return true;
    }

    public static int nQueens2(int n) {
        if (n > 32 || n < 1) {
            return 0;
        }
        int mask = n == 32 ? -1 : (1 << n) - 1;
        return process2(mask, 0, 0, 0);
    }


    private static int process2(int mask, int colLimit, int leftLimit, int rightLimit) {
        if (mask == colLimit) {
            return 1;
        }
        //这一层，能放的位置是next二进制中1的位置
        int next = mask & (~(colLimit | leftLimit | rightLimit));
        //遍历所有1的位置
        int ans = 0;
        while (next != 0) {
            //最右边的位置
            int point = next & (-next);
            next -= point;
            ans += process2(mask, colLimit | point, (leftLimit | point) << 1, (rightLimit | point) >>> 1);
        }
        return ans;
    }

}
