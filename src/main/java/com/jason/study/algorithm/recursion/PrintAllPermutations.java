package com.jason.study.algorithm.recursion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 1.打印一个字符串的全排列
 * 2.要求不出现重复的排列 boolean[255]    --剪枝
 *
 * @author JasonC5
 */
public class PrintAllPermutations {

    public static void main(String[] args) {
        String s = "111";
        List<String> subsequence = permutations1(s);
        for (String sub : subsequence) {
            System.out.println(sub);
        }
        System.out.println("##################");
        subsequence = permutations2(s);
        for (String sub : subsequence) {
            System.out.println(sub);
        }
    }

    /**
     * 不去重全排列
     *
     * @param str
     * @return
     */
    private static List<String> permutations1(String str) {
        List<String> ans = new ArrayList();
        if (str == null || str.length() == 0) {
            return ans;
        }
        //思路，该位置和后面的每个位置做交换，完成后
        char[] chars = str.toCharArray();
        process(chars, 0, ans);
        return ans;
    }

    private static void process(char[] chars, int idx, List<String> ans) {
        if (idx == chars.length) {
            ans.add(new String(chars));
            return;
        }
        for (int i = idx; i < chars.length; i++) {
            swap(chars, idx, i);
            process(chars, idx + 1, ans);
            swap(chars, idx, i);
        }
    }

    private static void swap(char[] chars, int idx1, int idx2) {
        char tmp = chars[idx1];
        chars[idx1] = chars[idx2];
        chars[idx2] = tmp;
    }

    /**
     * 去重全排列
     * @param str
     * @return
     */
    private static List<String> permutations2(String str) {
        List<String> ans = new ArrayList();
        if (str == null || str.length() == 0) {
            return ans;
        }
        //思路，该位置和后面的每个位置做交换，完成后
        char[] chars = str.toCharArray();
        process2(chars, 0, ans);
        return ans;
    }

    private static void process2(char[] chars, int idx, List<String> ans) {
        if (idx == chars.length) {
            ans.add(new String(chars));
            return;
        }
        boolean[] visited = new boolean[255];
        for (int i = idx; i < chars.length; i++) {
            if (visited[chars[i]]) {//已经处理过了,剪枝
                continue;
            }
            visited[chars[i]] = true;
            swap(chars, idx, i);
            process2(chars, idx + 1, ans);
            swap(chars, idx, i);
        }
    }

}
