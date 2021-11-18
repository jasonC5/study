package com.jason.study.algorithmQuestions.class34;

/**
 * 给定一个包含n + 1 个整数的数组nums ，其数字都在 1 到 n之间（包括 1 和 n），可知至少存在一个重复的整数。
 * <p>
 * 假设 nums 只有 一个重复的整数 ，找出 这个重复的数 。
 * <p>
 * 你设计的解决方案必须不修改数组 nums 且只用常量级 O(1) 的额外空间。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,3,4,2,2]
 * 输出：2
 * 示例 2：
 * <p>
 * 输入：nums = [3,1,3,4,2]
 * 输出：3
 * 示例 3：
 * <p>
 * 输入：nums = [1,1]
 * 输出：1
 * 示例 4：
 * <p>
 * 输入：nums = [1,1,2]
 * 输出：1
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-the-duplicate-number
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC287 {
    public static void main(String[] args) {
        int[] nums = new int[]{3, 1, 3, 4, 2};
        System.out.println(findDuplicate(nums));
    }

    public static int findDuplicate(int[] nums) {
        int slow = nums[0];
        int fast = nums[nums[0]];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        fast = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

}
