package com.jason.study.algorithmQuestions.class36;

/**
 * // 来自哈喽单车
 * // 本题是leetcode原题 : https://leetcode.com/problems/stone-game-iv/
 * 1510. 石子游戏 IV
 * Alice 和 Bob 两个人轮流玩一个游戏，Alice 先手。
 * <p>
 * 一开始，有 n个石子堆在一起。每个人轮流操作，正在操作的玩家可以从石子堆里拿走 任意非零 平方数个石子。
 * <p>
 * 如果石子堆里没有石子了，则无法操作的玩家输掉游戏。
 * <p>
 * 给你正整数n，且已知两个人都采取最优策略。如果 Alice 会赢得比赛，那么返回True，否则返回False。
 * <p>
 *
 * <p>
 * 示例 1：
 * <p>
 * 输入：n = 1
 * 输出：true
 * 解释：Alice 拿走 1 个石子并赢得胜利，因为 Bob 无法进行任何操作。
 * 示例 2：
 * <p>
 * 输入：n = 2
 * 输出：false
 * 解释：Alice 只能拿走 1 个石子，然后 Bob 拿走最后一个石子并赢得胜利（2 -> 1 -> 0）。
 * 示例 3：
 * <p>
 * 输入：n = 4
 * 输出：true
 * 解释：n 已经是一个平方数，Alice 可以一次全拿掉 4 个石子并赢得胜利（4 -> 0）。
 * 示例 4：
 * <p>
 * 输入：n = 7
 * 输出：false
 * 解释：当 Bob 采取最优策略时，Alice 无法赢得比赛。
 * 如果 Alice 一开始拿走 4 个石子， Bob 会拿走 1 个石子，然后 Alice 只能拿走 1 个石子，Bob 拿走最后一个石子并赢得胜利（7 -> 3 -> 2 -> 1 -> 0）。
 * 如果 Alice 一开始拿走 1 个石子， Bob 会拿走 4 个石子，然后 Alice 只能拿走 1 个石子，Bob 拿走最后一个石子并赢得胜利（7 -> 6 -> 2 -> 1 -> 0）。
 * 示例 5：
 * <p>
 * 输入：n = 17
 * 输出：false
 * 解释：如果 Bob 采取最优策略，Alice 无法赢得胜利。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/stone-game-iv
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Code11_StoneGameIV {

    public boolean winnerSquareGame(int n) {
        //返回先手能否能赢
        if (n == 0) {
            return false;
        }
        for (int i = 1; i * i <= n; i++) {
            //后手输了，我就赢了
            if (!winnerSquareGame(n - (i * i))) {
                return true;
            }
        }
        return false;
    }

    public boolean winnerSquareGame2(int n) {
        int[] dp = new int[n + 1];
        dp[0] = -1;
        return process(n, dp);
    }

    private boolean process(int n, int[] dp) {
        if (dp[n] != 0) {
            return dp[n] == 1;
        }
        int ans = -1;
        for (int i = 1; i * i <= n; i++) {
            //后手输了，我就赢了
            if (!process(n - (i * i), dp)) {
                ans = 1;
                break;
            }
        }
        dp[n] = ans;
        return ans == 1;
    }

    public static boolean winnerSquareGame3(int n) {
        boolean[] dp = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                //后手输了，我就赢了
                if (!dp[i - (j * j)]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }

}
