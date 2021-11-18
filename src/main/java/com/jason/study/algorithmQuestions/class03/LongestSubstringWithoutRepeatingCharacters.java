package com.jason.study.algorithmQuestions.class03;

import java.util.HashMap;
import java.util.Map;

/**
 * 求一个字符串中，最长无重复字符子串长度
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 * --以i结尾推出多远不重复【O(n^2)】
 * --前面的结论帮助后面的推理：
 * 1.这个位置上的元素上次出现的位置（先全部标成-1，减少判断，还能算对）
 * 2.前一个位置最长长度（前一个位置推到的距离）【可以空间压缩】
 *
 * @author JasonC5
 */
public class LongestSubstringWithoutRepeatingCharacters {

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("au"));
    }

    public static int lengthOfLongestSubstring(String s) {
        //2个参数：
        //  1.前一位往前最多推到哪里
        //  2.当前位置上的元素，上次出现的位置
        int preIndex = 0;
        Map<Character, Integer> lastIndex = new HashMap<>();
        char[] chars = s.toCharArray();
        int maxLen = 0;
        for (int index = 0; index < chars.length; index++) {
//            int p1 = 1 + preIndex;
//            int p2 = index - lastIndex.getOrDefault(chars[index], -1);
//            preIndex = Math.min(p1, p2);
//            maxLen = Math.max(maxLen, preIndex);
            maxLen = Math.max(maxLen, preIndex = Math.min(1 + preIndex, index - lastIndex.getOrDefault(chars[index], -1)));
            lastIndex.put(chars[index], index);
        }
        return maxLen;
    }


}
