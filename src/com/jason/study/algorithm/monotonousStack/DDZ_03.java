package com.jason.study.algorithm.monotonousStack;

/**
 * https://leetcode-cn.com/problems/largest-rectangle-in-histogram/
 * 给定一个非负数组arr，代表直方图
 * 返回直方图的最大长方形面积
 * @author JasonC5
 */
public class DDZ_03 {
    public static int largestRectangleArea(int[] arr) {
        int max = -1;
        //左边小于arr[i]的位置，右边小于arr[i]的位置
        int length = arr.length;
//        Stack<Integer> stack = new Stack<>();
        int[] stack = new int[length];
        int point = -1;
        //先遍历数组,找到针对每个元素作为最小值时候的答案
        for (int i = 0; i < length; i++) {
            //把小于当前元素的，都弹出来，获得统计值
            while (point != -1 && arr[stack[point]] >= arr[i]) {
                Integer idx = stack[point--];
                //谁把它弹出来了，就是它右边小于它的最近值
                int right = i - 1;
                int left = point == -1 ? 0 : stack[point] + 1;
                max = Math.max(max, (right - left + 1) * arr[idx]);
            }
//            stack.push(i);
            stack[++point] = i;
        }
        //再把栈弹空
        while (point != -1) {
            Integer idx = stack[point--];
            int right = length - 1;
            int left = point == -1 ? 0 : stack[point] + 1;
            max = Math.max(max, (right - left + 1) * arr[idx]);
        }
        return max;
    }


}
