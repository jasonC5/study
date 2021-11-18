package com.jason.study.algorithm.dynamicProgramming;

/**
 * 机器人走路：给定一个长度L，机器人在这个长度内活动，初始位置start，目标位置target，和步数k，
 * 机器人每次走一步，在中间的时候左右走，边界的时候只能往一边走，返回能走到的不同方法数
 *
 * @author JasonC5
 */
public class DP1_RobotWalk {

    public static void main(String[] args) {
        int l = 6, start = 2, target = 4, step = 4;
        int ans = walk(l, start, target, step);
        System.out.println(ans);
        int ans2 = walk2(l, start, target, step);
        System.out.println(ans2);
        int ans3 = walk3(l, start, target, step);
        System.out.println(ans3);
    }

    //先用暴力递归写出来
    private static int walk(int l, int start, int target, int step) {
        //步数为0了，当前在目标上返回1，否则返回0
        return process1(l, start, target, step);
    }

    private static int process1(int l, int cur, int target, int step) {
        if (step == 0) {
            return cur == target ? 1 : 0;
        }
        if (cur == 1) {
            return walk(l, 2, target, step - 1);
        }
        if (cur == l) {
            return walk(l, l - 1, target, step - 1);
        }
        return walk(l, cur - 1, target, step - 1) + walk(l, cur + 1, target, step - 1);
    }

    //再改写动态规划
    //1.递归过程中，加个二位数组缓存【记忆化搜索】从顶向下的动态规划
    private static int walk2(int l, int start, int target, int step) {
        //步数为0了，当前在目标上返回1，否则返回0
        int[][] dp = new int[step + 1][l + 1];
        for (int i = 1; i <= step; i++) {
            for (int j = 0; j <= l; j++) {
                dp[i][j] = -1;
            }
        }
        return process2(l, start, target, step, dp);
    }

    private static int process2(int l, int cur, int target, int step, int[][] dp) {
        if (dp[step][cur] != -1) {
            return dp[step][cur];
        }
        int ans = 0;
        if (step == 0) {
            ans = cur == target ? 1 : 0;
        } else if (cur == 1) {
            ans = walk(l, 2, target, step - 1);
        } else if (cur == l) {
            ans = walk(l, l - 1, target, step - 1);
        } else {
            ans = walk(l, cur - 1, target, step - 1) + walk(l, cur + 1, target, step - 1);
        }
        dp[step][cur] = ans;
        return ans;
    }

    //2.直接推dp数组版本动态规划
    private static int walk3(int l, int start, int target, int step) {
        if (l < 2 || start < 0 || start > l || target < 0 || target > l || step < 0) {
            return 0;
        }
        //步数为0了，当前在目标上返回1，否则返回0
        int[][] dp = new int[step + 1][l + 1];
        //step=0的时候只有target那一行为1，其他全为0【第0列初始化】
        dp[0][target] = 1;
        //
        for (int i = 1; i <= step; i++) {
            dp[i][1] = dp[i - 1][2];
            for (int j = 2; j < l; j++) {
                dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j + 1];
            }
            dp[i][l] = dp[i][l - 1];
        }
        return dp[step][start];
    }
}
