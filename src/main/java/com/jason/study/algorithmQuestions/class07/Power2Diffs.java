package com.jason.study.algorithmQuestions.class07;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 给定一个有序数组arr，其中值可能为正、负、0
 * 返回arr中每个数都平方之后不同的结果有多少种？		--绝对值相同
 * <p>
 * 给定一个数组arr，先递减然后递增，
 * 返回arr中有多少个不同的数字？
 * --双指针，往中间移动，谁大谁往中间走（相等的一次性划过），相等了一起走，走一步答案+1；
 *
 * @author JasonC5
 */
public class Power2Diffs {

    // for test
    public static int[] randomSortedArray(int len, int value) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value) - (int) (Math.random() * value);
        }
        Arrays.sort(ans);
        return ans;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int cur : arr) {
            System.out.print(cur + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 100;
        int value = 500;
        int testTimes = 200000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = randomSortedArray(len, value);
            int ans1 = diff1(arr);
            int ans2 = diff2(arr);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("test finish");
    }

    // 暴力解
    private static int diff1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        HashSet<Integer> set = new HashSet<>();
        for (int num : arr) {
            set.add(num * num);
        }
        return set.size();
    }

    // 双指针法
    private static int diff2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int length = arr.length;
        int left = 0, right = length - 1;
        int ans = 0;
        while (left <= right) {
            ans++;
            int leftAbs = Math.abs(arr[left]);
            int rightAbs = Math.abs(arr[right]);
            if (leftAbs > rightAbs) {
                while (left < length && Math.abs(arr[left]) == leftAbs) {
                    left++;
                }
            } else if (leftAbs < rightAbs) {
                while (right >= 0 && Math.abs(arr[right]) == rightAbs) {
                    right--;
                }
            } else {
                while (left < length && Math.abs(arr[left]) == leftAbs) {
                    left++;
                }
                while (right >= 0 && Math.abs(arr[right]) == rightAbs) {
                    right--;
                }
            }
        }
        return ans;
    }

}
