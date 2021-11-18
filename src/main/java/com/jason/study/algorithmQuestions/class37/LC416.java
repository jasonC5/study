package com.jason.study.algorithmQuestions.class37;

import java.util.Arrays;

/**
 * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,5,11,5]
 * 输出：true
 * 解释：数组可以分割成 [1, 5, 5] 和 [11] 。
 * 示例 2：
 * <p>
 * 输入：nums = [1,2,3,5]
 * 输出：false
 * 解释：数组不能分割成两个元素和相等的子集。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/partition-equal-subset-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC416 {
    public static void main(String[] args) {
        int[] nums = {1, 5, 11, 5};
        System.out.println(canPartition(nums));
    }

    public static boolean canPartition(int[] nums) {
        int length = nums.length;
        int sum = Arrays.stream(nums).sum();
        if ((sum & 1) == 1) {
            return false;
        }
        sum >>= 1;
        // 只取前i位，能否装满j空间
        boolean[][] dp = new boolean[length][sum + 1];
        // 0空间不用算
        for (int i = 0; i < length; i++) {
            dp[i][0] = true;
        }
        if (nums[0] <= sum) {
            dp[0][nums[0]] = true;
        }
        for (int i = 1; i < length; i++) {
            for (int j = 1; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - nums[i] > 0) {
                    dp[i][j] |= dp[i - 1][j - nums[i]];
                }
            }
            //提前跳出，加速
            if (dp[i][sum]) {
                return true;
            }
        }
        return dp[length - 1][sum];
    }

}
