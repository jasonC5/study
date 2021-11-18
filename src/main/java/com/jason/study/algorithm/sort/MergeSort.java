package com.jason.study.algorithm.sort;


import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 归并排序：递归法+迭代法
 *
 * @author JasonC5
 */
public class MergeSort {

    //recursion
    public static void recursionMergeSort(int[] arr, int start, int end) {
        if (arr == null || arr.length < 2) {
            return;
        }
        //start 到 end 范围内排序
        if (start == end) {
            return;
        }
        int mid = start + ((end - start) >> 1);
        recursionMergeSort(arr, start, mid);
        recursionMergeSort(arr, mid + 1, end);
        merge(arr, start, mid, end);
    }

    private static void merge(int[] arr, int start, int mid, int end) {
        int[] tmp = new int[end-start+1];
        int leftPoint=start,rightPoint=mid+1,tempPoint=0;
        while (leftPoint <= mid && rightPoint<= end){
            tmp[tempPoint++] = arr[leftPoint] <= arr[rightPoint] ? arr[leftPoint++] : arr[rightPoint++];
        }
        while (leftPoint <= mid) {
            tmp[tempPoint++] =  arr[leftPoint++];
        }
        while (rightPoint <= end) {
            tmp[tempPoint++] =  arr[rightPoint++];
        }
        //放回到对应位置
        for (int i = 0; i < end - start + 1; i++) {
            arr[start+i] = tmp[i];
        }
    }


    //iteration  迭代法
    public static void iterationMergeSort(int[] arr){
        int tmp = 1;//步长内排序
        int length = arr.length;
        while (tmp < length){
            int point = 0;//从第0个位置开始排序
            while(point + tmp <= length){//最后一个左
                int mid = point + tmp - 1;
                int right = Math.min(mid + tmp, length-1);
                merge(arr, point,mid,right);
                point = right+1;
            }
            //防止溢出
            if (tmp > (length >>> 1)) {
                break;
            }
            tmp <<= 1;
        }
    }


    public static void main(String[] args) {
        int times = 1000;
        for (int i = 0; i < times; i++) {
            int maxLength = 10;
            int maxVal = 100;
            int[] array = InsertSort.buildArray(maxLength, maxVal);
//            Arrays.stream(array).boxed().forEach(x -> System.out.print(x + " "));
//            System.out.println();
            int[] a = InsertSort.copyArray(array);
            int[] b = InsertSort.copyArray(array);
            recursionMergeSort(a,0, array.length-1);//递归归并排序
//        Arrays.stream(a).boxed().forEach(x -> System.out.print(x + " "));
//        System.out.println();
            iterationMergeSort(b);//迭代归并排序
//        Arrays.stream(b).boxed().forEach(x -> System.out.print(x + " "));
//        System.out.println();
            if (!InsertSort.compare(a,b)) {
                System.out.println("failed, arr="+print(array));
                return;
            }
        }
        System.out.println("complete!!!");
    }

    public static String print(int[] array) {
        return Arrays.stream(array).boxed().map(x -> x + "").collect(Collectors.joining(","));
    }
}
