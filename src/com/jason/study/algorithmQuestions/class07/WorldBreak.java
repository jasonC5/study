package com.jason.study.algorithmQuestions.class07;

import java.util.HashSet;

/**
 * 假设所有字符都是小写字母
 * 大字符串是str
 * arr是去重的单词表, 每个单词都不是空字符串且可以使用任意次.
 * 使用arr中的单词有多少种拼接str的方式. 返回方法数.
 * --从左往右模型
 * --前缀树
 * --记忆化搜索
 *
 * @author JasonC5
 */
public class WorldBreak {

    // 以下的逻辑都是为了测试
    public static class RandomSample {
        public String str;
        public String[] arr;

        public RandomSample(String s, String[] a) {
            str = s;
            arr = a;
        }
    }

    // 随机样本产生器
    public static RandomSample generateRandomSample(char[] candidates, int num, int len, int joint) {
        String[] seeds = randomSeeds(candidates, num, len);
        HashSet<String> set = new HashSet<>();
        for (String str : seeds) {
            set.add(str);
        }
        String[] arr = new String[set.size()];
        int index = 0;
        for (String str : set) {
            arr[index++] = str;
        }
        StringBuilder all = new StringBuilder();
        for (int i = 0; i < joint; i++) {
            all.append(arr[(int) (Math.random() * arr.length)]);
        }
        return new RandomSample(all.toString(), arr);
    }

    public static String[] randomSeeds(char[] candidates, int num, int len) {
        String[] arr = new String[(int) (Math.random() * num) + 1];
        for (int i = 0; i < arr.length; i++) {
            char[] str = new char[(int) (Math.random() * len) + 1];
            for (int j = 0; j < str.length; j++) {
                str[j] = candidates[(int) (Math.random() * candidates.length)];
            }
            arr[i] = String.valueOf(str);
        }
        return arr;
    }


    public static void main(String[] args) {
        char[] candidates = {'a', 'b'};
        int num = 20;
        int len = 4;
        int joint = 5;
        int testTimes = 30000;
        boolean testResult = true;
        for (int i = 0; i < testTimes; i++) {
            RandomSample sample = generateRandomSample(candidates, num, len, joint);
            int ans1 = ways1(sample.str, sample.arr);
            int ans2 = ways2(sample.str, sample.arr);
            int ans3 = ways3(sample.str, sample.arr);
            int ans4 = ways4(sample.str, sample.arr);
            if (ans1 != ans2 || ans3 != ans4 || ans2 != ans4) {
                testResult = false;
            }
        }
        System.out.println(testTimes + "finished ? " + testResult);
    }

    public static int ways1(String str, String[] arr) {
        if (str == null || str.length() == 0 || arr == null || arr.length == 0) {
            return 0;
        }
        HashSet<String> map = new HashSet<>();
        for (String s : arr) {
            map.add(s);
        }
        return f(str, map, 0);
    }

    private static int f(String str, HashSet<String> map, int index) {
        if (index == str.length()) {
            return 1;
        }
        int ways = 0;
        for (int i = index; i < str.length(); i++) {
            String preStr = str.substring(index, i + 1);
            if (map.contains(preStr)) {
                ways += f(str, map, i + 1);
            }
        }
        return ways;
    }

    public static int ways2(String str, String[] arr) {
        if (str == null || str.length() == 0 || arr == null || arr.length == 0) {
            return 0;
        }
        HashSet<String> map = new HashSet<>();
        for (String s : arr) {
            map.add(s);
        }
        int length = str.length();
        int[] dp = new int[length + 1];
        dp[length] = 1;
        for (int i = length - 1; i >= 0; i--) {
            int ways = 0;
            for (int j = i; j < str.length(); j++) {
                String preStr = str.substring(i, j + 1);
                if (map.contains(preStr)) {
                    ways += f(str, map, j + 1);
                }
            }
            dp[i] = ways;
        }
        return dp[0];
    }

    public static class PrefixTreeNode {
        public PrefixTreeNode[] nexts;
        public boolean end;

        public PrefixTreeNode() {
            nexts = new PrefixTreeNode[26];
            end = false;
        }
    }

    public static int ways3(String str, String[] arr) {
        if (str == null || str.length() == 0 || arr == null || arr.length == 0) {
            return 0;
        }
        // 组装前缀树
        PrefixTreeNode root = new PrefixTreeNode();
        for (String s : arr) {
            char[] chars = s.toCharArray();
            PrefixTreeNode reader = root;
            for (char c : chars) {
                int index = c - 'a';
                if (reader.nexts[index] == null) {
                    reader.nexts[index] = new PrefixTreeNode();
                }
                reader = reader.nexts[index];
            }
            reader.end = true;
        }
        return g(str.toCharArray(), root, 0);
    }

    private static int g(char[] str, PrefixTreeNode prefixTree, int index) {
        if (str.length == index) {
            return 1;
        }
        int ways = 0;
        PrefixTreeNode cur = prefixTree;
        for (int i = index; i < str.length; i++) {
            int path = str[i] - 'a';
            if (cur.nexts[path] != null) {
                cur = cur.nexts[path];
                if (cur.end) {
                    ways += g(str, prefixTree, i + 1);
                }
            } else {
                break;
            }
        }
        return ways;
    }

    public static int ways4(String str, String[] arr) {
        if (str == null || str.length() == 0 || arr == null || arr.length == 0) {
            return 0;
        }
        // 组装前缀树
        PrefixTreeNode root = new PrefixTreeNode();
        for (String s : arr) {
            char[] chars = s.toCharArray();
            PrefixTreeNode reader = root;
            for (char c : chars) {
                int index = c - 'a';
                if (reader.nexts[index] == null) {
                    reader.nexts[index] = new PrefixTreeNode();
                }
                reader = reader.nexts[index];
            }
            reader.end = true;
        }
        char[] chars = str.toCharArray();
        int length = chars.length;
        int[] dp = new int[length + 1];
        dp[length] = 1;
        for (int index = length - 1; index >= 0; index--) {
            int ways = 0;
            PrefixTreeNode cur = root;
            for (int j = index; j < length; j++) {
                int path = chars[j] - 'a';
                if (cur.nexts[path] != null) {
                    cur = cur.nexts[path];
                    if (cur.end) {
                        ways += g(chars, root, j + 1);
                    }
                } else {
                    break;
                }
            }
            dp[index] = ways;
        }
        return dp[0];
    }

}
