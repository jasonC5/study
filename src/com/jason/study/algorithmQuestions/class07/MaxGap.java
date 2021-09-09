package com.jason.study.algorithmQuestions.class07;

import java.util.Arrays;

/**
 * 3.给定一个数组arr，
 * 返回如果排序之后，相邻两数的最大差值
 * 要求：时间复杂度O(N)
 * --桶排序思想
 * --假设答案法	--只要出现就是难题
 * --鸽笼原理，空降一个答案
 *
 * @author JasonC5
 */
public class MaxGap {

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (maxGap(arr1) != comparator(arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }


    // 暴力解
    public static int comparator(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        Arrays.sort(nums);
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < nums.length; i++) {
            max = Math.max(max, nums[i] - nums[i - 1]);
        }
        return max;
    }

    // 利用桶排序的思想
    public static int maxGap(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int length = nums.length;
        // 先找出最大最小值
        int maxVal = nums[0], minVal = nums[0];
        for (int num : nums) {
            maxVal = Math.max(maxVal, num);
            minVal = Math.min(minVal, num);
        }
        if (maxVal == minVal) {// 整个数组，都是一个数
            return 0;
        }
        // 分桶：最大最小值之间，有多少个数，就分n+1个桶（length+1？）
        boolean[] has = new boolean[length + 1];
        int[] min = new int[length + 1];
        int[] max = new int[length + 1];
        for (int i = 0; i < length; i++) {
            int bucket = findBucket(nums[i], minVal, maxVal, length);
            min[bucket] = has[bucket] ? Math.min(min[bucket], nums[i]) : nums[i];
            max[bucket] = has[bucket] ? Math.max(max[bucket], nums[i]) : nums[i];
            has[bucket] = true;
        }
        int ans = 0;
        int lastMax = max[0];
        for (int i = 1; i <= length; i++) {
            if (has[i]) {
                ans = Math.max(ans, min[i] - lastMax);
                lastMax = max[i];
            }
        }
        return ans;
    }

    private static int findBucket(int num, int minVal, int maxVal, int length) {
        return (int) ((num - minVal) * length / (maxVal - minVal));
    }


}
