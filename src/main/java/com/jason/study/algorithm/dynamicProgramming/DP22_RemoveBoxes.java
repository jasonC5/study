package com.jason.study.algorithm.dynamicProgramming;

/**
 * 给出一些不同颜色的盒子，盒子的颜色由数字表示，即不同的数字表示不同的颜色。
 * <p>
 * 你将经过若干轮操作去去掉盒子，直到所有的盒子都去掉为止。每一轮你可以移除具有相同颜色的连续 k 个盒子（k>= 1），这样一轮之后你将得到 k * k 个积分。
 * <p>
 * 当你将所有盒子都去掉之后，求你能获得的最大积分和。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：boxes = [1,3,2,2,2,3,4,3,1]
 * 输出：23
 * 解释：
 * [1, 3, 2, 2, 2, 3, 4, 3, 1]
 * ----> [1, 3, 3, 4, 3, 1] (3*3=9 分)
 * ----> [1, 3, 3, 3, 1] (1*1=1 分)
 * ----> [1, 1] (3*3=9 分)
 * ----> [] (2*2=4 分)
 * 示例 2：
 * <p>
 * 输入：boxes = [1,1,1]
 * 输出：9
 * 示例 3：
 * <p>
 * 输入：boxes = [1]
 * 输出：1
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-boxes
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class DP22_RemoveBoxes {

    public static int removeBoxes(int[] boxes) {
        return func1(boxes, 0, boxes.length - 1, 0);
    }

    private static int func1(int[] arr, int left, int right, int k) {
        if (left > right) {
            return 0;
        }
        int ans = func1(arr, left + 1, right, 0) + (k + 1) * (k + 1);
        for (int i = left + 2; i <= right; i++) {
            if (arr[left] == arr[i]) {
                ans = Math.max(ans, func1(arr, left + 1, i - 1, 0) + func1(arr, i, right, k + 1));
            }
        }
        return ans;
    }

    public static int removeBoxes2(int[] boxes) {
        int length = boxes.length;
        int[][][] dp = new int[length][length][length];
        return process2(boxes, 0, boxes.length - 1, 0, dp);
    }

    private static int process2(int[] arr, int left, int right, int k, int[][][] dp) {
        if (left > right) {
            return 0;
        }
        if (dp[left][right][k] > 0) {
            return dp[left][right][k];
        }
        int last = left;
        while (last + 1 <= right && arr[last + 1] == arr[left]) {
            last++;
        }
        int pre = k + last - left;
        int ans = process2(arr, last + 1, right, 0, dp) + (pre + 1) * (pre + 1);
        for (int i = last + 2; i <= right; i++) {
            if (arr[i] == arr[left] && arr[i - 1] != arr[left]) {
                ans = Math.max(ans, process2(arr, last + 1, i - 1, 0, dp) + process2(arr, i, right, pre + 1, dp));
            }
        }
        dp[left][right][k] = ans;
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 1, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 1, 2, 2, 2, 2, 1, 2, 1, 1, 2, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1, 2, 2, 1, 1, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2, 1, 1, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 2, 1};
        System.out.println(removeBoxes2(arr));
        System.out.println(removeBoxes(arr));

    }


}
