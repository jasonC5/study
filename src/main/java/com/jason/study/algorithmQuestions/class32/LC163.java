package com.jason.study.algorithmQuestions.class32;

import java.util.ArrayList;
import java.util.List;

/**
 * 给一个有序数组，缺东西，返回缺的信息
 *
 * @author JasonC5
 */
public class LC163 {

    public static void main(String[] args) {
        int[] nums = {0, 1, 3, 50, 75};
        System.out.println(findMissingRanges(nums, 0, 99));
    }

    public static List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> ans = new ArrayList<>();
        int next = lower;
        for (int num : nums) {
            if (num != next) {
                ans.add(getString(next, num - 1));
            }
            next = num + 1;
        }
        if (next != upper - 1) {
            ans.add(getString(next, upper));
        }
        return ans;
    }

    private static String getString(int start, int end) {
        return start == end ? start + "" : start + "->" + end;
    }
}
