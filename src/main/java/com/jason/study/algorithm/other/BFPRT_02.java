package com.jason.study.algorithm.other;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 求第K小的问题
 * <p>
 * 给定一个无序数组arr中，长度为N，给定一个正数k，返回top k个最大的数【排好序返回】
 * 不同时间复杂度三个方法：
 * 1）O(N*logN)			排序
 * 2）O(N + K*logN)		堆
 * 3）O(n + k*logk)		bfprt
 *
 * @author JasonC5
 */
public class BFPRT_02 {


    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            // [-? , +?]
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 生成随机数组测试
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("start");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;

            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);

            int[] ans1 = maxTopK1(arr1, k);
            int[] ans2 = maxTopK2(arr2, k);
            int[] ans3 = maxTopK3(arr3, k);
            if (!isEqual(ans1, ans2) || !isEqual(ans1, ans3)) {
                System.out.println("出错了！");
                printArray(ans1);
                printArray(ans2);
                printArray(ans3);
                return;
            }
        }
        System.out.println("completed");
    }

    //改写快排，再排序O(N + k*logk)
    private static int[] maxTopK3(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        //改写快排，获取第n-k小的数
        int n = arr.length;
        int[] ans = new int[k];
        int idx = 0;
        int num = minKth(arr, n - k);
        //找出所有比临界值大的
        for (int i = 0; i < n; i++) {
            if (arr[i] > num) {
                ans[idx++] = arr[i];
            }
        }
        //剩下的填充=临界值的
        for (; idx < k; idx++) {
            ans[idx] = num;
        }
        //排序
        ans = Arrays.stream(ans).boxed().sorted((i1, i2) -> i2 - i1).mapToInt(Integer::valueOf).toArray();
        return ans;
    }

    private static int minKth(int[] arr, int index) {
        //两个
        int L = 0;
        int R = arr.length - 1;
        int pivot = 0;
        int[] range = null;
        while (L < R) {
            //随机位置的值
            pivot = arr[L + (int) (Math.random() * (R - L + 1))];
            //快速排序的扩过程，返回指定数字的范围
            range = partition(arr, L, R, pivot);
            //只需要范围内的一半，另外一半不用处理，若指定index上就是指定数组，则直接返回数字，否则取大于的一边或者小于的一边继续处理
            if (index < range[0]) {
                R = range[0] - 1;
            } else if (index > range[1]) {
                L = range[1] + 1;
            } else {
                return pivot;
            }
        }
        return arr[L];
    }
    //荷兰国旗问题2.0
    //小于 pivot 的在左边，等于pivot的在中间，大于pivot的在右边，返回pivot的区间
    public static int[] partition(int[] arr, int L, int R, int pivot) {
        //小于的边界
        int less = L - 1;
        //大于的边界
        int more = R + 1;
        //从左边到右
        int cur = L;
        while (cur < more) {
            if (arr[cur] < pivot) {//如果当前位置小于目标值，与左边界交换，左边界+1，下跳下一个
                swap(arr, ++less, cur++);
            } else if (arr[cur] > pivot) { //如果当前位置大于目标值，与右边界交换，右边界-1，不跳
                swap(arr, cur, --more);
            } else { //若相等，两个边界指针不变，扫描指针跳下一个
                cur++;
            }
        }
        //返回指定数字的范围
        return new int[] { less + 1, more - 1 };
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }



    //O(N + K*logN)
    private static int[] maxTopK2(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int[] ans = new int[k];
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>((i1, i2) -> i2 - i1);
        for (int i : arr) {
            heap.offer(i);
        }
        for (int i = 0; i < k; i++) {
            ans[i] = heap.poll();
        }
        return ans;
    }

    //O(N*logN)
    private static int[] maxTopK1(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int[] ans = new int[k];
        int length = arr.length;
        Arrays.sort(arr);
        for (int i = 0; i < k; i++) {
            ans[i] = arr[length - i - 1];
        }
        return ans;
    }

}
