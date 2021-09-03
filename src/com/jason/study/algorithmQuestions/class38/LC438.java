package com.jason.study.algorithmQuestions.class38;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
 * <p>
 * 异位词 指字母相同，但排列不同的字符串。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: s = "cbaebabacd", p = "abc"
 * 输出: [0,6]
 * 解释:
 * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
 * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
 *  示例 2:
 * <p>
 * 输入: s = "abab", p = "ab"
 * 输出: [0,1,2]
 * 解释:
 * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
 * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
 * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
 *  
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-all-anagrams-in-a-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC438 {

    static class Info {
        public int[] target;
        public int distance;

        public Info(String p) {
            distance = p.length();
            target = new int[26];
            for (char c : p.toCharArray()) {
                target[c - 'a']++;
            }
        }
    }

    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> ans = new ArrayList<>();
        if (s == null || p == null || s.length() < p.length()) {
            return ans;
        }
        char[] str = s.toCharArray();
        Info info = new Info(p);
        int length = str.length;
        int pl = p.length();
        for (int i = 0; i < pl - 1; i++) {
            int count = info.target[str[i] - 'a']--;
            if (count > 0) {
                info.distance--;
            }
        }
        for (int end = pl - 1, start = 0; end < length; end++, start++) {
            //扩
            int count = info.target[str[end] - 'a']--;
            if (count > 0) {
                info.distance--;
            }
            if (info.distance == 0) {
                ans.add(start);
            }
            //缩
            if (++info.target[str[start] - 'a'] > 0){
                info.distance++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String s = "cbaebabacd", p = "abc";
        System.out.println(findAnagrams(s, p));
    }
}
