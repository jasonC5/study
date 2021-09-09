package com.jason.study.algorithmQuestions.class07;

import java.util.Arrays;

/**
 * 给定一个非负数组成的数组，长度一定大于1
 * 想知道数组中哪两个数&的结果最大
 * 返回这个最大结果
 * 时间复杂度O(N) ，额外空间复杂度O(1)
 * --按二进制位 遍历
 * --交换，缩窗口
 *
 * @author JasonC5
 */
public class MaxAnd {

    // 暴力解 O(n^2)
    public static int maxAndValue1(int[] arr) {
        int length = arr.length;
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < length; i++) {
            for (int j = 0; j < i; j++) {
                max = Math.max(max, arr[i] & arr[j]);
            }
        }
        return max;
    }

    //O(n) 按二进制位来，从第31位开始，有1的num==2，直接返回，有1的num<2不缩，有1的num>2，把为0的置换出窗口，缩
    public static int maxAndValue2(int[] arr) {
        int win = arr.length;//有效数字窗口
        for (int bit = 30; bit >= 0; bit--) {
            int i = 0;
            int tmp = win;
            while (i < tmp) {
                if (((arr[i] >> bit) & 1) == 1) {
                    i++;
                } else {
                    //换
                    swap(arr, i, --tmp);
                }
            }
            if (tmp == 2) {
                return arr[0] & arr[1];
            } else if (tmp > 2) {
                // 缩小有效窗口
                win = tmp;
            } else {
                // 啥也不干，下一个
            }
        }
        return arr[0] & arr[1];
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    public static int[] randomArray(int size, int range) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * range) + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
        int maxSize = 50;
        int range = 30;
        int testTime = 1000000;
        System.out.println("start");
        for (int i = 0; i < testTime; i++) {
            int size = (int) (Math.random() * maxSize) + 2;
            int[] arr = randomArray(size, range);
            int ans1 = maxAndValue1(arr);
            int ans2 = maxAndValue2(arr);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                Arrays.stream(arr).boxed().forEach(it-> System.out.print(it + "\t"));
                System.out.println();
                System.out.println(ans1);
                System.out.println(ans2);
            }
        }
        System.out.println("end");

    }
}
