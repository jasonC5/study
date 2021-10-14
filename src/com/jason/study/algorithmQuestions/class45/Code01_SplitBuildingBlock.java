package com.jason.study.algorithmQuestions.class45;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * // 来自京东笔试
 * // 小明手中有n块积木，并且小明知道每块积木的重量。现在小明希望将这些积木堆起来
 * // 要求是任意一块积木如果想堆在另一块积木上面，那么要求：
 * // 1) 上面的积木重量不能小于下面的积木重量
 * // 2) 上面积木的重量减去下面积木的重量不能超过x
 * // 3) 每堆中最下面的积木没有重量要求
 * // 现在小明有一个机会，除了这n块积木，还可以获得k块任意重量的积木。
 * // 小明希望将积木堆在一起，同时希望积木堆的数量越少越好，你能帮他找到最好的方案么？
 * // 输入描述:
 * // 第一行三个整数n,k,x，1<=n<=200000，0<=x,k<=1000000000
 * // 第二行n个整数，表示积木的重量，任意整数范围都在[1,1000000000]
 * // 样例输出：
 * // 13 1 38
 * // 20 20 80 70 70 70 420 5 1 5 1 60 90
 * // 1 1 5 5 20 20 60 70 70 70 80 90 420 -> 只有1块魔法积木，x = 38
 * // 输出：2
 * // 解释：
 * // 两堆分别是
 * // 1 1 5 5 20 20 (50) 60 70 70 70 80 90
 * // 420
 * // 其中x是一个任意重量的积木，夹在20和60之间可以让积木继续往上搭
 *
 * @author JasonC5
 */
public class Code01_SplitBuildingBlock {

    public static int minSplit(int[] arr, int k, int x) {
        Arrays.sort(arr);
        int length = arr.length;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();//存放每个分段中间的差值
        int stage = 1;
        for (int i = 1; i < length; i++) {
            if (arr[i] - arr[i - 1] > x) {
                //无法继续往上磊了
                minHeap.offer(arr[i] - arr[i - 1]);
                stage++;
            }
        }
        //边界情况
        if (stage == 1 || x == 0) {
            return stage;
        }
        //从花费小到花费大，消耗魔法积木，粘合相邻分段
        while (!minHeap.isEmpty()) {
            Integer s = minHeap.poll();
            int cost = (s - 1) / x;
            if (cost <= k) {
                stage--;
                k -= cost;
            } else {
                break;
            }
        }
        return stage;
    }
}
