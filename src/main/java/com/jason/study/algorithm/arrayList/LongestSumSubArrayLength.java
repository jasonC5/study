package com.jason.study.algorithm.arrayList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 给定一个整数组成的无序数组arr，值可能正、可能负、可能0，给定一个整数值K
 * 找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
 * 返回其长度		--没有单调性了
 * --看到子数组，把流程定为：以子数组必须以i为结尾的情况下
 * --累加和数组、前缀和索引最早出现、（0，-1）初始索引	--预处理
 *
 * @author JasonC5
 */
public class LongestSumSubArrayLength {

//    public static int fun1(int[] arr, int k){
//
//    }


    // for test
    public static int right(int[] arr, int K) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (valid(arr, i, j, K)) {
                    max = Math.max(max, j - i + 1);
                }
            }
        }
        return max;
    }

    // for test
    public static boolean valid(int[] arr, int L, int R, int K) {
        int sum = 0;
        for (int i = L; i <= R; i++) {
            sum += arr[i];
        }
        return sum == K;
    }

    // for test
    public static int[] generateRandomArray(int size, int value) {
        int[] ans = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value) - (int) (Math.random() * value);
        }
        return ans;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 50;
        int value = 100;
        int testTime = 500000;

        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(len, value);
            int K = (int) (Math.random() * value) - (int) (Math.random() * value);
            int ans1 = maxLength(arr, K);
            int ans2 = right(arr, K);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println("K : " + K);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("test end");

    }

    private static int maxLength(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        //值可能正、可能负、可能0，给定一个整数值K  找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的  返回其长度
//        看到子数组，把流程定为：以子数组必须以i为结尾的情况下
        int length = arr.length;
        int sum = 0;
        int max = 0;
        //前缀和哈希表
        HashMap<Integer, Integer> preSumIndexMap = new HashMap<>();
        preSumIndexMap.put(0, -1);
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            Integer left = null;
            if ((left = preSumIndexMap.get(sum - k)) != null) {
                max = Math.max(max, i - left);
            }
            preSumIndexMap.putIfAbsent(sum, i);
        }
        return max;
    }

    private static int maxLength2(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        //值可能正、可能负、可能0，给定一个整数值K  找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的  返回其长度
//        看到子数组，把流程定为：以子数组必须以i为结尾的情况下
        int sum = 0;
        int max = 0;
        //前缀和哈希表
        HashMap<Integer, Integer> preSumIndexMap = new HashMap<>();
        preSumIndexMap.put(0, -1);//左边界
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (preSumIndexMap.containsKey(sum - k)) {
                max = Math.max(max, i - preSumIndexMap.get(sum - k));
            }
            preSumIndexMap.putIfAbsent(sum, i);
        }
        return max;
    }

}
