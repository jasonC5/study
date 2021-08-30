package com.jason.study.algorithmQuestions.class36;

import java.util.Arrays;

/**
 * // 来自腾讯
 * // 给定一个正数数组arr，代表每个人的体重。给定一个正数limit代表船的载重，所有船都是同样的载重量
 * // 每个人的体重都一定不大于船的载重
 * // 要求：
 * // 1, 可以1个人单独一搜船
 * // 2, 一艘船如果坐2人，两个人的体重相加需要是偶数，且总体重不能超过船的载重
 * // 3, 一艘船最多坐2人
 * // 返回如果想所有人同时坐船，船的最小数量
 *
 * @author JasonC5
 */
public class Code09_MinBoatEvenNumbers {
    public static int minBoat(int[] arr, int limit) {
        //由于条件2，先把整个数组分为奇数数组和偶数数组，再直接调用之前的小船问题即可
        Arrays.sort(arr);
        int odd = 0;
        int even = 0;
        for (int num : arr) {
            if ((num & 1) == 0) {
                even++;
            } else {
                odd++;
            }
        }
        int[] odds = new int[odd];
        int[] evens = new int[even];
        for (int num : arr) {
            if ((num & 1) == 0) {
                evens[--even] = num;
            } else {
                odds[--odd] = num;
            }
        }
        return min(odds, limit) + min(evens, limit);
    }

    public static int min(int[] arr, int limit) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        Arrays.sort(arr);
        int length = arr.length;
        //一艘船运不了一个人，完不成的任务
        if (arr[length - 1] > limit) {
            return -1;
        }
        //右指针limit/2的右边位置、左指针指向limit/2左边的位置
        //左边指针往左移，能能满足右指针的时候打钩，不能则打×
        int lessRight = -1;
        for (int i = length - 1; i >= 0; i--) {
            if (arr[i] <= (limit >> 1)) {
                lessRight = i;
                break;
            }
        }
        //就没一个人在limit/2以下的，每个人一艘船
        if (lessRight == -1) {
            return length;
        }
        int left = lessRight;
        int right = lessRight + 1;
        //用了
        int noUsed = 0;
        while (left >= 0) {
            int solved = 0;
            while (right < length && arr[left] + arr[right] <= limit) {
                solved++;
                right++;
            }
            if (solved == 0) {
                left--;
                //左边打叉
                noUsed++;
            } else {
                left = Math.max(-1, left - solved);
            }
        }
        //左边有多少配对了【右边就有多少配对了】
        int picked = lessRight + 1 - noUsed;
        //左边还剩多少,两个人一艘船
        int leftRestCost = (noUsed + 1) >> 1;
        //右边还剩多少
        int rightRestRest = (length - lessRight - 1) - picked;
        return picked + leftRestCost + rightRestRest;
    }
}
