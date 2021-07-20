package com.jason.study.algorithm.others;

/**
 * 给定一个只包含正数的数组arr，arr中任何一个子数组sub，
 * 一定都可以算出(sub累加和 )* (sub中的最小值)是什么，
 * 那么所有子数组中，这个值最大是多少？
 * <p>
 * 针对数组中的每个值i，以i为最小值的子数组，最大的范围是(i左边比它小，i右边比它小)
 * 找到每个i对应的范围
 *
 * @author JasonC5
 */
public class DDZ_02 {

    public static int max1(int[] arr) {
        int max = -1;
        //左边小于arr[i]的位置，右边小于arr[i]的位置
        int length = arr.length;
//        Stack<Integer> stack = new Stack<>();
        int[] stack = new int[length];
        int point = -1;
        //计算累加和，方便下面计算
        int[] sum = new int[length];
        sum[0] = arr[0];
        for (int i = 1; i < length; i++) {
            sum[i] = sum[i - 1] + arr[i];
        }
        //先遍历数组,找到针对每个元素作为最小值时候的答案
        for (int i = 0; i < length; i++) {
            //把小于当前元素的，都弹出来，获得统计值
            while (point != -1 && arr[stack[point]] >= arr[i]) {
                Integer idx = stack[point--];
                //谁把它弹出来了，就是它右边小于它的最近值
                int right = i - 1;
                int left = point == -1 ? 0 : stack[point] + 1;
                max = Math.max(max, (sum[right] - (left - 1 == -1 ? 0 : sum[left - 1])) * arr[idx]);
            }
//            stack.push(i);
            stack[++point] = i;
        }
        //再把栈弹空
        while (point != -1) {
            Integer idx = stack[point--];
            int right = length - 1;
            int left = point == -1 ? 0 : stack[point] + 1;
            max = Math.max(max, (sum[right] - (left - 1 == -1 ? 0 : sum[left - 1])) * arr[idx]);
        }
        return max;
    }


    public static void main(String[] args) {
//        int[] arr = {4, 5, 1, 2, 6, 8, 4, 5};
//        int max1 = max1(arr);
//        int max2 = max2(arr);
//        System.out.println(max1);
//        System.out.println(max2);
        int testTimes = 2000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = gerenareRondomArray();
            if (max1(arr) != max2(arr)) {
                System.out.println("FUCK!");
                break;
            }
        }
        System.out.println("test finish");
    }
    //
    public static int[] gerenareRondomArray() {
        int[] arr = new int[(int) (Math.random() * 20) + 10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 101);
        }
        return arr;
    }

    //暴力方法
    public static int max2(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int minNum = Integer.MAX_VALUE;
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += arr[k];
                    minNum = Math.min(minNum, arr[k]);
                }
                max = Math.max(max, minNum * sum);
            }
        }
        return max;
    }
}
