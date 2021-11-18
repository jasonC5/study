package com.jason.study.algorithm.sort;

import com.sun.scenario.effect.impl.sw.java.JSWColorAdjustPeer;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 快速排序3.0
 * @author JasonC5
 */
public class QuickSort {

    public static void main(String[] args) {
        //对数器
        int times = 1000;
        int maxLength = 100;
        int maxVal = 1000;
        for (int i = 0; i < times; i++) {
            //产生随机参数
            int[] x = InsertSort.buildArray(maxLength, maxVal);
            //copy两份
            int[] a = InsertSort.copyArray(x);
            int[] b = InsertSort.copyArray(x);

//        int[] arr = {1,6,3,7,2,4,1};
            quickSort(a,0, a.length-1);//快速排序
            InsertSort.insertSort(b);//插入排序
            if (!InsertSort.compare(a,b)){
                System.out.println("failed");
                System.out.println("a="+ MergeSort.print(a));
                System.out.println("b="+ MergeSort.print(b));
                return;
            }
        }
        System.out.println("compete!!!");
//        Arrays.stream(a).boxed().forEach(num-> System.out.print(num + ","));
    }

    public static void quickSort(int[] arr, int left, int right){
        if (arr == null || arr.length < 2) {
            return;
        }
        if (left >= right) {
            return;
        }
        //left到right中随机找到一个数，换到right再进行荷兰国旗问题
        int random = (int)Math.random() * (right - left + 1) + left;
        swap(arr, right, random);
        //得到random这个位置上对应的数字排好序之后对应的区间【荷兰国旗2.0问题】
        int[] section = partSort(arr, left, right);
        quickSort(arr, left, section[0] -1);
        quickSort(arr, section[1] + 1, right);
    }

    private static int[] partSort(int[] arr, int left, int right) {
        int checkNum = arr[right];
        int lessPoint = left-1;
        int checkPoint=left;
        int morePoint = right;
        //
        while (checkPoint < morePoint) {
            if (arr[checkPoint] < checkNum) {
                swap(arr, checkPoint++, ++lessPoint);
            } else if (arr[checkPoint] > checkNum) {
                swap(arr, checkPoint, --morePoint);
            } else {//arr[left] == check
                checkPoint++;
            }
        }
        swap(arr, right, morePoint);//换回来
        return new int[]{lessPoint + 1, morePoint};
    }
    //这里可能自己和自己换，所以不用^
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


}
