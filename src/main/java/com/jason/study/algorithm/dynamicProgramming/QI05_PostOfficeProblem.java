package com.jason.study.algorithm.dynamicProgramming;

import java.util.Arrays;

/**
 * 一条直线上有居民点，邮局只能建在居民点上。给定一个有序正数数组arr，
 * 每个值表示 居民点的一维坐标，再给定一个正数 num，表示邮局数量。
 * 选择num个居民点建立num个 邮局，使所有的居民点到最近邮局的总距离最短，返回最短的总距离
 * 【举例】
 * arr=[1,2,3,4,5,1000]，num=2。
 * 第一个邮局建立在 3 位置，第二个邮局建立在 1000 位置。那么 1 位置到邮局的距离 为 2， 2 位置到邮局距离为 1，3 位置到邮局的距离为 0，4 位置到邮局的距离为 1， 5 位置到邮局的距 离为 2，1000 位置到邮局的距离为 0。这种方案下的总距离为 6， 其他任何方案的总距离都不会 比该方案的总距离更短，所以返回6
 *
 * @author JasonC5
 */
public class QI05_PostOfficeProblem {

//    public static void main(String[] args) {
//        int[] arr = {1, 2, 3, 4, 5, 1000};
//        int num = 2;
//        System.out.println(postOfficeProblem(arr, num));
//    }


    // for test
    public static int[] randomSortedArray(int len, int range) {
        int[] arr = new int[len];
        for (int i = 0; i != len; i++) {
            arr[i] = (int) (Math.random() * range);
        }
        Arrays.sort(arr);
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int N = 30;
        int maxValue = 100;
        int testTime = 10000;
        System.out.println("test start");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N) + 1;
            int[] arr = randomSortedArray(len, maxValue);
            int num = (int) (Math.random() * N) + 1;
            int ans1 = min1(arr, num);
            int ans2 = min2(arr, num);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(num);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");

    }


    public static int min1(int[] arr, int num) {
        //1.给定一个数组，是居民点的位置，如果只有一个邮局，那么建在哪里所有点到邮局的距离最短？：中间点，奇数个就正中间节点，偶数个中间两个节点是一样的。
        //2.如果只有一个邮局，从左往右，加居民点进来，没加一个，总距离增加多少？：arr[n]-arr[n/2],
        //  如果之前是奇数个，那么加进来之后变成偶数个，之前的中间点还可以认为是中间点，前面的总距离不变，只需要加新加进来的点到中间点的距离
        //  如果之前是偶数个，那么认为之前的邮局放在了后面的中间点，加进来之后前面的总距离还是没变，只需要加新加进来的点到中间点的距离
        //由以上可以得到任意两个区间内，一个邮局，最小全局距离 distance[left][right]（left <= right left==right 时 距离为0）
        if (arr == null || num < 1 || arr.length < num) {
            return 0;
        }
        int length = arr.length;
        int[][] minDistance = new int[length + 1][length + 1];
        for (int left = 0; left < length; left++) {
            for (int right = left + 1; right < length; right++) {
                minDistance[left][right] = minDistance[left][right - 1] + arr[right] - arr[left + ((right - left) >> 1)];
            }
        }
        //3.多个邮局的时候如何处理，认为最后一个加进来的邮局分单一部分路程，前面的邮局分担的距离可以查得到
        //DP：从0开始到n结尾，有k个邮局的时候，最短路径
        int[][] dp = new int[length][num + 1];
        for (int i = 0; i < length; i++) {
            dp[i][1] = minDistance[0][i];
        }
        //依赖关系：最后一个邮局：邮局越多最短距离和越短，居民点越多最短距离和越长
        for (int k = 2; k <= num; k++) {
            for (int right = 1; right < length; right++) {
                int ans = Integer.MAX_VALUE;
                for (int i = 0; i < right; i++) {
                    ans = Math.min(ans, dp[i][k - 1] + minDistance[i + 1][right]);
                }
                dp[right][k] = ans;
            }
        }
        return dp[length - 1][num];
    }


    public static int min2(int[] arr, int num) {
        if (arr == null || num < 1 || arr.length < num) {
            return 0;
        }
        int length = arr.length;
        int[][] minDistance = new int[length + 1][length + 1];
        for (int left = 0; left < length; left++) {
            for (int right = left + 1; right < length; right++) {
                minDistance[left][right] = minDistance[left][right - 1] + arr[right] - arr[left + ((right - left) >> 1)];
            }
        }
        //3.多个邮局的时候如何处理，认为最后一个加进来的邮局分单一部分路程，前面的邮局分担的距离可以查得到
        //DP：从0开始到n结尾，有k个邮局的时候，最短路径
        int[][] dp = new int[length][num + 1];
        int[][] best = new int[length][num + 1];
        for (int i = 0; i < length; i++) {
            dp[i][1] = minDistance[0][i];
            best[i][1] = -1;
        }
        //依赖关系：最后一个邮局：邮局越多最短距离和越短，居民点越多最短距离和越长
        for (int k = 2; k <= num; k++) {
            for (int right = length - 1; right >= k/*邮局数量超过小区数答案就是0*/; right--) {
                int ans = Integer.MAX_VALUE;
                int bestChoose = -1;
                //划分值下界：左边的（少一个邮局的时候）上界：下边的（多一个小区的时候）
                for (int i = best[right][k - 1]; i <= (right + 1 == length ? length - 1 : best[right + 1][k]); i++) {
                    int distance = (i == -1 ? 0 : dp[i][k - 1]) + (i >= right ? 0 : minDistance[i + 1][right]);
                    if (distance <= ans) {
                        ans = distance;
                        bestChoose = i;
                    }
                }
                dp[right][k] = ans;
                best[right][k] = bestChoose;
            }
        }
        return dp[length - 1][num];
    }

}
