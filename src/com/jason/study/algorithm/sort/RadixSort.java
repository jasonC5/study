package com.jason.study.algorithm.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 基数排序
 *
 * @author JasonC5
 */
public class RadixSort {

    public static void main(String[] args) {
//        int[] arr = {101, 2, 303, 4, 10, 999, 1314, 447};
//        int maxBits = getMaxBits(arr);
//        sortByBucket(arr, 0, arr.length - 1, maxBits);
////        2,4,10,101,303,447,999,1314
//        sortHaveNoBucket(arr, 0, arr.length - 1, maxBits);
////        2,4,10,101,303,447,999,1314
//        System.out.println(MergeSort.print(arr));
        int times = 10_000;
        int maxLength = 100;
        int maxVal = 999999;
        for (int i = 0; i < times; i++) {
            //产生随机参数
            int[] x = InsertSort.buildArray(maxLength, maxVal);
            //copy两份
            int[] a = InsertSort.copyArray(x);
            int[] b = InsertSort.copyArray(x);
            sortByBucket(a, 0, a.length - 1, getMaxBits(a));
//            sortHaveNoBucket(b, 0, b.length - 1, getMaxBits(b));
            InsertSort.insertSort(b);//插入排序
            if (!InsertSort.compare(a,b)){
                System.out.println("failed");
                System.out.println("a="+ MergeSort.print(a));
                System.out.println("b="+ MergeSort.print(b));
                return;
            }
        }
        System.out.println("compete!!!");
    }


    private static int getMaxBits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = Math.max(max, i);
        }
        int ans = 0;
        while (max > 0) {
            max /= 10;
            ans++;
        }
        return ans;
    }

    //桶版本
    private static void sortByBucket(int[] arr, int left, int right, int maxBits) {
        //准备10个桶
        List<Integer>[] lists = new List[10];
        int mask = 1;
        for (int i = 0; i < maxBits; i++) {
            //清空桶
            for (int j = 0; j < 10; j++) {
                lists[j] = new ArrayList<Integer>();
            }
            for (int j = left; j <= right; j++) {
                int temp = arr[j];
                int k = temp % (mask * 10);
                int bucket = k / mask;
                lists[bucket].add(arr[j]);
            }
            int index = left;
            for (List<Integer> list : lists) {
                if (list.size() > 0) {
                    for (Integer value : list) {
                        arr[index++] = value;
                    }
                }
            }
            mask *= 10;
        }
    }

    //无桶优化
    private static void sortHaveNoBucket(int[] arr, int left, int right, int maxBits) {
        int mask = 1;
        for (int i = 0; i < maxBits; i++) {
            //进桶，出桶，无非是为了让相应位置上的数字出现在对应位置上
            //计算出1有几个，2有几个，从右边往左放入，即可
            int[] fakeBucket = new int[10];
            int[] tempArr = new int[arr.length];//复制出来一份，下面需要
            for (int j = left; j <= right; j++) {
                tempArr[j] = arr[j];
                int temp = arr[j];
                int k = temp % (mask * 10);
                int bucket = k / mask;
                fakeBucket[bucket]++;//相应位置上+1
            }
            int[] pointer = new int[10];
            pointer[0] = fakeBucket[0];
            for (int j = 1; j < 10; j++) {
                pointer[j] = pointer[j - 1] + fakeBucket[j];
            }
            //pointer 每个位置上，记录着，以几结尾的数字，该存放的最右边的位置
            for (int j = right; j >= left; j--) {
                int temp = tempArr[j];
                int k = temp % (mask * 10);
                int bucket = k / mask;
                //找到该存在的地方，然后指针减一
                int seek = pointer[bucket]--;
                //放如找到的位置
                arr[seek-1] = temp;
            }
            mask *= 10;
        }
    }
}
