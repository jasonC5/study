package com.jason.study.algorithmQuestions.class09;

/**
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 * <p>
 * 子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 * <p>
 *  
 * 示例 1：
 * <p>
 * 输入：nums = [10,9,2,5,3,7,101,18]
 * 输出：4
 * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
 * 示例 2：
 * <p>
 * 输入：nums = [0,1,0,3,2,3]
 * 输出：4
 * 示例 3：
 * <p>
 * 输入：nums = [7,7,7,7,7,7,7]
 * 输出：1
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-increasing-subsequence
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC300 {
    public int lengthOfLIS(int[] nums) {
        int length = nums.length;
        int maxLen = 1;
        int[] lenMin = new int[length];
        lenMin[0] = nums[0];
        for (int i = 1; i < length; i++) {
            if (nums[i] > lenMin[maxLen - 1]) {
                // 当前就能拉长最长递增子序列
                lenMin[maxLen++] = nums[i];
            } else if (nums[i] < lenMin[0]) {
                // 比最小的还小
                lenMin[0] = nums[i];
            } else {
                // 当前不能扩张，也不能覆盖最小值，看能不能更新，二分【找 >= nums[i] 的最大位置】
                int left = 0, right = maxLen - 1, find = left;
                while (left <= right) {
                    int mid = left + ((right - left) >> 1);
                    if (lenMin[mid] >= nums[i]){
                        find = mid;
                        right = mid - 1;
                    } else {
                        left = mid+1;
                    }
                }
                lenMin[find] = nums[i];
            }
        }
        return maxLen;
    }
}
