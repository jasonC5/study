package com.jason.study.algorithm.other;

/**
 * 两个串是否是互相旋转串
 * S1+S1  是否包含S2
 *
 * @author JasonC5
 */
public class KMP_02 {

    public static void main(String[] args) {
        String str1 = "chenjie";
        String str2 = "jiechen";
        System.out.println(isRotation(str1, str2));
    }

    /**
     * 是否旋转串
     *
     * @param str1
     * @param str2
     * @return
     */
    private static boolean isRotation(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }
        String v = str1 + str1;
        //v中包含所有str1旋转串的可能，只要str2是v的子串，则str1必是str2的旋转串
        return kmp(v, str2) != -1;
    }

    private static int kmp(String s1, String s2) {
        if (s1 == null || s2 == null || s2.length() == 0 || s1.length() < s2.length()) {
            return -1;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[] next = getNextArr(s2);
        int pointer1 = 0;
        int pointer2 = 0;
        while (pointer1 < s1.length() && pointer2 < s2.length()) {
            if (str1[pointer1] == str2[pointer2]) {
                pointer1++;
                pointer2++;
            } else if (pointer2 != 0) {
                pointer2 = next[pointer2];
            } else {
                pointer1++;
            }
        }
        return pointer2 == s2.length() ? pointer1 - pointer2 : -1;
    }

    private static int[] getNextArr(String str) {
        if (str.length() == 1) {
            return new int[]{-1};
        }
        char[] chars = str.toCharArray();
        int[] next = new int[str.length()];
        next[0] = -1;
        next[1] = 0;
        int i = 2; // 目前在哪个位置上求next数组的值
        int cn = 0; // 当前是哪个位置的值再和i-1位置的字符比较
        while (i < str.length()) {
            if (chars[cn] == chars[i]) {
                next[i++] = ++cn;
            } else if (cn == 0) {
                next[i++] = 0;
            } else {
                cn = next[cn];
            }
        }
        return next;
    }
}
