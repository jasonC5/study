package com.jason.study.algorithm.sort;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 插入排序、对数器使用
 * @author JasonC5
 */
public class InsertSort {

    public static void main(String[] args) {
        //随机数
//        int[] a = {8,1,4,5,1,6,1,4};
////        System.out.println(a);
//        Arrays.stream(a).boxed().forEach(System.out::print);
//        insertSort(a);
////        System.out.println(a);
//        System.out.println();
//        Arrays.stream(a).boxed().forEach(System.out::print);
        //产生随机长度的随机数
        //对数器
        int maxLength = 100;
        int maxVal = 1000;
        //产生随机参数
        int[] x = buildArray(maxLength, maxVal);
        //copy两份
        int[] a = copyArray(x);
        int[] b = copyArray(x);
        //两种方案进行处理
        insertSort(a);
        Arrays.sort(b);
        //两种方案结果进行对比
        if (compare(a,b)) {
            System.out.println("cool!");
        } else {
            System.out.println("so sad!");
        }
    }

    public static boolean compare(int[] a, int[] b) {
        if (a.length != b.length){
            return false;
        }
        for (int i = 0; i < a.length; i++){
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] copyArray(int[] x) {
        int[] arr = new int[x.length];
        for (int i = 0; i < x.length; i++) {
            arr[i] = x[i];
        }
        return arr;
    }

    public static int[] buildArray(int maxLength, int maxVal) {//产生随机长度，随机大小数组
        int length = (int) (Math.random() * (maxLength + 1));
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int) (Math.random() * (maxVal + 1));
        }
        return arr;
    }

    public static int[] insertSort(int[] arr){
        //从第一个位置开始，往左边看，左边比自己大，就交换，直到左边的数比自己小就停止
        int length = arr.length;
        for (int i = 1; i < length; i++) {//从第1个开始看，第0个是=自己的
            for (int j = i; j >0 && arr[j] < arr[j-1]; j--) {
                swap(arr, j-1, j);
            }
        }
        return arr;
    }

    private static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];// a ^ b
        arr[j] = arr[i] ^ arr[j];// a ^ b ^ b = a
        arr[i] = arr[i] ^ arr[j];// a ^ b ^ a = b
    }


}
