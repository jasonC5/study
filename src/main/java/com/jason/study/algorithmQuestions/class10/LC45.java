package com.jason.study.algorithmQuestions.class10;

public class LC45 {
    public int jump(int[] nums) {
        int step = 0, cur = 0, next = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > cur) {
                step++;
                cur = next;
            }
            next = Math.max(next, i + nums[i]);
        }
        return step;
    }
}
