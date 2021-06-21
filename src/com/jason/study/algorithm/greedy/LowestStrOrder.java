package com.jason.study.algorithm.greedy;

import java.util.Arrays;

/**
 * 给定一个有字符串组成的数组strs，必须吧所有的字符串拼起来，返回所有可能的拼接结果中，字典序最小的结果
 *
 * @author JasonC5
 */
public class LowestStrOrder {

    public static void main(String[] args) {
        String[] strs = {"aa", "ab", "b", "ba"};
        System.out.println(order(strs));
    }

    private static String order(String[] strs) {
        Arrays.sort(strs, (a, b) -> (a + b).compareTo(b + a));
        StringBuilder ans = new StringBuilder();
        for (String str : strs) {
            ans.append(str);
        }
        return ans.toString();
    }
}
