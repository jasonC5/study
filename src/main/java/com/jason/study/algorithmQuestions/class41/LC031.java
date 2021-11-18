package com.jason.study.algorithmQuestions.class41;

/**
 * 实现获取 下一个排列 的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列（即，组合出下一个更大的整数）。
 * <p>
 * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
 * <p>
 * 必须 原地 修改，只允许使用额外常数空间。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,2,3]
 * 输出：[1,3,2]
 * 示例 2：
 * <p>
 * 输入：nums = [3,2,1]
 * 输出：[1,2,3]
 * 示例 3：
 * <p>
 * 输入：nums = [1,1,5]
 * 输出：[1,5,1]
 * 示例 4：
 * <p>
 * 输入：nums = [1]
 * 输出：[1]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/next-permutation
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC031 {

    public void nextPermutation(int[] nums) {
        int length = nums.length;
        // 从右往左找到第一个降序的位置
        int reverseIndex = -1;
        for (int i = length - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                reverseIndex = i;
                break;
            }
        }
        // 如果到最后都没找到，说明本字符串是最后一个字典序，按照题意做逆序返回字典序第一个
        if (reverseIndex == -1) {
            reverse(nums, 0, length - 1);
        } else {
            // 如果找到了，找右边比当前数字大的最小数字，交换，然后右边逆序
            for (int i = length - 1; i > reverseIndex; i--) {
                if (nums[i] > nums[reverseIndex]) {
                    swap(nums, reverseIndex, i);
                    break;
                }
            }
            reverse(nums, reverseIndex + 1, length - 1);
        }
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left++, right--);
        }
    }

    public static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
