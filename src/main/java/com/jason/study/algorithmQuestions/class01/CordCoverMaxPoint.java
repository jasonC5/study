package com.jason.study.algorithmQuestions.class01;

import java.util.Arrays;

/**
 * 给定一个有序数组arr，代表坐落在X轴上的点
 * 给定一个正数K，代表绳子的长度
 * 返回绳子最多压中几个点？
 * 即使绳子边缘处盖住点也算盖住
 *
 * @author JasonC5
 */
public class CordCoverMaxPoint {

    public static void main(String[] args) {
        int times = 1000;
        int maxLen = 100;
        int max = 100;
        System.out.println("start");
        for (int i = 0; i < times; i++) {
            int[] arr = genArr(max, maxLen);
            int k = (int) (Math.random() * max);
            if (maxPoint1(arr, k) != maxPoint2(arr, k)) {
                System.out.println("failed");
                return;
            }
        }
        System.out.println("end");
    }

    private static int[] genArr(int max, int maxLen) {
        int length = (int) (Math.random() * maxLen);
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int) (Math.random() * max);
        }
        Arrays.sort(arr);
        return arr;
    }

//    public static int maxPoint1(int[] arr, int k) {
//        int length = arr.length;
//        int ans = 1;
//        for (int i = 0; i < length; i++) {
//            int end = arr[i] + k;
//            int t = find(arr, i, end);
//            ans = Math.max(ans, t);
//        }
//        return ans;
//    }

//    private static int find(int[] arr, int i, int end) {
//        int ans = 1;
//        for (int j = i + 1; j < arr.length; j++) {
//            if (arr[j] <= end) {
//                i++;
//            } else {
//                break;
//            }
//        }
//        return ans;
//    }

    public static int maxPoint1(int[] arr, int L) {
        int res = 1;
        for (int i = 0; i < arr.length; i++) {
            int nearest = nearestIndex(arr, i, arr[i] - L);
            res = Math.max(res, i - nearest + 1);
        }
        return res;
    }

    public static int nearestIndex(int[] arr, int R, int value) {
        int L = 0;
        int index = R;
        while (L <= R) {
            int mid = L + ((R - L) >> 1);
            if (arr[mid] >= value) {
                index = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return index;
    }

    public static int maxPoint2(int[] arr, int k) {
        int left = 0;
        int right = 1;
        int ans = 1;
        while (left < arr.length) {
            while (right < arr.length && arr[right] - arr[left] <= k) {
                right++;
            }
            ans = Math.max(ans, right - left++);
        }
        return ans;
    }


}
