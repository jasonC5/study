package com.jason.study.algorithm.windowMaxMin;

import java.util.LinkedList;

/**
 *
 * 给定一个整型数组arr，和一个整数num
 * 某个arr中的子数组sub，如果想达标，必须满足：
 * sub中最大值 – sub中最小值 <= num，
 * 返回arr中达标子数组的数量
 *
 * @author JasonC5
 */
public class MinSub {


    // for test
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    // 暴力的对数器方法
    public static int right(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num < 0) {
            return 0;
        }
        int N = arr.length;
        int count = 0;
        for (int L = 0; L < N; L++) {
            for (int R = L; R < N; R++) {
                int max = arr[L];
                int min = arr[L];
                for (int i = L + 1; i <= R; i++) {
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }
                if (max - min <= num) {
                    count++;
                }
            }
        }
        return count;
    }


    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 200;
        int testTime = 100000;
        System.out.println("start");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int sum = (int) (Math.random() * (maxValue + 1));
            int ans1 = right(arr, sum);
            int ans2 = num(arr, sum);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(sum);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("complete");

    }

    private static int num(int[] arr, int num) {
        int N = arr.length;
        LinkedList<Integer> maxWindow = new LinkedList<>();
        LinkedList<Integer> minWindow = new LinkedList<>();
        int ans = 0;
        //右边界
        int R = 0;
        //左边界
        for (int L = 0; L < arr.length; L++) {
            //右边界右滑，滑不动了【max-min > sum】记录个数，左边界滑一个，继续右边界滑动
            while (R < N) {
                //放到两个队列中
                while(!maxWindow.isEmpty() && arr[R] >= arr[maxWindow.peekLast()]){
                    maxWindow.pollLast();
                }
                maxWindow.add(R);
                while(!minWindow.isEmpty() && arr[R] <= arr[minWindow.peekLast()]){
                    minWindow.pollLast();
                }
                minWindow.add(R);
                //滑过了，滑不动了，跳出
                if (arr[maxWindow.peekFirst()] - arr[minWindow.peekFirst()] > num) {
                    break;
                } else {
                    R++;
                }
            }
            //此时L~R中的所有位置，到R为止的子数组（左闭右开），都满足答案
            ans += R- L;
            //该移动左窗口了，踢出去
            if (maxWindow.peekFirst() == L) {
                maxWindow.pollFirst();
            }
            if (minWindow.peekFirst() == L) {
                minWindow.pollFirst();
            }
        }
        return ans;
    }

}
