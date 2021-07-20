package com.jason.study.algorithm.others;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 单调栈的实现
 * 1）arr[i]的左侧离i最近并且小于(或者大于)arr[i]的数在哪？
 * 2）arr[i]的右侧离i最近并且小于(或者大于)arr[i]的数在哪？
 * @apiNote 单调栈实现
 * @author JasonC5
 */
public class DDZ_01 {
    //1.不重复数组实现
    // arr = [ 3, 1, 2, 3]
    //         0  1  2  3
    //  [
    //     0 : [-1,  1]
    //     1 : [-1, -1]
    //     2 : [ 1, -1]
    //     3 : [ 2, -1]
    //  ]
    public static int[][] getNearLessNoRepeat(int[] arr) {
        //左边小于arr[i]的位置，右边小于arr[i]的位置
        int length = arr.length;
        int[][] res = new int[length][2];
        Stack<Integer> stack = new Stack<>();
        //先遍历数组
        for (int i = 0; i < length; i++) {
            //把小于当前元素的，都弹出来，获得统计值
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                Integer idx = stack.pop();
                //谁把它弹出来了，就是它右边小于它的最近值
                res[idx][1] = i;
                res[idx][0] = stack.isEmpty() ? -1 : stack.peek();
            }
            stack.push(i);
        }
        //再把栈弹空
        while (!stack.isEmpty()) {
            Integer idx = stack.pop();
            res[idx][1] = -1;//没人把它弹出来，右边没有更小的了
            res[idx][0] = stack.isEmpty() ? -1 : stack.peek();
        }
        return res;
    }

    public static int[][] getNearLessNoRepeatByArray(int[] arr) {
        //左边小于arr[i]的位置，右边小于arr[i]的位置
        int length = arr.length;
        int[][] res = new int[length][2];
//        Stack<Integer> stack = new Stack<>();
        int[] stack = new int[length];
        int point = -1;
        //先遍历数组
        for (int i = 0; i < length; i++) {
            //把小于当前元素的，都弹出来，获得统计值
            while (point != -1 && arr[stack[point]] > arr[i]) {
                Integer idx = stack[point--];
                //谁把它弹出来了，就是它右边小于它的最近值
                res[idx][1] = i;
                res[idx][0] = point == -1 ? -1 : stack[point];
            }
//            stack.push(i);
            stack[++point] = i;
        }
        //再把栈弹空
        while (point != -1) {
            Integer idx = stack[point--];
            res[idx][1] = -1;//没人把它弹出来，右边没有更小的了
            res[idx][0] = point == -1 ? -1 : stack[point];
        }
        return res;
    }

    //2.可重复数组实现--栈内放数组，重复的时候挂起，
    public static int[][] getNearLess(int[] arr) {
        int length = arr.length;
        int[][] res = new int[length][2];
        Stack<List<Integer>> stack = new Stack<>();
        //先遍历数组
        for (int i = 0; i < length; i++) {
            //把小于当前元素的，都弹出来，获得统计值
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                List<Integer> idxList = stack.pop();
                //谁把它弹出来了，就是它右边小于它的最近值
                for (Integer idx : idxList) {
                    res[idx][1] = i;
                    res[idx][0] = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                }
            }
            //放进去的时候，可能有相同值，挂在一起
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().add(i);
            } else {
                List<Integer> idxList = new ArrayList<>();
                idxList.add(i);
                stack.push(idxList);
            }
        }
        //再把栈弹空
        while (!stack.isEmpty()) {
            List<Integer> idxList = stack.pop();
            int leftLessIdx = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for (Integer idx : idxList) {
                res[idx][1] = -1;
                res[idx][0] = leftLessIdx;
            }
        }
        return res;

    }

    //2.可重复数组实现--栈内放数组，重复的时候挂起，
    public static int[][] getNearLessByArray(int[] arr) {
        int length = arr.length;
        int[][] res = new int[length][2];
        List<Integer>[] stack = new List[length];
        int point = -1;
        //先遍历数组
        for (int i = 0; i < length; i++) {
            //把小于当前元素的，都弹出来，获得统计值
            while (point != -1 && arr[stack[point].get(0)] > arr[i]) {
                List<Integer> idxList = stack[point--];
                //谁把它弹出来了，就是它右边小于它的最近值
                for (Integer idx : idxList) {
                    res[idx][1] = i;
                    res[idx][0] = (point == -1) ? -1 : stack[point].get(stack[point].size() - 1);
                }
            }
            //放进去的时候，可能有相同值，挂在一起
            if (point != -1 && arr[stack[point].get(0)] == arr[i]) {
                stack[point].add(i);
            } else {
                List<Integer> idxList = new ArrayList<>();
                idxList.add(i);
                stack[++point] = idxList;
            }
        }
        //再把栈弹空
        while (point != -1) {
            List<Integer> idxList = stack[point--];
            int leftLessIdx = point == -1 ? -1 : stack[point].get(stack[point].size() - 1);
            for (Integer idx : idxList) {
                res[idx][1] = -1;
                res[idx][0] = leftLessIdx;
            }
        }
        return res;

    }

    public static void main(String[] args) {
        int size = 10;
        int max = 20;
        int testTimes = 2000000;
        System.out.println("start");
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = getRandomArrayNoRepeat(size);
            int[] arr2 = getRandomArray(size, max);
            if (!isEqual(getNearLessNoRepeat(arr1), rightWay(arr1))) {
                System.out.println("Oops-1!");
                printArray(arr1);
                break;
            }
            if (!isEqual(getNearLessNoRepeat(arr1), getNearLessNoRepeatByArray(arr1))) {
                System.out.println("Oops-4!");
                printArray(arr1);
                break;
            }
            if (!isEqual(getNearLess(arr2), rightWay(arr2))) {
                System.out.println("Oops-2!");
                printArray(arr2);
                break;
            }
            if (!isEqual(getNearLessByArray(arr2), rightWay(arr2))) {
                System.out.println("Oops-3!");
                printArray(arr2);
                break;
            }
        }
        System.out.println("end");
    }


    // for test
    public static int[] getRandomArrayNoRepeat(int size) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < arr.length; i++) {
            int swapIndex = (int) (Math.random() * arr.length);
            int tmp = arr[swapIndex];
            arr[swapIndex] = arr[i];
            arr[i] = tmp;
        }
        return arr;
    }

    // for test
    public static int[] getRandomArray(int size, int max) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    // for test
    public static int[][] rightWay(int[] arr) {
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            int leftLessIndex = -1;
            int rightLessIndex = -1;
            int cur = i - 1;
            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    leftLessIndex = cur;
                    break;
                }
                cur--;
            }
            cur = i + 1;
            while (cur < arr.length) {
                if (arr[cur] < arr[i]) {
                    rightLessIndex = cur;
                    break;
                }
                cur++;
            }
            res[i][0] = leftLessIndex;
            res[i][1] = rightLessIndex;
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[][] res1, int[][] res2) {
        if (res1.length != res2.length) {
            return false;
        }
        for (int i = 0; i < res1.length; i++) {
            if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
                return false;
            }
        }

        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }


}
