package com.jason.study.algorithmQuestions.class33;

/**
 * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
 * <p>
 * 注意：若s 和 t中每个字符出现的次数都相同，则称s 和 t互为字母异位词。
 * <p>
 * <p>
 * <p>
 * 示例1:
 * <p>
 * 输入: s = "anagram", t = "nagaram"
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: s = "rat", t = "car"
 * 输出: false
 * <p>
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-anagram
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC242 {
    //    public boolean isAnagram(String s, String t) {
//        if (s.length() != t.length()) {
//            return false;
//        }
//        int[] count = new int[26];
//        for (int i = 0; i < s.length(); i++) {
//            count[s.charAt(i) - 'a']++;
//        }
//        for (int i = 0; i < t.length(); i++) {
//            if (--count[t.charAt(i) - 'a'] < 0){
//                return false;
//            }
//        }
//        return true;
//    }
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] count = new int[26];
        char[] chars = s.toCharArray();
        for (char c : chars) {
            count[c - 'a']++;
        }
        chars = t.toCharArray();
        for (char c : chars) {
            if (--count[c - 'a'] < 0){
                return false;
            }
        }
        return true;
    }
}
