package com.jason.study.algorithm.sort;

import java.util.Arrays;

/**
 * 二分法--解决局部最小值问题
 * 局部最小值：当前值比左边的数小，比右边的数也小（若有左右）
 * @author JasonC5
 */
public class Dichotomy {
    public static void main(String[] args) {
        int maxLength = 10;
        int maxVal = 100;
        int[] array = InsertSort.buildArray(maxLength, maxVal);//这个其实有问题，按照题意相邻的数字必相等，这个不能保证
        int index = findLocalMinNumIndex(array);
        System.out.println(index);
        Arrays.stream(array).boxed().forEach(x->System.out.print(x+" "));
    }

    private static int findLocalMinNumIndex(int[] array) {
        if (array.length <= 1) {
            return 0;
        }
        //先找左右边界
        int length = array.length;
        if (array[0] < array[1]) {
            return 0;
        } else if (array[length-1] < array[length -2]) {
            return length-1;
        }
        //二分法查找
//        return findLocalMinNumIndexWin(array,0,length-1);
//        走到这没出去，说明左边界向下，右边界向上，相邻的数不相等，所以至少存在一个局部最小值
        int left = 1,right = length-2,mid;
        while(left < right){
            mid = left + (right-left) >> 1;
            if (array[mid] > array[mid-1]) {//右边界收敛
                right = mid-1;
            } else if (array[mid] > array[mid+1]) {
                left = mid+1;
            } else {
                return mid;
            }
        }
        return left;
    }

//    private static int findLocalMinNumIndexWin(int[] array, int left, int right) {
//        int mid = left + (right-left) >> 1;
//        if () {
//
//        } else if () {
//
//        } else {
//            return mid;
//        }
//        return 0;
//    }
}
