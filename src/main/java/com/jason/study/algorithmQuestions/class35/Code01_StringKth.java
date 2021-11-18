package com.jason.study.algorithmQuestions.class35;

import java.util.ArrayList;
import java.util.List;

/**
 * // 给定一个长度len，表示一共有几位
 * // 所有字符都是小写(a~z)，可以生成长度为1，长度为2，
 * // 长度为3...长度为len的所有字符串
 * // 如果把所有字符串根据字典序排序，每个字符串都有所在的位置。
 * // 给定一个字符串str，给定len，请返回str是总序列中的第几个
 * // 比如len = 4，字典序的前几个字符串为:
 * // a aa aaa aaaa aaab ... aaaz ... azzz b ba baa baaa ... bzzz c ...
 * // a是这个序列中的第1个，bzzz是这个序列中的第36558个
 *
 * @author JasonC5
 */
public class Code01_StringKth {

    public static int kth(String s, int len) {
        if (s == null || s.length() == 0 || s.length() > len){
            return -1;
        }
        char[] chars = s.toCharArray();
        int ans = 0;
        for (char c : chars) {
            ans += f(c, --len);
        }
        return ans;
    }

    private static int f(char c, int len) {
        //除了每个位置上都有字母，还有可能为空的
//        return ((int) Math.pow(26, len) + 1) * (c - 'a');
        int ans = 1;
        int base = 26;
        for (int i = 1; i <= len; i++) {
            ans += base;
            base *= 26;
        }
        return ans * (c - 'a') + 1;
    }


    // 为了测试
    public static List<String> all(int len) {
        List<String> ans = new ArrayList<>();
        for (int i = 1; i <= len; i++) {
            char[] path = new char[i];
            process(path, 0, ans);
        }
        return ans;
    }

    // 为了测试
    public static void process(char[] path, int index, List<String> ans) {
        if (index == path.length) {
            ans.add(String.valueOf(path));
        } else {
            for (char c = 'a'; c <= 'z'; c++) {
                path[index] = c;
                process(path, index + 1, ans);
            }
        }
    }

    public static void main(String[] args) {
        int len = 4;
        // 暴力方法得到所有字符串
        List<String> ans = all(len);
        // 根据字典序排序，所有字符串都在其中
        ans.sort((a, b) -> a.compareTo(b));

        String test = "bzzz";
        // 根据我们的方法算出test是第几个？
        // 注意我们算出的第几个，是从1开始的
        // 而下标是从0开始的，所以变成index，还需要-1
        int index = kth(test, len) - 1;
        // 验证
        System.out.println(ans.get(index));
    }

}
