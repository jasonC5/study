package com.jason.study.algorithmQuestions.class02;

/**
 * 给定一个数组arr，只能对arr中的一个子数组排序，
 * 但是想让arr整体都有序
 * 返回满足这一设定的子数组中，最短的是多长
 * --遍历两遍，记录两个值，得到结果
 *
 * @author JasonC5
 */
public class MinLengthForSort {

    public static void main(String[] args) {
        int[] arr = {1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19};
        System.out.println(getMinLength(arr));
    }

    private static int getMinLength(int[] arr) {
        int length = arr.length;
        int left = -1;
        int right = -1;
        //从左到右应该递增，如果没有增，记录右边的位置
        int preMax = arr[0];
        for (int i = 1; i < length; i++) {
            if (arr[i] < preMax) {
                right = i;
            } else {
                preMax = arr[i];
            }
        }
        //全部递增，不需要改变
        if (right == -1) {
            return 0;
        }
        //从右往左，应该递减，如果没有减，记录最左边的位置
        int postMin = arr[length - 1];
        for (int i = length - 2; i >= 0; i--) {
            if (arr[i] > postMin) {
                left = i;
            } else {
                postMin = arr[i];
            }
        }
        return right - left + 1;
    }
}
