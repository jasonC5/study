package com.jason.study.algorithmQuestions.class03;

import java.util.Arrays;

/**
 * 给定一个正数数组arr，代表若干人的体重
 * 再给定一个正数limit，表示所有船共同拥有的载重量
 * 每艘船最多坐两人，且不能超过载重
 * 想让所有的人同时过河，并且用最好的分配方法让船尽量少
 * 返回最少的船数
 * --先排序
 * --贪心
 *
 * @author JasonC5
 */
public class MinBoat {

    public static void main(String[] args) {
        int[] arr = {5,1,4,2};
        int limit = 6;

        System.out.println(minBoat(arr, limit));
        System.out.println(minBoat2(arr, limit));
    }

    public static int minBoat(int[] arr, int limit) {
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


    public static int minBoat2(int[] arr, int limit) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        Arrays.sort(arr);
        if (arr[N - 1] > limit) {
            return -1;
        }
        int lessR = -1;
        for (int i = N - 1; i >= 0; i--) {
            if (arr[i] <= (limit / 2)) {
                lessR = i;
                break;
            }
        }
        if (lessR == -1) {
            return N;
        }
        int L = lessR;
        int R = lessR + 1;
        int noUsed = 0;
        while (L >= 0) {
            int solved = 0;
            while (R < N && arr[L] + arr[R] <= limit) {
                R++;
                solved++;
            }
            if (solved == 0) {
                noUsed++;
                L--;
            } else {
                L = Math.max(-1, L - solved);
            }
        }
        int all = lessR + 1;
        int used = all - noUsed;
        int moreUnsolved = (N - all) - used;
        return used + ((noUsed + 1) >> 1) + moreUnsolved;
    }
}
