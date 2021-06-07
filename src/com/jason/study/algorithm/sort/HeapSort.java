package com.jason.study.algorithm.sort;

/**
 * 堆排序
 *
 * @author JasonC5
 */
public class HeapSort {
    //一直调整堆大小
    public static void main(String[] args) {
//        int[] num = {40,49,64,96,45,68,100};
//        heapSort(num);
//        System.out.println(MergeSort.print(num));
        int times = 1000;
        for (int i = 0; i < times; i++) {
            int maxLength = 10;
            int maxVal = 100;
            int[] array = InsertSort.buildArray(maxLength, maxVal);
            int[] a = InsertSort.copyArray(array);
            int[] b = InsertSort.copyArray(array);
            MergeSort.recursionMergeSort(a, 0, array.length - 1);//递归归并排序
            heapSort(b);//迭代归并排序
            if (!InsertSort.compare(a, b)) {
                System.out.println("failed, arr=" + MergeSort.print(array));
                return;
            }
        }
        System.out.println("complete!!!");

    }

    /**
     * 堆排序
     *
     * @param arr
     */
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        //上来先把数组排成大根堆
        int length = arr.length;
        //O(n*log n)
//        for (int i = 0; i < length; i++) {
//            insert(arr, i);
//        }
        //O(n)
        for (int i = length - 1; i >= 0; i--) {
            heapify(arr, i, length);
        }
        //最大值换到最后
        swap(arr, 0, --length);//O(1)
        while (length > 0) {
            heapify(arr, 0, length);//O(log n)
            swap(arr, 0, --length);//O(1)
        }
    }

    /**
     * 插入上浮
     *
     * @param arr
     * @param index
     */
    public static void insert(int[] arr, int index) {
        //往上浮
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * 交换下沉
     *
     * @param arr
     * @param index
     * @param heapSize
     */
    public static void heapify(int[] arr, int index, int heapSize) {
        while (true) {
            int left = index * 2 + 1;
            if (left > heapSize - 1) {//没有子了，直接return
                return;
            }
            int bigIndex = left + 1 <= heapSize - 1 && arr[left] < arr[left + 1] ? left + 1 : left;
            bigIndex = arr[bigIndex] > arr[index] ? bigIndex : index;
            if (bigIndex != index) {//index不是left、right、index中最大的，大的往上浮动，自己往下走
                swap(arr, index, bigIndex);
                index = bigIndex;
            } else {
                return;//找到自己的位置了，return
            }
        }

    }

    /**
     * 交换
     *
     * @param arr
     * @param index1
     * @param index2
     */
    public static void swap(int[] arr, int index1, int index2) {
        int tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }

}
