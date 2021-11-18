package com.jason.study.algorithm.dynamicProgramming;

/**
 * https://leetcode-cn.com/problems/burst-balloons/
 * <p>
 * 有 n 个气球，编号为0 到 n - 1，每个气球上都标有一个数字，这些数字存在数组nums中。
 * <p>
 * 现在要求你戳破所有的气球。戳破第 i 个气球，你可以获得nums[i - 1] * nums[i] * nums[i + 1] 枚硬币。这里的 i - 1 和 i + 1 代表和i相邻的两个气球的序号。如果 i - 1或 i + 1 超出了数组的边界，那么就当它是一个数字为 1 的气球。
 * <p>
 * 求所能获得硬币的最大数量。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * 输入：nums = [3,1,5,8]
 * 输出：167
 * 解释：
 * nums = [3,1,5,8] --> [3,5,8] --> [3,8] --> [8] --> []
 * coins =  3*1*5    +   3*5*8   +  1*3*8  + 1*8*1 = 167
 * 示例 2：
 * <p>
 * 输入：nums = [1,5]
 * 输出：10
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/burst-balloons
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class DP21_BurstBalloons {
    public static void main(String[] args) {
        int[] arr = {3, 1, 5, 8};
        System.out.println(maxCoins(arr));
        System.out.println(maxCoins2(arr));
    }

    public static int maxCoins(int[] nums) {
        int length = nums.length;
        int[] help = new int[length + 2];
        help[0] = 1;
        help[length + 1] = 1;
        for (int i = 0; i < length; i++) {
            help[i + 1] = nums[i];
        }
        //当第i个气球，最后扎的时候，答案：
        return process(help, 1, length);
    }

    /**
     * L-1位置，和R+1位置，永远不越界，并且，[L-1] 和 [R+1] 一定没爆
     *
     * @param help
     * @param left
     * @param right
     * @return
     */
    private static int process(int[] help, int left, int right) {
        if (left == right) {
            return help[left - 1] * help[left] * help[right + 1];
        }
        int max = help[left - 1] * help[left] * help[right + 1] + process(help, left + 1, right);
        max = Math.max(max, process(help, left, right - 1) + help[left - 1] * help[right] * help[right + 1]);
        for (int i = left + 1; i < right; i++) {
            max = Math.max(max, process(help, left, i - 1) + help[left - 1] * help[i] * help[right + 1] + process(help, i + 1, right));
        }
        return max;
    }


    public static int maxCoins2(int[] nums) {
        int length = nums.length;
        int[] help = new int[length + 2];
        help[0] = 1;
        help[length + 1] = 1;
        for (int i = 0; i < length; i++) {
            help[i + 1] = nums[i];
        }
        int[][] dp = new int[length + 2][length + 2];
        for (int i = 1; i < length + 1; i++) {
            dp[i][i] = help[i - 1] * help[i] * help[i + 1];
        }
        for (int left = length; left > 0; left--) {
            for (int right = left + 1; right < length + 1; right++) {
                int max = help[left - 1] * help[left] * help[right + 1] + dp[left + 1][right];
                max = Math.max(max, dp[left][right - 1] + help[left - 1] * help[right] * help[right + 1]);
                for (int i = left + 1; i < right; i++) {
                    max = Math.max(max, dp[left][i - 1] + help[left - 1] * help[i] * help[right + 1] + dp[i + 1][right]);
                }
                dp[left][right] = max;
            }
        }
        return dp[1][length];
    }
}
