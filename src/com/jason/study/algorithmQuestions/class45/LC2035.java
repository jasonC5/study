package com.jason.study.algorithmQuestions.class45;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * 给你一个长度为 2 * n的整数数组。你需要将nums分成两个长度为n的数组，分别求出两个数组的和，并 最小化两个数组和之差的绝对值。nums中每个元素都需要放入两个数组之一。
 * <p>
 * 请你返回最小的数组和之差。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * <p>
 * 输入：nums = [3,9,7,3]
 * 输出：2
 * 解释：最优分组方案是分成 [3,9] 和 [7,3] 。
 * 数组和之差的绝对值为 abs((3 + 9) - (7 + 3)) = 2 。
 * 示例 2：
 * <p>
 * 输入：nums = [-36,36]
 * 输出：72
 * 解释：最优分组方案是分成 [-36] 和 [36] 。
 * 数组和之差的绝对值为 abs((-36) - (36)) = 72 。
 * 示例 3：
 * <p>
 * <p>
 * <p>
 * 输入：nums = [2,-1,0,4,-2,-9]
 * 输出：0
 * 解释：最优分组方案是分成 [2,4,-9] 和 [-1,0,-2] 。
 * 数组和之差的绝对值为 abs((2 + 4 + -9) - (-1 + 0 + -2)) = 0 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/partition-array-into-two-arrays-to-minimize-sum-difference
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC2035 {

    public static void main(String[] args) {

    }

    // 分治
    public static int minimumDifference(int[] arr) {
        int size = arr.length;
        int half = size >> 1;
        //左半部分，选i个数，能组成的所有不同的和
        Map<Integer, TreeSet<Integer>> leftMap = new HashMap<>();
        //右半部分，选i个数，能组成的所有不同的和
        Map<Integer, TreeSet<Integer>> rightMap = new HashMap<>();
        process(arr, 0, half, 0, 0, leftMap);
        process(arr, half, size, 0, 0, rightMap);
        //左边取i个，右边取half-i个，全试
        long sum = Arrays.stream(arr).sum();
        int ans = Integer.MAX_VALUE;
        for (Integer leftNum : leftMap.keySet()) {
            for (Integer leftSum : leftMap.get(leftNum)) {
                Integer rightSum = rightMap.get(half - leftNum).floor((int) (sum >> 1) - leftSum);
                if (rightSum != null) {
                    int pick = leftSum + rightSum;
                    int rest = (int) (sum - pick);
                    ans = Math.min(ans, rest - pick);
                }
            }
        }
        return ans;
    }

    private static void process(int[] arr, int index, int end, int picked, int sum, Map<Integer, TreeSet<Integer>> map) {
        if (index == end) {
            TreeSet<Integer> set = map.getOrDefault(picked, new TreeSet<>());
            set.add(sum);
            map.put(picked, set);
        } else {
            process(arr, index + 1, end, picked, sum, map);
            process(arr, index + 1, end, picked + 1, sum + arr[index], map);
        }
    }
}
