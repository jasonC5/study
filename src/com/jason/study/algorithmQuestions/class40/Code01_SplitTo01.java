package com.jason.study.algorithmQuestions.class40;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * // 腾讯
 * // 分裂问题
 * // 一个数n，可以分裂成一个数组[n/2, n%2, n/2]
 * // 这个数组中哪个数不是1或者0，就继续分裂下去
 * // 比如 n = 5，一开始分裂成[2, 1, 2]
 * // [2, 1, 2]这个数组中不是1或者0的数，会继续分裂下去，比如两个2就继续分裂
 * // [2, 1, 2] -> [1, 0, 1, 1, 1, 0, 1]
 * // 那么我们说，5最后分裂成[1, 0, 1, 1, 1, 0, 1]
 * // 每一个数都可以这么分裂，在最终分裂的数组中，假设下标从1开始
 * // 给定三个数n、l、r，返回n的最终分裂数组里[l,r]范围上有几个1
 * // n <= 2 ^ 50，n是long类型
 * // r - l <= 5000，l和r是int类型
 * // 我们的课加个码:
 * // n是long类型随意多大都行
 * // l和r也是long类型随意多大都行，但要保证l<=r
 *
 * @author JasonC5
 */
public class Code01_SplitTo01 {
    // 为了测试
    // 彻底生成n的最终分裂数组返回
    public static ArrayList<Integer> test(long n) {
        ArrayList<Integer> arr = new ArrayList<>();
        process(n, arr);
        return arr;
    }

    public static void process(long n, ArrayList<Integer> arr) {
        if (n == 1 || n == 0) {
            arr.add((int) n);
        } else {
            process(n / 2, arr);
            arr.add((int) (n % 2));
            process(n / 2, arr);
        }
    }

    public static void main(String[] args) {
        long num = 671;
        ArrayList<Integer> ans = test(num);
        int testTime = 10000;
        System.out.println("test start");
        for (int i = 0; i < testTime; i++) {
            int a = (int) (Math.random() * ans.size()) + 1;
            int b = (int) (Math.random() * ans.size()) + 1;
            int l = Math.min(a, b);
            int r = Math.max(a, b);
            int ans1 = 0;
            for (int j = l - 1; j < r; j++) {
                if (ans.get(j) == 1) {
                    ans1++;
                }
            }
            long ans2 = nums1(num, l, r);
            long ans3 = nums2(num, l, r);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("error!");
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("==============");
            }
        }
        System.out.println("test finished");
        System.out.println("==============");

        System.out.println("performance test start");
        num = (2L << 50) + 22517998136L;
        long l = 30000L;
        long r = 800000200L;
        long start;
        long end;
        start = System.currentTimeMillis();
        System.out.println("nums1 result : " + nums1(num, l, r));
        end = System.currentTimeMillis();
        System.out.println("nums1 cost(ms) : " + (end - start));

        start = System.currentTimeMillis();
        System.out.println("nums2 result : " + nums2(num, l, r));
        end = System.currentTimeMillis();
        System.out.println("nums2 cost(ms) : " + (end - start));
        System.out.println("performance test end");
        System.out.println("==============");

        System.out.println("nums2 time test start");
        num = (2L << 55) + 22517998136L;
        l = 30000L;
        r = 643100000200L;
        start = System.currentTimeMillis();
        System.out.println("nums2 result : " + nums2(num, l, r));
        end = System.currentTimeMillis();
        System.out.println("nums2 cost(ms) : " + (end - start));
        System.out.println("nums2 time test finished");
        System.out.println("==============");

    }

    //[n/2, n%2, n/2]
    private static long nums2(long num, long l, long r) {
        Map<Long, Long> sizeMap = new HashMap<>();
        getSize(num, sizeMap);
        Map<Long, Long> oneCountMap = new HashMap<>();
        getOneCount(num, oneCountMap);
        return dp(num, sizeMap, oneCountMap, l, r);
    }

    private static long dp(long num, Map<Long, Long> sizeMap, Map<Long, Long> oneCountMap, long l, long r) {
        if ((num & 1) == num) {//如果只有一位，直接返回是不是1
            return num & 1;
        }
        long half = sizeMap.get(num >> 1);//一半长度
        long all = (half << 1) + 1;//全部长度
        long mid = num & 1;//中间位置上是不是1
        if (l == 1 && r >= all) {//全包
            return oneCountMap.get(num);
        } else {//没全包
            mid = (l > half + 1 || r < half + 1) ? 0 : mid;
            long left = l <= half ? dp(num >> 1, sizeMap, oneCountMap, l, Math.min(half, r)) : 0;
            long right = r > half + 1 ? dp(num >> 1, sizeMap, oneCountMap, Math.max(1, l - half - 1), r - half - 1) : 0;
            return left + mid + right;
        }
    }

    private static long getOneCount(long num, Map<Long, Long> oneCountMap) {
        long count = (num & 1) == num ? (num & 1) : ((num & 1) + (getOneCount(num >> 1, oneCountMap) << 1));
        oneCountMap.put(num, count);
        return count;
//        if (num == 1 || num == 0) {
//            long count =  num == 1 ? 1 : 0;
//            oneCountMap.put(num, count);
//            return count;
//        }
//        long count = (getOneCount(num / 2, oneCountMap) << 1) + (num & 1);
//        oneCountMap.put(num, count);
//        return count;
    }

    private static long getSize(long num, Map<Long, Long> sizeMap) {
        if ((num & 1) == num) {
            sizeMap.put(num, 1L);
            return 1;
        }
        long size = 1 + (getSize(num >> 1, sizeMap) << 1);
        sizeMap.put(num, size);
        return size;
    }

    private static long nums1(long num, long l, long r) {
        if ((num & 1) == num) {//如果只有一位，直接返回是不是1
            return num & 1;
        }
        //如果不止一位，那么分成三部分，左 中 右，先求整体长度
        long half = getSize(num >> 1);//中间位置
        //分成三部分，左中右
        long left = l <= half ? nums1(num >> 1, l, Math.min(half, r)) : 0;
        long mid = (l <= half + 1 && r >= half + 1) ? (num & 1) : 0;
        long right = r > half + 1 ? nums1(num >> 1, Math.max(1, l - half - 1), r - half - 1) : 0;
        return left + mid + right;
    }

    private static long getSize(long num) {
        if ((num & 1) == num) {
            return 1;
        }
        return 1 + (getSize(num >> 1) << 1);
    }
}
