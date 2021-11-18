package com.jason.study.algorithmQuestions.class36;

import java.util.Stack;
import java.util.TreeSet;

/**
 * // 来自腾讯
 * // 给定一个字符串str，和一个正数k
 * // 返回长度为k的所有子序列中，字典序最大的子序列
 *
 * @author JasonC5
 */
public class Code10_MaxKLenSequence {

    // 为了测试
    public static String test(String str, int k) {
        if (k <= 0 || str.length() < k) {
            return "";
        }
        TreeSet<String> ans = new TreeSet<>();
        process(0, 0, str.toCharArray(), new char[k], ans);
        return ans.last();
    }

    // 为了测试
    public static void process(int si, int pi, char[] str, char[] path, TreeSet<String> ans) {
        if (si == str.length) {
            if (pi == path.length) {
                ans.add(String.valueOf(path));
            }
        } else {
            process(si + 1, pi, str, path, ans);
            if (pi < path.length) {
                path[pi] = str[si];
                process(si + 1, pi + 1, str, path, ans);
            }
        }
    }

    // 为了测试
    public static String randomString(int len, int range) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * range) + 'a');
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int n = 12;
        int r = 5;
        int testTime = 10000;
        System.out.println("start");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * (n + 1));
            String str = randomString(len, r);
            int k = (int) (Math.random() * (str.length() + 1));
            String ans1 = maxString(str, k);
            String ans2 = test(str, k);
            if (!ans1.equals(ans2)) {
                System.out.println("error！");
                System.out.println(str);
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("finished");
    }

    private static String maxString(String str, int k) {
        //单调栈
        int length = str.length();
        if (k <= 0 || length < k) {
            return "";
        }
        char[] chars = str.toCharArray();
        char[] stack = new char[length];
        int size = 0;
        for (int i = 0; i < length; i++) {
            while (size > 0 && chars[i] > stack[size - 1] && length - i + size > k) {
                size--;
            }
            if (length - i + size == k) {
                return String.valueOf(stack, 0, size) + str.substring(i);
            }
            stack[size++] = chars[i];
        }
        return String.valueOf(stack, 0, k);
    }
}
