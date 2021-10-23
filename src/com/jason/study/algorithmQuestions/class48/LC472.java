package com.jason.study.algorithmQuestions.class48;

import com.jason.study.algorithmQuestions.class06.MaxXOR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一个 不含重复 单词的字符串数组 words ，编写一个程序，返回 words 中的所有 连接词 。
 * <p>
 * 连接词 的定义为：一个字符串完全是由至少两个给定数组中的单词组成的。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：words = ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]
 * 输出：["catsdogcats","dogcatsdog","ratcatdogcat"]
 * 解释："catsdogcats"由"cats", "dog" 和 "cats"组成;
 * "dogcatsdog"由"dog", "cats"和"dog"组成;
 * "ratcatdogcat"由"rat", "cat", "dog"和"cat"组成。
 * 示例 2：
 * <p>
 * 输入：words = ["cat","dog","catdog"]
 * 输出：["catdog"]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/concatenated-words
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC472 {

    public static class PrefixTreeNode {
        public boolean end;
        public PrefixTreeNode[] nexts;

        public PrefixTreeNode() {
            end = false;
            nexts = new PrefixTreeNode[26];
        }
    }

    public static List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> ans = new ArrayList<>();
        if (words == null || words.length < 3) {
            return ans;
        }
        // 从小到大排序
        Arrays.sort(words, (s1, s2) -> s1.length() - s2.length());
        PrefixTreeNode root = new PrefixTreeNode();
        for (String str : words) {
            char[] s = str.toCharArray();
            //若能匹配
            if (s.length > 0 && split(s, root, 0)) {
                ans.add(str);
            } else {
                insert(root, s);
            }
        }
        return ans;
    }

    private static void insert(PrefixTreeNode root, char[] s) {
        for (char c : s) {
            int idx = c - 'a';
            if (root.nexts[idx] == null) {
                root.nexts[idx] = new PrefixTreeNode();
            }
            root = root.nexts[idx];
        }
        root.end = true;
    }

    private static boolean split(char[] s, PrefixTreeNode root, int i) {
        boolean ans = false;
        if (i == s.length) {
            return true;
        }
        PrefixTreeNode node = root;
        for (int end = i; end < s.length; end++) {
            int idx = s[end] - 'a';
            if (node.nexts[idx] == null) {
                break;
            }
            node = node.nexts[idx];
            if (node.end && split(s, root, end + 1)) {
                ans = true;
                break;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String[] words = {"cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"};
        System.out.println(findAllConcatenatedWordsInADict(words));
    }
}
