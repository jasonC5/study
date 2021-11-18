package com.jason.study.algorithmQuestions.class36;

/**
 * // 给定两个字符串s1和s2
 * // 返回在s1中有多少个子串等于s2
 * kmp
 * next数组，多加一位
 *
 * @author JasonC5
 */
public class Code03_MatchCount {

    public static int sa(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() < s2.length()) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        return count(str1, str2);
    }

    private static int count(char[] str1, char[] str2) {
        //求str2的next数组
        int[] next = getNexyArray(str2);
        int x = 0;
        int y = 0;
        int count = 0;
        while (x < str1.length) {
            if (str1[x] == str2[y]) {
                x++;
                y++;
                if (y == str2.length) {
                    count++;
                    y = next[y];
                }
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }
        return count;
    }

    //KMP 求 next 数组 前缀 后缀
    private static int[] getNexyArray(char[] str2) {
        if (str2.length == 1) {
            return new int[]{-1, 0};
        }
        int[] next = new int[str2.length + 1];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0;
        while (i < next.length) {
            if (str2[i - 1] == str2[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                //回到上一个可能点重新匹配
                cn = next[cn];
            } else {
                //没有可能点了，重头匹配
                next[i++] = 0;
            }
        }
        return next;
    }

}
