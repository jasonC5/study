package com.jason.study.algorithm.greedy;

import java.util.PriorityQueue;

/**
 * 3.一块金条切成两半，是需要花费和长度数字一样的铜板，比如长度为20的金条，不管怎么切，都需要花费20个铜板，一群人想整分整块金条，怎么分最省铜板
 * 例：给定数组：{10,20,30}代表一共三个人，整块金条长度为60，金条要分城10,20,30三部分
 * 输入一个数组，返回分隔的最小代价
 * 哈夫曼编码问题
 * 小根堆
 *
 * @author JasonC5
 */
public class Greedy3 {

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
//        int[] param= {10,20,30};
        int testTime = 1000;
        int maxSize = 6;
        int maxValue = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] param= generateRandomArray(maxSize, maxValue);
            if (lessMoney(param) != lessMoney1(param)) {
                System.out.println("failed,param="+param);
            }
        }

        System.out.println("finished");
    }

    // 纯暴力！
    public static int lessMoney1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0);
    }

    // 等待合并的数都在arr里，pre之前的合并行为产生了多少总代价
    // arr中只剩一个数字的时候，停止合并，返回最小的总代价
    public static int process(int[] arr, int pre) {
        if (arr.length == 1) {
            return pre;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                ans = Math.min(ans, process(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return ans;
    }

    public static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1];
        int ansi = 0;
        for (int arri = 0; arri < arr.length; arri++) {
            if (arri != i && arri != j) {
                ans[ansi++] = arr[arri];
            }
        }
        ans[ansi] = arr[i] + arr[j];
        return ans;
    }

    private static int lessMoney(int[] param) {
        if (param == null || param.length <= 1){
            return 0;
        }
        int ans = 0;
        PriorityQueue<Integer> minHeap = new PriorityQueue();
        for (int i : param) {
            minHeap.add(i);
        }
        while(minHeap.size() > 1){
            Integer poll1 = minHeap.poll();
            Integer poll2 = minHeap.poll();
            int sum = poll1 + poll2;
            ans += sum;
            minHeap.add(sum);
        }
        return ans;
    }

}
