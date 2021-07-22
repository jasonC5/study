package com.jason.study.algorithm.other;

/**
 * 给定一个字符串，只能在后面加字符，求需要加多少个字符才能整体变成回文串
 * 右边界是回文的右边界
 * Manacher扩到右边界的时候，就找到了中心点,然后，减去包含结尾的回文串,剩下的逆序，就是需要加上的串
 *
 * @author JasonC5
 */
public class Manacher_02 {

    public static char[] manacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i != res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }

    public static void main(String[] args) {
        String str1 = "11abcd123321";
        System.out.println(shortestEnd(str1));
    }

    public static String shortestEnd(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        char[] str = manacherString(s);
        //回文半径
        int[] arr = new int[str.length];
        //最大右边界(不包括自己)，和对应的中心点
        int c = -1, r = -1, endStr = -1;
        for (int i = 0; i < str.length; i++) {
            //找到包含最后一个字符的逆序串
            int len = r > i ? Math.min(arr[2 * c - i], r - i) : 1;
            while (i + len < str.length && i - len > -1 && str[i + len] == str[i - len]) {
                len++;
            }
            arr[i] = len;
            if (i + arr[i] > r) {
                r = i + arr[i];
                c = i;
            }
            //到右边界了
            if (r == str.length) {
                endStr = arr[i] - 1;
                break;
            }
        }
        char[] add = new char[s.length() - endStr];
        for (int i = 0; i < add.length; i++) {
            add[add.length - i - 1] = str[2 * i + 1];
        }
        return new String(add);
    }
}
