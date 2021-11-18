package com.jason.study.algorithm.dynamicProgramming;

/**
 * 马走日
 * <p>
 * 自行搜索或者想象一个象棋的棋盘，
 * 然后把整个棋盘放入第一象限，棋盘的最左下角是(0,0)位置
 * 那么整个棋盘就是横坐标上9条线、纵坐标上10条线的区域
 * 给你三个 参数 x，y，k
 * 返回“马”从(0,0)位置出发，必须走k步
 * 最后落在(x,y)上的方法数有多少种?
 *
 * @author JasonC5
 */
public class DP8_HorseJump {

    public static void main(String[] args) {
        int x = 7;
        int y = 7;
        int step = 10;
        System.out.println(jump(x, y, step));
        System.out.println(dp(x, y, step));
    }

    /**
     * 从[0,0]出发，走K步之后，到达[a,b]
     *
     * @param a
     * @param b
     * @param k
     * @return
     */
    public static int jump(int a, int b, int k) {
        return process(0, 0, k, a, b);
    }

    private static int process(int x, int y, int step, int a, int b) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        if (step == 0) {
            return (x == a && y == b) ? 1 : 0;
        }
        //八种可能
        int ans = process(x + 1, y + 2, step - 1, a, b);
        ans += process(x + 1, y - 2, step - 1, a, b);
        ans += process(x - 1, y - 2, step - 1, a, b);
        ans += process(x - 1, y + 2, step - 1, a, b);
        ans += process(x + 2, y + 1, step - 1, a, b);
        ans += process(x + 2, y - 1, step - 1, a, b);
        ans += process(x - 2, y - 1, step - 1, a, b);
        ans += process(x - 2, y + 1, step - 1, a, b);
        return ans;
    }

    public static int dp(int a, int b, int k) {
        int[][][] dp = new int[10][9][k + 1];
        dp[a][b][0] = 1;
        for (int l = 1; l <= k; l++) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    int ans = get(dp, i + 1, j + 2, l - 1);
                    ans += get(dp, i + 1, j - 2, l - 1);
                    ans += get(dp, i - 1, j - 2, l - 1);
                    ans += get(dp, i - 1, j + 2, l - 1);
                    ans += get(dp, i + 2, j + 1, l - 1);
                    ans += get(dp, i + 2, j - 1, l - 1);
                    ans += get(dp, i - 2, j - 1, l - 1);
                    ans += get(dp, i - 2, j + 1, l - 1);
                    dp[i][j][l] = ans;
                }
            }
        }
        return dp[0][0][k];
    }

    private static int get(int[][][] dp, int x, int y, int z) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        return dp[x][y][z];
    }


}
