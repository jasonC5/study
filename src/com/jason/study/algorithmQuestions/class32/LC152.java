package com.jason.study.algorithmQuestions.class32;

public class LC152 {

    public static void main(String[] args) {
        int[] nums = {-4,-3,-2};
        System.out.println(maxProduct(nums));
    }

    public static int maxProduct(int[] nums) {
        int ans = nums[0];
        int max = nums[0];
        int min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            //1.乘前面的，2.不乘前面的
            int curMax = Math.max(Math.max(nums[i] * max, nums[i] * min), nums[i]);
            min = Math.min(Math.min(nums[i] * max, nums[i] * min), nums[i]);
            max = curMax;
            ans = Math.max(max, ans);
        }
        return ans;
    }

}
