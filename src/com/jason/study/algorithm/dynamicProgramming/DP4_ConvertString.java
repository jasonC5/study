package com.jason.study.algorithm.dynamicProgramming;

/**
 * 规定1和A对应，2和B对应，3和C对应……26和Z对应，那么一个数字字符串比如”111“就可以转化为：”AAA“ ”KA“ ”AK“给定一个只有数字字符组成的字符串str，返回有多少种转化结果
 *
 * @author JasonC5
 */
public class DP4_ConvertString {
    public static void main(String[] args) {
        String num = "1212454681102323";
        //对数器
        int ans0 = number(num);
        System.out.println(ans0);

        int ans1 = convert1(num);
        System.out.println(ans1);

        int ans2 = convert2(num);
        System.out.println(ans2);
    }

    //对数器
    public static int number(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return process1(str.toCharArray(), 0);
    }

    // str[0..i-1]转化无需过问
    // str[i.....]去转化，返回有多少种转化方法
    public static int process1(char[] str, int i) {
        if (i == str.length) {
            return 1;
        }
        // i没到最后，说明有字符
        if (str[i] == '0') { // 之前的决定有问题
            return 0;
        }
        // str[i] != '0'
        // 可能性一，i单转
        int ways = process1(str, i + 1);
        if (i + 1 < str.length && (str[i] - '0') * 10 + str[i + 1] - '0' < 27) {
            ways += process1(str, i + 2);
        }
        return ways;
    }


    //暴力尝试

    private static int convert1(String num) {
        if (num == null || num.length() == 0) {
            return 0;
        }
        char[] chars = num.toCharArray();
        return process(chars, 0);
    }

    private static int process(char[] chars, int idx) {
        int length = chars.length;
        if (idx == length) {
            return 1;
        }
        if (chars[idx] == '0') {
            return 0;
        }
        int ways = process(chars, idx + 1);
        if (idx + 1 != length && ((chars[idx] - '0') * 10 + (chars[idx + 1] - '0')) < 27) {
            ways += process(chars, idx + 2);
        }
        return ways;
    }
    // 改dp

    private static int convert2(String num) {
        if (num == null || num.length() == 0) {
            return 0;
        }
        char[] chars = num.toCharArray();
        int length = chars.length;
        //从i到最后，有多少个可能的结果
        int[] dp = new int[length + 1];
        dp[length] = 1;
        for (int idx = length - 1; idx >= 0; idx--) {
            if (chars[idx] == '0') {
                dp[idx] = 0;
            } else {
                int ways = dp[idx + 1];
                if (idx + 1 != length && ((chars[idx] - '0') * 10 + (chars[idx + 1] - '0')) < 27) {
                    ways += dp[idx + 2];
                }
                dp[idx] = ways;
            }
        }
        return dp[0];
    }
}
