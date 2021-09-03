package com.jason.study.algorithmQuestions.class38;

import java.util.ArrayList;
import java.util.List;

/**
 * 给你一个含 n 个整数的数组 nums ，其中 nums[i] 在区间 [1, n] 内。请你找出所有在 [1, n] 范围内但没有出现在 nums 中的数字，并以数组的形式返回结果。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [4,3,2,7,8,2,3,1]
 * 输出：[5,6]
 * 示例 2：
 * <p>
 * 输入：nums = [1,1]
 * 输出：[2]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-all-numbers-disappeared-in-an-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC448 {
    // 标记
    public List<Integer> findDisappearedNumbers1(int[] nums) {
        int[] mark = new int[nums.length];
        for (int num : nums) {
            mark[num - 1]++;
        }
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (mark[i] == 0) {
                ans.add(i + 1);
            }
        }
        return ans;
    }

    // 下标循环怼
    public static List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return ans;
        }
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            go(nums, i);
        }
        for (int i = 0; i < length; i++) {
            if (i + 1 != nums[i]) {
                ans.add(i + 1);
            }
        }
        return ans;
    }

    private static void go(int[] nums, int i) {
        while (nums[i] != i + 1) {
            if (nums[nums[i] - 1] == nums[i]) {
                break;
            }
            swap(nums, i, nums[i] - 1);
        }
    }

    public static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
        System.out.println(findDisappearedNumbers(nums));
    }
}
