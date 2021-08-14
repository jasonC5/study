package com.jason.study.algorithmQuestions.class32;

import java.util.Arrays;

/**
 * // 给定一个数组arr，arr[i] = j，表示第i号试题的难度为j。给定一个非负数M
 * // 想出一张卷子，对于任何相邻的两道题目，前一题的难度不能超过后一题的难度+M
 * // 返回所有可能的卷子种数
 *
 * @author JasonC5
 */
public class SequenceM {


    // 纯暴力方法，生成所有排列，一个一个验证
    public static int ways1(int[] arr, int m) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0, m);
    }

    public static int process(int[] arr, int index, int m) {
        if (index == arr.length) {
            for (int i = 1; i < index; i++) {
                if (arr[i - 1] > arr[i] + m) {
                    return 0;
                }
            }
            return 1;
        }
        int ans = 0;
        for (int i = index; i < arr.length; i++) {
            swap(arr, index, i);
            ans += process(arr, index + 1, m);
            swap(arr, index, i);
        }
        return ans;
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 为了测试
    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * (value + 1));
        }
        return arr;
    }

    // 为了测试
    public static void main(String[] args) {
        int N = 10;
        int value = 20;
        int testTimes = 1000;
        System.out.println("start");
        for (int i = 0; i < testTimes; i++) {
            int len = (int) (Math.random() * (N + 1));
            int[] arr = randomArray(len, value);
            int m = (int) (Math.random() * (value + 1));
            int ans1 = ways1(arr, m);
            int ans2 = ways2(arr, m);
            int ans3 = ways3(arr, m);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("error!");
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
            }
        }
        System.out.println("end");
    }

    //二分
    //从左往右动态规划
    public static int ways2(int[] arr, int m) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 先排序，目的是不回退，
        // 【两个位置上的值不符合条件arr[i-1]>arr[i]+m，那么现在要加进去的值，比a[i-1]还大，这个值插进两个中间，并不能使这个结果满足条件，】
        // 所以前面有多少能成功的，自己加到最后就有多少能成功的
        // 有多少额外的能成功的？arr[i]一定比前面任何一个数字大，所以能放到任何一个数字的后面都能满足这个位置对前一个数字的条件
        // 能放到多少个数字的前面呢？arr[i] <= arr[x] + m  arr[x] >= arr[i]-m,通过二分或者indexTree
        Arrays.sort(arr);
        int db = 1;//1张卷子的时候，1个排列结果
        for (int i = 1; i < arr.length; i++) {
            //arr 中，0~i-1个元素中有多少个满足 arr[x] >= arr[i]-m
//            db = db + db * find(arr, i - 1, arr[i] - m);
            db = db * (find(arr, i - 1, arr[i] - m) + 1);
        }
        return db;
    }

    //先找到满足条件的最左位置，一减
    private static int find(int[] arr, int rightIndex, int moreThen) {
        int left = 0;
        int right = rightIndex;
        int pre = rightIndex + 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (arr[mid] >= moreThen) {
                pre = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return rightIndex - pre + 1;
    }

    //indexTree -- 从1开始
    public static int ways3(int[] arr, int m) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        //找到难度的最大最小值，建立indexTree，下标从1开始
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int num : arr) {
            max = Math.max(max, num);
            min = Math.min(min, num);
        }
        IndexTree indexTree = new IndexTree(max - min + 2);
        Arrays.sort(arr);
        int ans = 1;
        for (int i = 0; i < arr.length; i++) {
            //这张卷子对应的难度下标
            int hard = arr[i] - min + 1;
            // 有多少个满足 arr[x] >= arr[i] - m
            // 找到有多少个不满足的i一减就是
            // 多少个不满足的？难度在0~hard-m-1上的sum
            ans = ans + (ans * (i - indexTree.sum(hard - m - 1)));
            //这个难度的卷子+1
            indexTree.add(hard, 1);
        }
        return ans;
    }

    //IndexTree
    public static class IndexTree {
        private int[] nums;
        int length;

        public IndexTree(int length) {
            this.length = length;
            nums = new int[length + 1];
        }

        public void add(int index, int add) {
            while (index <= length) {
                nums[index] += add;
                index += index & -index;
            }
        }

        public int sum(int index) {
            int ans = 0;
            while (index > 0) {
                ans += nums[index];
                index -= index & -index;
            }
            return ans;
        }
    }
}
