package com.jason.study.algorithm.recursion;

import java.util.*;

/**
 * 打印一个字符串的全部子序列
 *
 * @author JasonC5
 */
public class PrintAllSubsequence {

    public static void main(String[] args) {
        String s = "aaa";
        Collection<String> subsequence = subString(s);
        for (String sub : subsequence) {
            System.out.println(sub);
        }
        System.out.println("##################");
        subsequence = subString2(s);
        for (String sub : subsequence) {
            System.out.println(sub);
        }
    }

    /**
     * 去重
     * @param str
     * @return
     */
    private static Set<String> subString2(String str) {
        char[] chars = str.toCharArray();
        Set<String> ans = new HashSet<>();
        process(chars, 0, "", ans);
        return ans;
    }

    private static List<String> subString(String str) {
        char[] chars = str.toCharArray();
        List<String> ans = new ArrayList<>();
        process(chars, 0, "", ans);
        return ans;
    }

    private static void process(char[] chars, int idx, String pre, Collection<String> ans) {
        if (idx == chars.length) {
            ans.add(pre);
            return;
        }
        char c = chars[idx];
        process(chars, idx + 1, pre, ans);
        process(chars, idx + 1, pre + c, ans);

    }

}
