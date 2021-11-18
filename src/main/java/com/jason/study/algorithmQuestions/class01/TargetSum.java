package com.jason.study.algorithmQuestions.class01;

/**
 * 给定一个数组arr，你可以在每个数字之前决定+或者-
 * 但是必须所有数字都参与
 * 再给定一个数target，请问最后算出target的方法数是多少？
 * --记忆化搜索、动态规划
 * --优化点1：每个数字正负不影响结果，把所有数字改成非负数
 * --优化点2：累加和sum<target：0种方法
 * --优化点3：sum和taiget奇偶性不一样：0种方法
 * --优化点4：所有加号一个集合P，所有减号一个集合N，target = P-N ==> 2P = target+P+N==>P = (taeget + sum) / 2 ，自由选择数字相加，得到 (taeget + sum) / 2==> 背包问题
 * --优化点5：空间压缩
 * --背包问题
 *
 * @author JasonC5
 */
public class TargetSum {
    public static void main(String[] args) {
        int[] arr = {1, 1, 1, 1, 1};
        int target = 3;
        System.out.println(findTargetSumWays3(arr, target));
    }

    public static int findTargetSumWays3(int[] arr, int target) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return (sum < target) || (target & 1) != (sum & 1) ? 0 : process2(arr, (target + sum) >> 1);
    }

    //arr 从左到右凑够 sum的方法数
    private static int process(int[] arr, int sum) {
        int[][] dp = new int[arr.length + 1][sum + 1];
        dp[0][0] = 1;//1种方案：不选
        for (int i = 1; i <= arr.length; i++) {
            for (int j = 0; j <= sum; j++) {
                //当前是arr[i]，要凑够j,0~i-1的时候要凑够 j-arr[i]
                dp[i][j] += dp[i - 1][j] + ((j - arr[i - 1]) < 0 ? 0 : dp[i - 1][j - arr[i - 1]]);
            }
        }
        return dp[arr.length][sum];
    }

    private static int process2(int[] arr, int sum) {
        int[] dp = new int[sum + 1];
        dp[0] = 1;//1种方案：不选
        for (int num : arr) {
            for (int i = sum; i >= num; i--) {
                dp[i] += dp[i - num];
            }
        }
        return dp[sum];
    }
}
