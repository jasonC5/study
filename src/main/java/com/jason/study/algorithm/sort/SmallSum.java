package com.jason.study.algorithm.sort;

/**
 * 小和问题
 * <p>
 * 一个数组中，数组中左边比自己小的数的总和的数组和
 *
 * @author JasonC5
 */
public class SmallSum {


    public static void main(String[] args) {
        int[] a = {85,52,74,62,28,74};
        System.out.println(smallSumByMergeSort(a, 0, a.length - 1));
        int times = 1000;
        for (int i = 0; i < times; i++) {
            int maxLength = 10;
            int maxVal = 100;
            int[] array = InsertSort.buildArray(maxLength, maxVal);
            int[] array1 = InsertSort.copyArray(array);
            int[] array2 = InsertSort.copyArray(array);
//            System.out.println(MergeSort.print(array));
            //对数器
            long smallSum1 = ordinaryFunction(array1);
//        System.out.println(smallSum1);
            //归并排序法解：问题转换，左边有多少个比自己小的数的总和，右边有多少个数比自己大，就加多少个自己，
            // 归并排序过程中自己先插入了，右边有多少个比自己大的
            long smallSum2 = smallSumByMergeSort(array2, 0, array2.length - 1);
//        System.out.println(smallSum2);
            if (smallSum1 != smallSum2) {
                System.out.println("failed, arr=" + MergeSort.print(array) + ",smallSum1=" + smallSum1 + ",smallSum2=" + smallSum2);
            }
        }
        System.out.println("complete!!!");
    }

    private static long smallSumByMergeSort(int[] array, int left, int right) {
        if (array == null || array.length < 2 || left == right) {
            return 0;
        }
        int mid = left + ((right - left) >> 1);
        return smallSumByMergeSort(array, left, mid) + smallSumByMergeSort(array, mid + 1, right) + merge(array, left, mid, right);
    }

    private static long merge(int[] arr, int left, int mid, int right) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        //合并的过程中，计算小和，只有左半拼装的时候才相加
        int sum = 0;
        int[] tmp = new int[right - left + 1];
        int leftPoint = left, rightPoint = mid + 1, tempPoint = 0;
        while (leftPoint <= mid && rightPoint <= right) {
            if (arr[leftPoint] < arr[rightPoint]) {//复制左边的时候
                sum += arr[leftPoint] * (right - rightPoint + 1);//右边有几个比自己大的,就加几个自己
            }
            tmp[tempPoint++] = arr[leftPoint] < arr[rightPoint] ? arr[leftPoint++] : arr[rightPoint++];//只能是<  <= 的时候结果不对
        }
        while (leftPoint <= mid) {
//            sum += arr[leftPoint] * (right - rightPoint + 1);  //这里 rightPoint 必定 = right + 1
            tmp[tempPoint++] = arr[leftPoint++];
        }
        while (rightPoint <= right) {
            tmp[tempPoint++] = arr[rightPoint++];
        }
        //放回到对应位置
        for (int i = 0; i < right - left + 1; i++) {
            arr[left + i] = tmp[i];
        }

        return sum;
    }

    //双重训话解，当对数器
    private static long ordinaryFunction(int[] array) {
        int length = array.length;
        long sum = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < i; j++) {
                if (array[i] > array[j]) {
                    sum += array[j];
                }
            }
        }
        return sum;
    }
}
