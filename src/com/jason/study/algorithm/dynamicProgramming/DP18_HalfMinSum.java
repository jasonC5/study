package com.jason.study.algorithm.dynamicProgramming;

/**
 * 给定一个正数数组arr，
 * 请把arr中所有的数分成两个集合，尽量让两个集合的累加和接近
 * 返回：
 * 最接近的情况下，较小集合的累加和
 *
 * @author JasonC5
 */
public class DP18_HalfMinSum {
    public static void main(String[] args) {
        int[] arr = {100,100};
        System.out.println(method1(arr));
    }

    public static int method1(int[] arr) {
        int length = arr.length;
        long sum = 0;
        for (int i = 0; i < length; i++) {
            sum += arr[i];
        }
        //一半
        sum >>= 1;
        return process(arr, 0, sum);
    }

    /**
     * 从idx往后拼够rest，但是不能超过
     *
     * @param arr
     * @param idx
     * @param rest
     * @return
     */
    private static int process(int[] arr, int idx, long rest) {
        int length = arr.length;
        if (idx == length || rest == 0) {
            return 0;
        }
        int p1 = process(arr, idx + 1, rest);
        int p2 = 0;
        if (arr[idx] <= rest) {
            p2 = arr[idx] + process(arr, idx + 1, rest - arr[idx]);
        }
        return Math.max(p1, p2);
    }

}
