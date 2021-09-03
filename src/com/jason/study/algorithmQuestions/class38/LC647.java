package com.jason.study.algorithmQuestions.class38;

/**
 * 给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
 * <p>
 * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入："abc"
 * 输出：3
 * 解释：三个回文子串: "a", "b", "c"
 * 示例 2：
 * <p>
 * 输入："aaa"
 * 输出：6
 * 解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa"
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/palindromic-substrings
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC647 {
    public static void main(String[] args) {
        System.out.println(countSubstrings("abc"));
    }

    public static int countSubstrings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        //回文半径
        int[] arr = new int[str.length];
        //最大右边界(不包括自己)，和对应的中心点
        int c = -1, r = -1;
        int count = 0;
        for (int i = 0; i < str.length; i++) {
            int len = r > i ? Math.min(arr[2 * c - i], r - i) : 1;
            while (i + len < str.length && i - len > -1 && str[i + len] == str[i - len]) {
                len++;
            }
            arr[i] = len;
            if (i + arr[i] > r) {
                r = i + arr[i];
                c = i;
            }
            //每一个回文半径，能产生多少个回文子串
            count += arr[i] / 2;
        }
        return count;
    }

    private static char[] manacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i != res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }


}
