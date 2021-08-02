package com.jason.study.algorithm.skill;

import java.util.Arrays;

/**
 * int[] d，d[i]：i号怪兽的能力
 * int[] p，p[i]：i号怪兽要求的钱
 * 开始时你的能力是0，你的目标是从0号怪兽开始，通过所有的怪兽。
 * 如果你当前的能力，小于i号怪兽的能力，你必须付出p[i]的钱，贿赂这个怪兽，然后怪兽就会加入你，他的能力直接累加到你的能力上；如果你当前的能力，大于等于i号怪兽的能力，你可以选择直接通过，你的能力并不会下降，你也可以选择贿赂这个怪兽，然后怪兽就会加入你，他的能力直接累加到你的能力上。
 * 返回通过所有的怪兽，需要花的最小钱数。
 * --------估计dp表规模，那种规模小，用哪种
 *
 * @author JasonC5
 */
public class Monster {

    //逐个怪物看能力
    public static long fun1(int[] d, int[] p) {
        return process1(d, p, 0, 0);
    }

    private static long process1(int[] d, int[] p, int index, int ability) {
        //边界条件：
        if (index == d.length) {
            return 0;
        }
        if (ability < d[index]) {
            //能力不足，只能贿赂
            return p[index] + process1(d, p, index + 1, ability + d[index]);
        } else {
            //能力够，可贿赂，可不贿赂，
            return Math.min(process1(d, p, index + 1, ability), p[index] + process1(d, p, index + 1, ability + d[index]));
        }
    }

    private static long fun2(int[] d, int[] p) {
        int length = d.length;
        int maxAbility = Arrays.stream(d).boxed().mapToInt(x -> x).sum();
        long dp[][] = new long[length + 1][maxAbility + 1];
        //边界条件：
        for (int index = length - 1; index >= 0; index--) {
            for (int ability = 0; ability <= maxAbility; ability++) {
                if (ability + d[index] > maxAbility) {
                    continue;
                }
                if (ability < d[index]) {
                    //能力不足，只能贿赂
                    dp[index][ability] = p[index] + dp[index + 1][ability + d[index]];
                } else {
                    //能力够，可贿赂，可不贿赂，
                    dp[index][ability] = Math.min(dp[index + 1][ability], p[index] + dp[index + 1][ability + d[index]]);
                }
            }
        }
        return dp[0][0];
    }
    //改动态规划


    //思路2，根据钱，
    // 从0....index号怪兽，花的钱，必须严格==money
    // 如果通过不了，返回-1
    // 如果可以通过，返回能通过情况下的最大能力值
    public static long fun3(int[] d, int[] p) {
        int allMoney = 0;
        for (int i = 0; i < p.length; i++) {
            allMoney += p[i];
        }
        for (int i = 0; i < allMoney; i++) {
            if (process2(d, p, d.length - 1, i) != -1) {
                return i;
            }
        }
        return allMoney;
    }

    //钱数为money时，刚好能走过该位置，的power值
    private static long process2(int[] d, int[] p, int index, int money) {
        if (index == -1) {
            return money == 0 ? 0 : -1;
        }
        //花钱
        long p1 = -1;
        long maxPower1 = process2(d, p, index - 1, money - p[index]);
        if (maxPower1 != -1) {
            p1 = maxPower1 + d[index];
        }
        //不花钱
        long p2 = -1;
        long maxPower2 = process2(d, p, index - 1, money);
        if (maxPower2 != -1 && maxPower2 >= d[index]) {
            p2 = maxPower2;
        }
        return Math.max(p1, p2);
    }

    public static long fun4(int[] d, int[] p) {
        int allMoney = 0;
        for (int num : p) {
            allMoney += num;
        }
        long[][] dp = new long[d.length][allMoney + 1];
        //初始化
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j <= allMoney; j++) {
                dp[i][j] = -1;
            }
        }
        //推表
        dp[0][p[0]] = d[0];//第一个怪兽
        for (int index = 1; index < d.length; index++) {
            for (int money = 0; money <= allMoney; money++) {
                if (money >= p[index] && dp[index - 1][money - p[index]] != -1) {
                    dp[index][money] = dp[index - 1][money - p[index]] + d[index];
                }
                if (dp[index - 1][money] >= d[index]) {
                    dp[index][money] = Math.max(dp[index][money], dp[index - 1][money]);
                }
            }
        }
        for (int money = 0; money <= allMoney; money++) {
            if (dp[d.length - 1][money] > -1) {
                return money;
            }
        }
        return allMoney;
    }


    public static int[][] generateTwoRandomArray(int len, int value) {
        int size = (int) (Math.random() * len) + 1;
        int[][] arrs = new int[2][size];
        for (int i = 0; i < size; i++) {
            arrs[0][i] = (int) (Math.random() * value) + 1;
            arrs[1][i] = (int) (Math.random() * value) + 1;
        }
        return arrs;
    }

    public static void main(String[] args) {
        int len = 10;
        int value = 20;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            int[][] arrs = generateTwoRandomArray(len, value);
            int[] d = arrs[0];
            int[] p = arrs[1];
            long ans1 = fun1(d, p);
            long ans2 = fun4(d, p);
            if (ans1 != ans2) {
                System.out.println("oops!");
            }
        }

    }

}
