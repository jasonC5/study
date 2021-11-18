package com.jason.study.algorithm.dynamicProgramming;

/**
 * 给定一个整型数组 arr，数组中的每个值都为正数，表示完成一幅画作需要的时间，再 给定 一个整数 num，表示画匠的数量，每个画匠只能画连在一起的画作。所有的画家 并行工作，请 返回完成所有的画作需要的最少时间。
 * 【举例】
 * arr=[3,1,4]，num=2。
 * 最好的分配方式为第一个画匠画 3 和 1，所需时间为 4。第二个画匠画 4，所需时间 为 4。 因为并行工作，所以最少时间为 4。如果分配方式为第一个画匠画 3，所需时 间为 3。第二个画 匠画 1 和 4，所需的时间为 5。那么最少时间为 5，显然没有第一 种分配方式好。所以返回 4。
 * arr=[1,1,1,4,3]，num=3。
 * 最好的分配方式为第一个画匠画前三个 1，所需时间为 3。第二个画匠画 4，所需时间 为 4。 第三个画匠画 3，所需时间为 3。返回 4。
 * --和第一题的区别：1只拆成左右两部分，这题可以拆成k部分
 * --从左往右的尝试模型	dp[i][j]  arr[0~i]画   j个画家
 * --记录最后一个画家获取位置的切割点
 *
 * @author JasonC5
 */
public class QI04_SplitArrayLargestSum {


    public static int[] randomArray(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int N = 100;
        int maxValue = 100;
        int testTime = 10000;
        System.out.println("test start");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N) + 1;
            int M = (int) (Math.random() * N) + 1;
            int[] arr = randomArray(len, maxValue);
            int ans1 = splitArray1(arr, M);
            int ans2 = splitArray2(arr, M);
            int ans3 = splitArray3(arr, M);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.print("arr : ");
                printArray(arr);
                System.out.println("M : " + M);
                System.out.println("ans1 : " + ans1);
                System.out.println("ans2 : " + ans2);
                System.out.println("ans3 : " + ans3);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("test end");
    }

    // 不优化枚举的动态规划方法，O(N^2 * K)
    public static int splitArray1(int[] nums, int k) {
        int length = nums.length;
        int[] sumArr = new int[length + 1];
        for (int i = 0; i < length; i++) {
            sumArr[i + 1] = sumArr[i] + nums[i];
        }
        //划分值：最后一个画家，和前面的画家，画的话的分隔点
        //0~i几幅画，几个人画
        int[][] dp = new int[length][k + 1];
        //dp[i][0]  没有画家，没意义
        //就一幅画
        for (int i = 1; i <= k; i++) {
            dp[0][i] = nums[0];
        }
        //一个画家
        for (int i = 0; i < length; i++) {
            dp[i][1] = sumArr[i + 1];
        }
        for (int i = 1; i < length; i++) {//画
            for (int j = 2; j <= k; j++) {//画家
                int ans = Integer.MAX_VALUE;
                for (int latch = 0; latch <= i; latch++) {//前面的画家完成0~latch副 画，最后一个商家完成latch+1~i副画
                    int leftCost = dp[latch][j - 1];
                    int rightCost = sumArr[i + 1] - sumArr[latch + 1];
                    int cost = Math.max(leftCost, rightCost);
                    if (cost <= ans) {
                        ans = cost;
                    }
                }
                dp[i][j] = ans;
            }
        }
        return dp[length - 1][k];
    }

    public static int splitArray2(int[] nums, int k) {
        int length = nums.length;
        int[] sumArr = new int[length + 1];
        for (int i = 0; i < length; i++) {
            sumArr[i + 1] = sumArr[i] + nums[i];
        }
        //划分值：最后一个画家，和前面的画家，画的话的分隔点
        //0~i几幅画，几个人画
        int[][] dp = new int[length][k + 1];
        int[][] best = new int[length][k + 1];
        //dp[i][0]  没有画家，没意义
        //就一幅画，全都给最后一个画家
        for (int j = 1; j <= k; j++) {
            dp[0][j] = nums[0];
            best[0][j] = -1;
        }
        //一个画家,全都给最后一个画家
        for (int i = 0; i < length; i++) {
            dp[i][1] = sumArr[i + 1];
            best[i][1] = -1;
        }
        //依赖左下，从左往右，从下往上推（第一列已经有了）
        for (int j = 2; j <= k; j++) {//画家
            for (int i = length - 1; i >= 1; i--) {//画
                int ans = Integer.MAX_VALUE;
                int bestLatch = -1;
                //起始尝试点：左，终止尝试点：有下就是下，没下就是length
                for (int latch = best[i][j - 1]; latch <= (i + 1 == length ? length - 1 : best[i + 1][j]); latch++) {//前面的画家完成0~latch副 画，最后一个商家完成latch+1~i副画
                    //划分点在-1的时候，全部由最后一个画家来干
                    int leftCost = latch == -1 ? 0 : dp[latch][j - 1];
                    //划分点到i的时候全部由前面的画家来干，但是加不加这个判断不影响结果，不加更快
                    int rightCost = /*latch == i ? 0 :*/ sumArr[i + 1] - sumArr[latch + 1];
                    int cost = Math.max(leftCost, rightCost);
                    if (cost < ans) {//改为<=就错，试就对了
                        ans = cost;
                        bestLatch = latch;
                    }
                }
                dp[i][j] = ans;
                best[i][j] = bestLatch;
            }
        }
        return dp[length - 1][k];
    }

    //最优解：二分法
    //在某个时间内能完成上述任务，所需要的画家数，将范围逐渐二分到答案上O(n * log sum)
    public static int splitArray3(int[] nums, int k) {
        //什么时候第一次<k，上一个就是答案
        long sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        long l = 0, r = sum;
        long ans = 0;
        while (l <= r) {
            long mid = (l + r) >> 1;
            long need = getNeedParts(nums, mid);
            if (need <= k) {
                r = mid - 1;
                ans = mid;
            } else {
                l = mid + 1;
            }
        }
        return (int) ans;
    }

    private static long getNeedParts(int[] nums, long time) {
        for (int num : nums) {
            if (num > time) {
                //其中有一个任务，比总耗时还多，永远完成不了
                return Integer.MAX_VALUE;
            }
        }
        int need = 1;
        long surTime = time;
        for (int num : nums) {
            if (surTime >= num) {
                surTime -= num;
            } else {
                surTime = time - num;
                need++;
            }
        }
        return need;
    }
}
