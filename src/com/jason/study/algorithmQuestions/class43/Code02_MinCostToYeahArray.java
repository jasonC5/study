package com.jason.study.algorithmQuestions.class43;

/**
 * // 来自360笔试
 * // 给定一个正数数组arr，长度为n，下标0~n-1
 * // arr中的0、n-1位置不需要达标，它们分别是最左、最右的位置
 * // 中间位置i需要达标，达标的条件是 : arr[i-1] > arr[i] 或者 arr[i+1] > arr[i]哪个都可以
 * // 你每一步可以进行如下操作：对任何位置的数让其-1
 * // 你的目的是让arr[1~n-1]都达标，这时arr称之为yeah！数组
 * // 返回至少要多少步可以让arr变成yeah！数组
 * // 数据规模 : 数组长度 <= 10000，数组中的值<=500
 *
 * @author JasonC5
 */
public class Code02_MinCostToYeahArray {
    public static final int INVALID = Integer.MAX_VALUE;


    // 纯暴力方法，只是为了结果对
    // 时间复杂度极差
    public static int minCost0(int[] arr) {
        if (arr == null || arr.length < 3) {
            return 0;
        }
        int n = arr.length;
        int min = INVALID;
        for (int num : arr) {
            min = Math.min(min, num);
        }
        int base = min - n;
        return process0(arr, base, 0);
    }

    public static int process0(int[] arr, int base, int index) {
        if (index == arr.length) {
            for (int i = 1; i < arr.length - 1; i++) {
                if (arr[i - 1] <= arr[i] && arr[i] >= arr[i + 1]) {
                    return INVALID;
                }
            }
            return 0;
        } else {
            int ans = INVALID;
            int tmp = arr[index];
            for (int cost = 0; arr[index] >= base; cost++, arr[index]--) {
                int next = process0(arr, base, index + 1);
                if (next != INVALID) {
                    ans = Math.min(ans, cost + next);
                }
            }
            arr[index] = tmp;
            return ans;
        }
    }

    // 为了测试
    public static int[] randomArray(int len, int v) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * v) + 1;
        }
        return arr;
    }

    // 为了测试
    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    // 为了测试
    public static void main(String[] args) {
        int len = 7;
        int v = 10;
        int testTime = 100;
        System.out.println("==========");
        System.out.println("functional_testing_begins");
        for (int i = 0; i < testTime; i++) {
            int n = (int) (Math.random() * len) + 1;
            int[] arr = randomArray(n, v);
            int[] arr0 = copyArray(arr);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);
            int[] arr4 = copyArray(arr);
            int ans0 = minCost0(arr0);
//            int ans1 = minCost1(arr1);
//            int ans2 = minCost2(arr2);
//            int ans3 = minCost3(arr3);
            int ans4 = yeah(arr4);
//            if (ans0 != ans1 || ans0 != ans2 || ans0 != ans3 || ans0 != ans4) {
//                System.out.println("出错了！");
//                break;
//            }
            if (ans0 != ans4) {
                System.out.println("error");
                break;
            }
        }
        System.out.println("end_of_functional_test");
        System.out.println("==========");
        System.out.println("performance_testing_begins");
        len = 10000;
        v = 500;
        System.out.println("generate_random_array_length:" + len);
        System.out.println("generate_a_range_of_random_array_values:[1, " + v + "]");
        int[] arr = randomArray(len, v);
        int[] arr3 = copyArray(arr);
        int[] arrYeah = copyArray(arr);
        long start;
        long end;
        start = System.currentTimeMillis();
//        int ans3 = minCost3(arr3);
//        end = System.currentTimeMillis();
//        System.out.println("minCost3:");
//        System.out.println("operation_result: " + ans3 + ",time_milliseconds: " + (end - start));

        start = System.currentTimeMillis();
        int ansYeah = yeah(arrYeah);
        end = System.currentTimeMillis();
        System.out.println("yeah:");
        System.out.println("operation_result: " + ansYeah + ",time_milliseconds: " + (end - start));
        System.out.println("end_of_performance_test");
        System.out.println("==========");
    }

    //贪心：保证整体先向下，再向上
    public static int yeah(int[] arr) {
        if (arr == null || arr.length < 3) {
            return 0;
        }
        int length = arr.length;
        int[] leftCostSum = new int[length];//从左往右下降成本和
        int[] rightCostSum = new int[length];//从右往左下降成本和
        int line = arr[0];
        for (int i = 1; i < length; i++) {
            line = Math.min(arr[i], line - 1);
            leftCostSum[i] = arr[i] - line + leftCostSum[i - 1];
        }
        line = arr[length - 1];
        for (int i = length - 2; i >= 0; i--) {
            line = Math.min(arr[i], line - 1);
            rightCostSum[i] = arr[i] - line + rightCostSum[i + 1];
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < length - 1; i++) {
            ans = Math.min(ans, leftCostSum[i] + rightCostSum[i + 1]);
        }
        return ans;
    }

    //动态规划


}
