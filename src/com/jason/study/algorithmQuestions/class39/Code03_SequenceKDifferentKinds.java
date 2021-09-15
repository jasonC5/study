package com.jason.study.algorithmQuestions.class39;

/**
 * // 来自百度
 * // 给定一个字符串str，和一个正数k
 * // str子序列的字符种数必须是k种，返回有多少子序列满足这个条件
 * // 已知str中都是小写字母
 * // 原始是取mod
 * // 本节在尝试上，最难的
 * // 搞出桶来，组合公式
 *
 * @author JasonC5
 */
public class Code03_SequenceKDifferentKinds {
    public static void main(String[] args) {
        String str = "acbbca";
        int k = 3;
        System.out.println(nums(str, k));
    }

    private static int nums(String str, int k) {
        int[] count = new int[26];
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            count[c - 'a']++;
        }
        ;
        //从出现过的字符中挑选k个，出现的次数任意
        return ways(count, 0, k);
    }

    private static int ways(int[] count, int index, int rest) {
        if (rest == 0) {
            return 1;
        }
        if (index == count.length) {
            return 0;
        }
        // 不要当前字符
        int ans = ways(count, index + 1, rest);
        // 要
        int c = count[index];
        ans += c == 0 ? 0 : (math(c) * ways(count, index + 1, rest - 1));
        return ans;
    }

    private static int math(int count) {
        // Cn1+Cn2+Cn3+……+Cnn = 2^n - 1
        return (1 << count) - 1;
    }
}
