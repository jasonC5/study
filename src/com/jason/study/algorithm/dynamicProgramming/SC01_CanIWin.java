package com.jason.study.algorithm.dynamicProgramming;

import java.util.HashMap;
import java.util.Map;

/**
 * 状态压缩State Compression
 * <p>
 * 在 "100 game" 这个游戏中，两名玩家轮流选择从 1 到 10 的任意整数，累计整数和，先使得累计整数和达到或超过 100 的玩家，即为胜者。
 * <p>
 * 如果我们将游戏规则改为 “玩家不能重复使用整数” 呢？
 * <p>
 * 例如，两个玩家可以轮流从公共整数池中抽取从 1 到 15 的整数（不放回），直到累计整数和 >= 100。
 * <p>
 * 给定一个整数maxChoosableInteger（整数池中可选择的最大数）和另一个整数desiredTotal（累计和），判断先出手的玩家是否能稳赢（假设两位玩家游戏时都表现最佳）？
 * <p>
 * 你可以假设maxChoosableInteger不会大于 20，desiredTotal不会大于 300。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/can-i-win
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class SC01_CanIWin {

    public static void main(String[] args) {
        int choose = 10;
        int total = 1;
        System.out.println(canIWin(choose, total));
        System.out.println(canIWin2(choose, total));
        System.out.println(canIWin3(choose, total));
    }

    /**
     * @param choose 1~mcb可选
     * @param total  目标是>=这个数，
     * @return 先手是否能赢
     */
    public static boolean canIWin(int choose, int total) {
        //题目规定
        if (total == 0) {
            return true;
        }
        //1~choose全加起来都凑不够total，false
        if ((choose * (choose + 1) >> 1) < total) {
            return false;
        }
        return process(choose, 0, total);
    }

    private static boolean process(int choose, int map, int rest) {
        //前一个人已经凑够了，我输了！！
        if (rest <= 0) {
            return false;
        }
        //先手去尝试
        for (int i = 1; i <= choose; i++) {
            //没拿过才尝试
            if ((map & (1 << i)) == 0) {
                boolean next = process(choose, (map | (1 << i)), rest - i);
                //后手输了，我就赢了
                if (!next) {
                    return true;
                }
            }
        }
        return false;
    }


    //改dp
    public static boolean canIWin2(int choose, int total) {
        if (total == 0) {
            return true;
        }
        if ((choose * (choose + 1) >> 1) < total) {
            return false;
        }
        // dp[map] == 1  true
        // dp[map] == -1  false
        // dp[map] == 0  process(status) 没算过！去算！
        int[] dp = new int[1 << (choose + 1)];//记忆化搜索
        return process2(choose, 0, total, dp);
    }

    private static boolean process2(int choose, int map, int rest, int[] dp) {
        if (dp[map] != 0) {
            return dp[map] == 1;
        }
        //前一个人已经凑够了，我输了！！
        if (rest <= 0) {
            return false;
        }
        boolean ans = false;
        //先手去尝试
        for (int i = 1; i <= choose; i++) {
            //没拿过才尝试
            if ((map & (1 << i)) == 0) {
                boolean next = process2(choose, (map | (1 << i)), rest - i, dp);
                //后手输了，我就赢了
                if (!next) {
                    ans = true;
                    break;
                }
            }
        }
        dp[map] = ans ? 1 : -1;
        return ans;
    }

    //哈希表版本
    public static boolean canIWin3(int choose, int total) {
        if (total == 0) {
            return true;
        }
        if ((choose * (choose + 1) >> 1) < total) {
            return false;
        }
        // dp[map] == 1  true
        // dp[map] == -1  false
        // dp[map] == 0  process(status) 没算过！去算！
        Map<Integer, Boolean> cache = new HashMap<>();
        return process3(choose, 0, total, cache);
    }

    private static boolean process3(int choose, int map, int rest, Map<Integer, Boolean> cache) {
        if (cache.getOrDefault(map, false)) {
            return cache.getOrDefault(map, false);
        }
        //前一个人已经凑够了，我输了！！
        if (rest <= 0) {
            return false;
        }
        boolean ans = false;
        //先手去尝试
        for (int i = 1; i <= choose; i++) {
            //没拿过才尝试
            if ((map & (1 << i)) == 0) {
                boolean next = process3(choose, (map | (1 << i)), rest - i, cache);
                //后手输了，我就赢了
                if (!next) {
                    ans = true;
                    break;
                }
            }
        }
        cache.put(map, ans);
        return ans;
    }

}
