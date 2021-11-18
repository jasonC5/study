package com.jason.study.algorithm.dynamicProgramming;

/**
 * 给定3个参数，N，M，K
 * 怪兽有N滴血，等着英雄来砍自己
 * 英雄每一次打击，都会让怪兽流失[0~M]的血量
 * 到底流失多少？每一次在[0~M]上等概率的获得一个值
 * 求K次打击之后，英雄把怪兽砍死的概率
 *
 * @author JasonC5
 */
public class DP15_AttackMonster {

    public static void main(String[] args) {
        //怪兽血量，每次可能掉血，刀数
        int N = 10, M = 3, K = 5;
        double p1 = probability1(N, M, K);
        System.out.println(p1);
        double p2 = dp1(N, M, K);
        System.out.println(p2);
        double p3 = dp2(N, M, K);
        System.out.println(p3);
    }

    private static double probability1(int n, int m, int k) {
        if (n < 1 || m < 1 || k < 1) {
            return 0;
        }
        long denominator = (int) Math.pow(m + 1, k);
        long count = process(n, m, k);
        return (double) count / denominator;
    }

    private static long process(int n, int m, int k) {
        if (n <= 0) {
            return (long) Math.pow(m + 1, k);
        }
        if (k < 0) {
            return 0;
        }
        int ans = 0;
        for (int i = 0; i <= m; i++) {
            ans += process(n - i, m, k - 1);
        }
        return ans;
    }

    public static double dp1(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long all = (long) Math.pow(M + 1, K);
        long[][] dp = new long[K + 1][N + 1];
        dp[0][0] = 1;
        for (int count = 1; count <= K; count++) {
            dp[count][0] = (long) Math.pow(M + 1, count);
            for (int hp = 1; hp <= N; hp++) {
                int ans = 0;
                for (int i = 0; i <= M; i++) {
                    if (hp - i > 0) {
                        ans += dp[count - 1][hp - i];
                    } else {
                        ans += dp[count - 1][0];
                    }
                }
                dp[count][hp] = ans;
            }
        }
        long kill = dp[K][N];
        return (double) ((double) kill / (double) all);
    }

    public static double dp2(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long all = (long) Math.pow(M + 1, K);
        long[][] dp = new long[K + 1][N + 1];
        dp[0][0] = 1;
        for (int count = 1; count <= K; count++) {
            dp[count][0] = (long) Math.pow(M + 1, count);
            for (int hp = 1; hp <= N; hp++) {
//                int ans = 0;
//                for (int i = 0; i <= M; i++) {
//                    if (hp - i > 0) {
//                        ans += dp[count - 1][hp - i];
//                    } else {
//                        ans += dp[count - 1][0];
//                    }
//                }
//                dp[count][hp] = ans;
                dp[count][hp] = dp[count - 1][hp] + dp[count][hp - 1];
                if (hp - 1 - M > 0) {
                    dp[count][hp] -= dp[count - 1][hp - 1 - M];
                } else {
                    dp[count][hp] -= dp[count - 1][0];
                }
            }
        }
        long kill = dp[K][N];
        return (double) ((double) kill / (double) all);
    }


}
