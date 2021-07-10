package com.jason.study.algorithm.dynamicProgramming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * arr是货币数组，其中的值都是正数。再给定一个正数aim。
 * 每个值都认为是一张货币，
 * 认为值相同的货币没有任何不同，
 * 返回组成aim的方法数
 * 例如：arr = {1,2,1,1,2,1,2}，aim = 4
 * 方法：1+1+1+1、1+1+2、2+2
 * 一共就3种方法，所以返回3
 *
 * @author JasonC5
 */
public class DP13_CoinsWaySameValueSamePapper {


    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int times = 100;

        for (int i = 0; i < times; i++) {
            int maxLen = 10;
            int maxValue = 10;
            int targetMax = 20;
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * targetMax);
            int ans1 = coinWays1(arr, aim);
//            int ans2 = coinWays2(arr, aim);
            int ans2 = coinWays3(arr, aim);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                return;
            }
        }
        System.out.println("complete!!");
    }

    public static class Info {
        int num;
        int count;

        public Info(int num, int count) {
            this.num = num;
            this.count = count;
        }
    }


    private static List<Info> arr2Info(int[] arr) {
        Map<Integer, Info> map = new HashMap<>();
        for (int num : arr) {
            Info info = map.getOrDefault(num, new Info(num, 0));
            info.count++;
            map.put(num, info);
        }
        return new ArrayList<>(map.values());
    }

    private static int coinWays1(int[] arr, int aim) {
        //数组内合并，转换成两个数组
        List<Info> infoList = arr2Info(arr);
        int[] nums = new int[infoList.size()];
        int[] counts = new int[infoList.size()];
        for (int i = 0; i < infoList.size(); i++) {
            nums[i] = infoList.get(i).num;
            counts[i] = infoList.get(i).count;
        }

        return process(nums, counts, 0, aim);
    }

    private static int process(int[] nums, int[] counts, int idx, int sur) {
        if (sur < 0) {
            return 0;
        }
        if (idx == nums.length) {
            return sur == 0 ? 1 : 0;
        }
        int ans = 0;
        int sum;
        for (int i = 0; i <= counts[idx] && (sum = (nums[idx] * i)) <= sur; i++) {
            ans += process(nums, counts, idx + 1, sur - sum);
        }
        return ans;
    }


    private static int coinWays2(int[] arr, int aim) {
        //数组内合并，转换成两个数组
        List<Info> infoList = arr2Info(arr);
        int[] nums = new int[infoList.size()];
        int[] counts = new int[infoList.size()];
        for (int i = 0; i < infoList.size(); i++) {
            nums[i] = infoList.get(i).num;
            counts[i] = infoList.get(i).count;
        }
        int[][] dp = new int[nums.length + 1][aim + 1];
        dp[nums.length][0] = 1;
        for (int idx = nums.length - 1; idx >= 0; idx--) {
            for (int sur = 0; sur <= aim; sur++) {
                int ans = 0;
                int sum;
                for (int i = 0; i <= counts[idx] && (sum = (nums[idx] * i)) <= sur; i++) {
                    ans += dp[idx + 1][sur - sum];
                }
                dp[idx][sur] = ans;
            }
        }
        return dp[0][aim];
    }

    private static int coinWays3(int[] arr, int aim) {
        //数组内合并，转换成两个数组
        List<Info> infoList = arr2Info(arr);
        int[] nums = new int[infoList.size()];
        int[] counts = new int[infoList.size()];
        for (int i = 0; i < infoList.size(); i++) {
            nums[i] = infoList.get(i).num;
            counts[i] = infoList.get(i).count;
        }
        int[][] dp = new int[nums.length + 1][aim + 1];
        dp[nums.length][0] = 1;
        for (int idx = nums.length - 1; idx >= 0; idx--) {
            for (int sur = 0; sur <= aim; sur++) {
                dp[idx][sur] = dp[idx + 1][sur];
                if (sur >= nums[idx]) {
                    dp[idx][sur] += dp[idx][sur - nums[idx]];
                }
                if (sur >= (nums[idx] * (counts[idx] + 1))) {
                    dp[idx][sur] -= dp[idx + 1][sur - nums[idx] * (counts[idx] + 1)];
                }
            }
        }
        return dp[0][aim];
    }

}
