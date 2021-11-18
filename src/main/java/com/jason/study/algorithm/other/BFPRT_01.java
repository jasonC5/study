package com.jason.study.algorithm.other;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * bfprt 求第k大问题
 * 快排思路--改写
 * 随机选数改为bfprt
 *
 * @author JasonC5
 */
public class BFPRT_01 {

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // 利用大根堆，时间复杂度 O(N*logK)--堆内最多只有k个数
    public static int minKth1(int[] arr, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>((o1, o2) -> o2 - o1);
        for (int i = 0; i < k; i++) {
            maxHeap.add(arr[i]);
        }
        for (int i = k; i < arr.length; i++) {
            if (arr[i] < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.add(arr[i]);
            }
        }
        return maxHeap.peek();
    }

    // 小根堆，全放进去，再拿出来 O(n+k*logn)
    public static int minKth2(int[] arr, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
        for (int i = 0; i < arr.length; i++) {
            minHeap.add(arr[i]);
        }
        for (int i = 1; i < k; i++) {
            minHeap.poll();
        }
        return minHeap.poll();
    }

    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i != ans.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            //大根堆，O(N*logK)
            int[] arr1 = copyArray(arr);
            int ans1 = minKth1(arr1, k);
            //小根堆，O(N + k*logN)
            int[] arr2 = copyArray(arr);
            int ans2 = minKth2(arr2, k);
            //改写快速排序
            int[] arr3 = copyArray(arr);
            int ans3 = minKth3(arr3, k);
            //bfprt
            int[] arr4 = copyArray(arr);
            int ans4 = minKth4(arr3, k);
            //bfprt

            if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
                System.out.println("Oops!");
            }
//            if (ans1 != ans2) {
//                System.out.println("failed");
//                return;
//            }
        }
        System.out.println("test finish");
    }

    //改写快排
    private static int minKth3(int[] arr, int k) {
        return process(arr, 0, arr.length - 1, k - 1);
    }

    private static int process(int[] arr, int left, int right, int idx) {
        if (left == right) {
            return arr[left];
        }
        int randomIdx = (int) (Math.random() * (right - left + 1)) + left;
        int random = arr[randomIdx];
        //range[0] = left，range[0] = right
        int[] range = partition(arr, left, right, random);
        if (range[0] > idx) {
            right = range[0] - 1;
        } else if (range[1] < idx) {
            left = range[1] + 1;
        } else {
            return random;
        }
        return process(arr, left, right, idx);
//        if (idx >= range[0] && idx <= range[1]) {
//            return arr[idx];
//        } else if (idx < range[0]) {
//            return process(arr, left, range[0] - 1, idx);
//        } else {
//            return process(arr, range[1] + 1, right, idx);
//        }
    }

    private static int[] partition(int[] arr, int left, int right, int num) {
        int l = left - 1;
        int r = right + 1;
        int cur = left;
        while (cur < r) {
            if (arr[cur] < num) {
                swap(arr, cur++, ++l);
            } else if (arr[cur] > num) {
                swap(arr, cur, --r);
            } else {
                cur++;
            }
        }
        return new int[]{l + 1, r - 1};
        //等于的区间
    }

    private static void swap(int[] arr, int i, int j) {
        if (i != j) {
            arr[i] = arr[i] ^ arr[j];
            arr[j] = arr[i] ^ arr[j];
            arr[i] = arr[i] ^ arr[j];
        }
//        int tmp = arr[i];
//        arr[i] = arr[j];
//        arr[j] = tmp;
    }

    //bfprt
    private static int minKth4(int[] arr, int k) {
        return bfprt(arr, 0, arr.length - 1, k - 1);
    }

    //bfprt
    private static int bfprt(int[] arr, int left, int right, int idx) {
        if (left == right) {
            return arr[left];
        }
        // L...R  每五个数一组
        // 每一个小组内部排好序
        // 小组的中位数组成新数组
        // 这个新数组的中位数返回
        int pivot = medianOfMedians(arr, left, right);
//        int randomIdx = (int) (Math.random() * (right - left + 1)) + left;
//        int random = arr[p];
        //range[0] = left，range[0] = right
        int[] range = partition(arr, left, right, pivot);
        if (range[0] > idx) {
            right = range[0] - 1;
        } else if (range[1] < idx) {
            left = range[1] + 1;
        } else {
            return pivot;
        }
        return process(arr, left, right, idx);
    }

    /**
     * // L...R  每五个数一组
     * // 每一个小组内部排好序
     * // 小组的中位数组成新数组
     * // 这个新数组的中位数返回
     *
     * @param arr
     * @param left
     * @param right
     * @return
     */
    private static int medianOfMedians(int[] arr, int left, int right) {
        int size = right - left + 1;
        int[] midArr = new int[size / 5 + ((size % 5 == 0) ? 0 : 1)];
        for (int i = 0; i < midArr.length; i++) {
            int l = left + i * 5;
            //局部排序，取中位数
            midArr[i] = getMid(arr, l, Math.min(l + 4, right));
        }
        //找到中位数数组的中位数
        return bfprt(midArr, 0, midArr.length - 1, midArr.length / 2);
    }

    private static int getMid(int[] arr, int l, int r) {
        //局部排序再返回中位数
        sort(arr, l, r);
        return arr[l + (r - l) >> 1];
    }

    //插入排序
    private static void sort(int[] arr, int l, int r) {
        for (int i = l + 1; i < r; i++) {
            //arr[j + 1] > arr[j] 这里的大于小于不影响最终的结果，只是取中位数而已
            for (int j = i - 1; j >= l && arr[j + 1] < arr[j]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

}
