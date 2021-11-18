package com.jason.study.algorithm.sort;


import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 最大重合线段问题
 *
 * @author JasonC5
 */
public class Heap_MaxCover {

    /**
     * 线段
     */
    public static class Line {
        public int start;
        public int end;

        public Line(int s, int e) {
            start = s;
            end = e;
        }
    }

    /**
     * 线段数组中，求最多有几条线段重合
     * 算法：
     * 1.设置一个小根堆，将堆内小于 当前线段的起点start的数全部弹出
     * 2.将当前线段的终点end放入小根堆
     * 3.堆中有几个元素，说明起点位置有几个线段重合
     * 4.全局一个max和当前结果取最大值
     */
    public static void main(String[] args) {
        //
        int[][] lines = {{4, 9}, {1, 4}, {7, 15}, {2, 4}, {4, 6}, {3, 7}};
        int c = maxCover(lines);
        System.out.println(c);
    }

    private static int maxCover(int[][] arr) {
        Line[] lines = new Line[arr.length];
        for (int i = 0; i < arr.length; i++) {
            lines[i] = new Line(arr[i][0], arr[i][1]);
        }
        //按照起点从小到大排列
        Arrays.sort(lines, (a, b) -> a.start - b.start);
        //创建一个小根堆，里面放的end
        //PriorityQueue默认小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int max = 0;
        for (int i = 0; i < lines.length; i++) {
            Line thisLine = lines[i];
            //把自己的终点放入小根堆
            heap.add(thisLine.end);
            //先将小根堆里面小于等于起始值的终点弹出
            while (!heap.isEmpty() && heap.peek() <= thisLine.start) {
                heap.poll();
            }
            max = Math.max(max, heap.size());
        }
        return max;
    }
}
