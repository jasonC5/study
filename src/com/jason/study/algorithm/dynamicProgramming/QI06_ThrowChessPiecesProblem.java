package com.jason.study.algorithm.dynamicProgramming;
//

import java.util.Enumeration;

/**
 * leetcode测试链接：https://leetcode.com/problems/super-egg-drop
 * <p>
 * 一座大楼有 0~N 层，地面算作第 0 层，最高的一层为第 N 层。已知棋子从第 0 层掉落肯定 不会摔碎，从第 i 层掉落可能会摔碎，也可能不会摔碎(1≤i≤N)。给定整数 N 作为楼层数， 再给定整数 K 作为棋子数，返 回如果想找到棋子不会摔碎的最高层数，即使在最差的情况下扔 的最少次数。一次只能扔一个棋子。
 * 【举例】
 * N=10，K=1。
 * 返回 10。因为只有 1 棵棋子，所以不得不从第 1 层开始一直试到第 10 层，在最差的情况 下，即第 10 层 是不会摔坏的最高层，最少也要扔 10 次。
 * N=3，K=2。
 * 返回 2。先在 2 层扔 1 棵棋子，如果碎了，试第 1 层，如果没碎，试第 3 层。 N=105，K=2 返回 14。
 * 第一个棋子先在 14 层扔，碎了则用仅存的一个棋子试 1~13。 若没碎，第一个棋子继续在 27 层扔，碎了则 用仅存的一个棋子试 15~26。
 * 若没碎，第一个棋子继续在 39 层扔，碎了则用仅存的一个棋子试 28~38。 若 没碎，第一个棋子继续在 50 层扔，碎了则用仅存的一个棋子试 40~49。
 * 若没碎，第一个棋子继续在 60 层扔， 碎了则用仅存的一个棋子试 51~59。
 * 若没碎，第一个棋子继续在 69 层扔，碎了则用仅存的一个棋子试 61~68。
 * 若没碎，第一个棋子继续在 77 层扔，碎了则用仅存的一个棋子试 70~76。
 * 若没碎，第一个棋子继续在 84 层 扔，碎了则用仅存的一个棋子试 78~83。
 * 若没碎，第一个棋子继续在 90 层扔，碎了则用仅存的一个棋子试 85~89。
 * 若没碎，第一个棋子继续在 95 层扔，碎了则用仅存的一个棋子试 91~94。
 * 若没碎，第一个棋子继续 在 99 层扔，碎了则用仅存的一个棋子试 96~98。
 * 若没碎，第一个棋子继续在 102 层扔，碎了则用仅存的一 个棋子试 100、101。
 * 若没碎，第一个棋子继续在 104 层扔，碎了则用仅存的一个棋子试 103。 若没碎，
 * 第 一个棋子继续在 105 层扔，若到这一步还没碎，那么 105 便是结果。
 *
 * @author JasonC5
 */
public class QI06_ThrowChessPiecesProblem {
    public static void main(String[] args) {
        System.out.println(superEggDrop1(2, 6));
        System.out.println(superEggDrop2(2, 6));
        System.out.println(superEggDrop3(2, 6));
    }


    /**
     * 有几个鸡蛋，一共多少层楼，想知道最高在基层扔下来不会碎，实验的最小次数
     *
     * @param kChess
     * @param nLevel
     * @return
     */
    public static int superEggDrop1(int kChess, int nLevel) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        return process1(nLevel, kChess);
    }

    //暴力解
    private static int process1(int restLevel, int num) {
        //一层一层试
        if (restLevel == 0) {
            return 0;
        }
        if (num == 1) {
            return restLevel;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 1; i <= restLevel; i++) {
            ans = Math.min(ans, /*可能会碎，可能不碎，取最大值*/Math.max(process1(i - 1, num - 1), process1(restLevel - i, num)));
        }
        return ans + 1;
    }

    public static int superEggDrop3(int kChess, int nLevel) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        if (kChess == 1) {
            return nLevel;
        }
        int[][] dp = new int[nLevel + 1][kChess + 1];
        //最好结果中，第一次扔的楼层记下来
        int[][] best = new int[nLevel + 1][kChess + 1];
        //dp[i][0] = 0;省略
        //只有一颗棋子的时候，有几层就要几次
        for (int i = 1; i < nLevel; i++) {
            dp[i][1] = i;
            best[i][1] = 0;
        }
        //只有一楼的时候，不管几个蛋都只需要在一楼扔一次就够了
        for (int i = 1; i <= kChess; i++) {
            dp[1][i] = 1;
            best[1][i] = 1;
        }
        for (int level = 2; level < nLevel + 1; level++) {
            for (int num = kChess; num > 1; num--) {
                int ans = Integer.MAX_VALUE;
                int bestChoose = -1;
                for (int k = best[level - 1][num]; k <= (num == kChess ? level : best[level][num + 1]); k++) {
                    int curCount = Math.max(dp[k - 1][num - 1], dp[level - k][num]);
                    if (curCount <= ans) {
                        ans = curCount;
                        bestChoose = k;
                    }
                }
                best[level][num] = bestChoose;
                dp[level][num] = ans + 1;
            }
        }
        return dp[nLevel][kChess];
    }

    public static int superEggDrop2(int kChess, int nLevel) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        if (kChess == 1) {
            return nLevel;
        }
        int[][] dp = new int[nLevel + 1][kChess + 1];
        //dp[i][0] = 0;省略
        //只有一颗棋子的时候，有几层就要几次
        for (int i = 1; i < nLevel; i++) {
            dp[i][1] = i;
        }
        for (int level = 1; level <= nLevel; level++) {
            for (int num = 2; num <= kChess; num++) {
                int ans = Integer.MAX_VALUE;
                for (int k = 1; k <= level; k++) {
                    ans = Math.min(ans, Math.max(dp[k - 1][num - 1], dp[level - k][num]));
                }
                dp[level][num] = ans + 1;
            }
        }
        return dp[nLevel][kChess];
    }

    public static int superEggDrop4(int kChess, int nLevel) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        //几个鸡蛋
        int[] dp = new int[kChess];//空间优化，一维数组往下滚动，当二维数组用
        //尝试次数
        int res = 0;
        while (true) {
            res++;
            int previous = 0;
            for (int i = 0; i < dp.length; i++) {
                int tmp = dp[i];
                //左边的（i-1个鸡蛋rest次，的时候能尝试出几层） 加上面的（i个鸡蛋rest-1次能尝试出几层） 加一（自己这次用到的）
                dp[i] = dp[i] + previous + 1;
                previous = tmp;
                //当能到达的楼层第一次大于指定楼层时，就能确定次数
                if (dp[i] >= nLevel) {
                    return res;
                }
            }
        }
    }

}
