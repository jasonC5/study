package com.jason.study.algorithmQuestions.class39;

/**
 * // 来自腾讯
 * // 给定一个长度为n的数组arr，求有多少个子数组满足 :
 * // 子数组两端的值，是这个子数组的最小值和次小值，最小值和次小值谁在最左和最右无所谓
 * // n<=100000（10^5） n*logn  O(N)
 * --单调栈
 * --可见山峰对问题类似
 * --  某位置上的数一定要作为子数组右端，且是最小值
 * --  某一位置上的数一定要作为子数组左端，且是最小值
 * -- 两个相加
 *
 * @author JasonC5
 */
public class Code02_ValidSequence {

    // 为了测试
    // 暴力方法
    public static int test(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int ans = 0;
        for (int s = 0; s < arr.length; s++) {
            for (int e = s + 1; e < arr.length; e++) {
                int max = Math.max(arr[s], arr[e]);
                boolean valid = true;
                for (int i = s + 1; i < e; i++) {
                    if (arr[i] < max) {
                        valid = false;
                        break;
                    }
                }
                ans += valid ? 1 : 0;
            }
        }
        return ans;
    }

    // 为了测试
    public static int[] randomArray(int n, int v) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = (int) (Math.random() * v);
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // 为了测试
    public static void main(String[] args) {
        int n = 30;
        int v = 30;
        int testTime = 100000;
        System.out.println("start");
        for (int i = 0; i < testTime; i++) {
            int m = (int) (Math.random() * n);
            int[] arr = randomArray(m, v);
            int ans1 = nums(arr);
            int ans2 = test(arr);
            if (ans1 != ans2) {
                System.out.println("error!");
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
            }
        }
        System.out.println("finished");
    }

    public static int nums(int[] arr) {
        //子数组两端的值，是这个子数组的最小值和次小值，最小值和次小值谁在最左和最右无所谓
        int length = arr.length;
        if (length == 2) {
            return 1;
        }
        int ans = 0;
        int[] stack = new int[length];
        int[] sameNum = new int[length];
        int size = 0;
        //从左往右，处理相等的值【左边是次小值，右边是最小值】
        for (int i = 0; i < length; i++) {
            //先尝试弹出栈内元素
            while (size > 0 && arr[i] < stack[size - 1]) {
                int count = sameNum[size - 1];
                ans += count;//弹出了多少个
                ans += ((count - 1) * count) >> 1;//有相同的，任意找两个可组成答案，Cn2
                size--;
            }
            // 再把自己放入栈中
            if (size > 0 && arr[i] == stack[size - 1]) {
                sameNum[size - 1]++;
            } else {
                stack[size] = arr[i];
                sameNum[size++] = 1;
            }
        }
        // 栈中还有，只处理相等的
        while (size > 0) {
            int count = sameNum[--size];
            ans += ((count - 1) * count) >> 1;
        }
        //从右往左，不处理相等的值【左边是最小值，右边是次小值】
        for (int i = length - 1; i >= 0; i--) {
            while (size > 0 && arr[i] < stack[size - 1]) {
                int count = sameNum[size - 1];
                ans += count;//弹出了多少个
//                ans += ((count - 1) * count) >> 1;//第二次，不处理相同的，否则会重
                size--;
            }
            // 再把自己放入栈中
            if (size > 0 && arr[i] == stack[size - 1]) {
                sameNum[size - 1]++;
            } else {
                stack[size] = arr[i];
                sameNum[size++] = 1;
            }
        }
        return ans;
    }
}
