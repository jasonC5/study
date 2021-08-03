package com.jason.study.algorithm.arrayList;

/**
 * 给定一个正整数组成的无序数组arr，给定一个正整数值K
 * 找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
 * 返回其长度
 *
 * @author JasonC5
 */
public class LongestSumSubArrayLengthInPositiveArray {

    public static void main(String[] args) {
        int[] arr = {59,45,36,15,82,89,98,25,82,99,52,96,80,9,57,6,52,38,65,71,55,2,27,40,12,39,26,50,46,69,65,36,68,92,5,20,78,75,66,86,53,41,90,53,100,56,32,69,15,71};
        int i = func1(arr, 18);
        System.out.println(i);
    }

    public static int func1(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) {
            return 0;
        }
        int left = 0, right = 0, max = 0, sum = arr[0];
        while (right < arr.length) {
            if (sum == k) {
                max = Math.max(max, right - left + 1);
//                sum += arr[right++];
                sum -= arr[left++];
            } else if (sum < k) {
                if (++right < arr.length) {
                    sum += arr[right];
                } else {
                    break;
                }
            } else {
                if (left < arr.length) {
                    sum -= arr[left++];
                }
            }
        }
        return max;
    }

    public static int getMaxLength(int[] arr, int K) {
        if (arr == null || arr.length == 0 || K <= 0) {
            return 0;
        }
        int left = 0;
        int right = 0;
        int sum = arr[0];
        int len = 0;
        while (right < arr.length) {
            if (sum == K) {
                len = Math.max(len, right - left + 1);
                sum -= arr[left++];
            } else if (sum < K) {
                right++;
                if (right == arr.length) {
                    break;
                }
                sum += arr[right];
            } else {
                sum -= arr[left++];
            }
        }
        return len;
    }

    // for test
    public static int right(int[] arr, int K) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (valid(arr, i, j, K)) {
                    max = Math.max(max, j - i + 1);
                }
            }
        }
        return max;
    }

    // for test
    public static boolean valid(int[] arr, int L, int R, int K) {
        int sum = 0;
        for (int i = L; i <= R; i++) {
            sum += arr[i];
        }
        return sum == K;
    }

    // for test
    public static int[] generatePositiveArray(int size, int value) {
        int[] ans = new int[size];
        for (int i = 0; i != size; i++) {
            ans[i] = (int) (Math.random() * value) + 1;
        }
        return ans;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
        System.out.println();
    }

//    public static void main(String[] args) {
//        int len = 50;
//        int value = 100;
//        int testTime = 500000;
//        System.out.println("test begin");
//        for (int i = 0; i < testTime; i++) {
//            int[] arr = generatePositiveArray(len, value);
//            int K = (int) (Math.random() * value) + 1;
//            int ans1 = getMaxLength(arr, K);
//            System.out.println(ans1);
//            int ans2 = func1(arr, K);
//            if (ans1 != ans2) {
//                System.out.println("Oops!");
//                printArray(arr);
//                System.out.println("K : " + K);
//                System.out.println(ans1);
//                System.out.println(ans2);
//                break;
//            }
//        }
//        System.out.println("test end");
//    }
}
