package com.jason.study.algorithmQuestions.class03;

import java.util.Arrays;

/**
 * 给你一个整数数组 nums 和一个目标值 goal 。
 * <p>
 * 你需要从 nums 中选出一个子序列，使子序列元素总和最接近 goal 。也就是说，如果子序列元素和为 sum ，你需要 最小化绝对差 abs(sum - goal) 。
 * <p>
 * 返回 abs(sum - goal) 可能的 最小值 。
 * <p>
 * 注意，数组的子序列是通过移除原始数组中的某些元素（可能全部或无）而形成的数组。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [5,-7,3,5], goal = 6
 * 输出：0
 * 解释：选择整个数组作为选出的子序列，元素和为 6 。
 * 子序列和与目标值相等，所以绝对差为 0 。
 * 示例 2：
 * <p>
 * 输入：nums = [7,-9,15,-2], goal = -5
 * 输出：1
 * 解释：选出子序列 [7,-9,-2] ，元素和为 -4 。
 * 绝对差为 abs(-4 - (-5)) = abs(1) = 1 ，是可能的最小值。
 * 示例 3：
 * <p>
 * 输入：nums = [1,2,3], goal = -7
 * 输出：7
 * <p>
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/closest-subsequence-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class ClosestSubsequenceSum {

    public static void main(String[] args) {
        int[] arr = {1,2,3};
        int goal = -7;
        System.out.println(new ClosestSubsequenceSum().minAbsDifference(arr, goal));
    }

    public static int[] l = new int[1 << 20];
    public static int[] r = new int[1 << 20];

    //1 <= nums.length <= 40        分治
    public int minAbsDifference(int[] nums, int goal) {
        if (nums == null || nums.length == 0) {
            return goal;
        }
        //前20位全排列(加或者不加)，后20位全排列，然后从遍历其中一个数字，在另一个数组中找答案
        int length = nums.length;
        int leftCount = process(nums, 0, length >> 1, 0, 0, l);
        int rightCount = process(nums, length >> 1, length, 0, 0, r);
        Arrays.sort(l, 0, leftCount);
        Arrays.sort(r, 0, rightCount--);
        int ans = Math.abs(goal);
        for (int left = 0; left < leftCount; left++) {
            int rest = goal - l[left];
            //TODO
            while (rightCount > 0 && Math.abs(rest - r[rightCount]) >= Math.abs(rest - r[rightCount - 1])) {
                rightCount--;
            }
            ans = Math.min(ans, Math.abs(rest - r[rightCount]));
        }
        return ans;
    }

    private int process(int[] nums, int index, int end, int sum, int fill, int[] arr) {
        if (index == end) {
            arr[fill++] = sum;
        } else {
            fill = process(nums, index + 1, end, sum, fill, arr);
            fill = process(nums, index + 1, end, sum + nums[index], fill, arr);
        }
        return fill;
    }
}

