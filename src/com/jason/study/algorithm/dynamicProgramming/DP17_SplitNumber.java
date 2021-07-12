package com.jason.study.algorithm.dynamicProgramming;

/**
 * 给定一个正数n，求n的裂开方法数，
 * 规定：后面的数不能比前面的数小
 * 比如4的裂开方法有：
 * 1+1+1+1、1+1+2、1+3、2+2、4
 * 5种，所以返回5
 *
 * @author JasonC5
 */
public class DP17_SplitNumber {
    public static void main(String[] args) {
        int num = 10;
        int ans1 = split1(num);
        System.out.println(ans1);
        int ans2 = dp1(num);
        System.out.println(ans2);
        int ans3 = dp2(num);
        System.out.println(ans3);
    }

    private static int split1(int num) {
        if (num < 0) {
            return 0;
        }
        if (num == 1) {
            return 1;
        }
        return process(num, 1);
    }

    private static int process(int rest, int pre) {
        if (rest == 0) {
            return 1;
        }
        if (pre > rest) {
            return 0;
        }
        int ans = 0;
        for (int i = pre; i <= rest; i++) {
            ans += process(rest - i, i);
        }
        return ans;
    }

    private static int dp1(int num) {
        if (num < 0) {
            return 0;
        }
        if (num == 1) {
            return 1;
        }
        int[][] dp = new int[num + 1][num + 1];
        for (int pre = 1; pre <= num; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }
        for (int pre = num - 1; pre >= 1; pre--) {
            for (int rest = pre + 1; rest <= num; rest++) {
                int ans = 0;
                for (int i = pre; i <= rest; i++) {
                    ans += dp[i][rest - i];
                }
                dp[pre][rest] = ans;
            }
        }
        return dp[1][num];
    }

    private static int dp2(int num) {
        if (num < 0) {
            return 0;
        }
        if (num == 1) {
            return 1;
        }
        int[][] dp = new int[num + 1][num + 1];
        for (int pre = 1; pre <= num; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }
        for (int pre = num - 1; pre >= 1; pre--) {
            for (int rest = pre + 1; rest <= num; rest++) {
//                int ans = 0;
//                for (int i = pre; i <= rest; i++) {
//                    ans += dp[i][rest - i];
//                }
//                dp[pre][rest] = ans;


//                dp[pre][rest] = dp[pre + 1][rest] ;
//                if (rest - pre >= 0) {        //rest >= pre + 1
//                    dp[pre][rest] += dp[pre][rest - pre];
//                }

                dp[pre][rest] = dp[pre + 1][rest] + dp[pre][rest - pre];
            }
        }
        return dp[1][num];
    }

}
