package com.jason.study.algorithmQuestions.class05;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 给定两个字符串s1和s2，问s2最少删除多少字符可以成为s1的子串？
 * 比如 s1 = "abcde"，s2 = "axbc"
 * --暴力解：子序列全排列+KMP
 * --编辑距离：s2通过删除的方式，能否变成s1的一个子串
 *
 * @author JasonC5
 */
public class DeleteMinCost {
    // 解法一
    // 求出str2所有的子序列，然后按照长度排序，长度大的排在前面。
    // 然后考察哪个子序列字符串和s1的某个子串相等(KMP)，答案就出来了。
    // 分析：
    // 因为题目原本的样本数据中，有特别说明s2的长度很小。所以这么做也没有太大问题，也几乎不会超时。
    // 但是如果某一次考试给定的s2长度远大于s1，这么做就不合适了。
    //暴力解
    public static int minCost1(String s1, String s2) {
        // S2的所有子串，全排列，从大到小尝试，如果有哪一个是S1的子序列，则返回S2和符合条件的子串的长度差，即为需要删除的个数
        // 1.查找所有子串的全排列
        List<String> s2Sub = process(s2, 0, "");
        //长度从小到大尝试
        s2Sub.sort((o1, o2) -> o2.length() - o1.length());
        for (String s : s2Sub) {
            if (s1.contains(s)) {
                return s2.length() - s.length();
            }
        }
        return s2.length();
    }

    private static List<String> process(String str, int index, String pre) {
        if (index == str.length()) {
            return new ArrayList<String>() {{
                this.add(pre);
            }};
        }
        List<String> ans = new ArrayList<>();
        //不要当前位置
        ans.addAll(process(str, index + 1, pre));
        //要当前位置
        ans.addAll(process(str, index + 1, pre + str.charAt(index)));
        return ans;
    }


    public static String generateRandomString(int l, int v) {
        int len = (int) (Math.random() * l);
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ('a' + (int) (Math.random() * v));
        }
        return String.valueOf(str);
    }


    // 解法二
    // 生成所有s1的子串
    // 然后考察每个子串和s2的编辑距离(假设编辑距离只有删除动作且删除一个字符的代价为1)
    // 如果s1的长度较小，s2长度较大，这个方法比较合适
    public static int minCost2(String s1, String s2) {
        if (s1.length() == 0 || s2.length() == 0) {
            return s2.length();
        }
        int ans = Integer.MAX_VALUE;
        char[] str2 = s2.toCharArray();
        for (int start = 0; start < s1.length(); start++) {
            for (int end = start + 1; end <= s1.length(); end++) {
                ans = Math.min(ans, distance(str2, s1.substring(start, end).toCharArray()));
            }
        }
        return ans == Integer.MAX_VALUE ? s2.length() : ans;
    }

    private static int distance(char[] chars1, char[] chars2) {
        int row = chars1.length;
        int col = chars2.length;
        int dp[][] = new int[row][col];
        // dp[i][j]的含义：
        // str2[0..i]仅通过删除行为变成s1sub[0..j]的最小代价
        // 可能性一：
        // str2[0..i]变的过程中，不保留最后一个字符(str2[i])，
        // 那么就是通过str2[0..i-1]变成s1sub[0..j]之后，再最后删掉str2[i]即可 -> dp[i][j] = dp[i-1][j] + 1
        // 可能性二：
        // str2[0..i]变的过程中，想保留最后一个字符(str2[i])，然后变成s1sub[0..j]，
        // 这要求str2[i] == s1sub[j]才有这种可能, 然后str2[0..i-1]变成s1sub[0..j-1]即可
        // 也就是str2[i] == s1sub[j] 的条件下，dp[i][j] = dp[i-1][j-1]
        //样本对应模型
        dp[0][0] = chars1[0] == chars2[0] ? 0 : -1;
        for (int j = 1; j < col; j++) {
            //chars1 就只有1个，怎么删都不可能超过1个
            dp[0][j] = -1;
        }
        for (int i = 1; i < row; i++) {
            dp[i][0] = (dp[i - 1][0] != -1 || chars1[i] == chars2[0]) ? i : -1;
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                //chars1 0~i 位  通过删除  到chars2 0~j位
                //首先给个默认-1，如果i<j那么一定是-1
                dp[i][j] = -1;
                //如果dp[i-1][j]能搞定，把自己删掉就行
                if (dp[i - 1][j] != -1) {
                    dp[i][j] = dp[i - 1][j] + 1;
                }
                //如果当前位置能搞定目标最后一位，看i-1和j-1位置能不能搞定，如果能和上面的取最小值【如果有的话，没有就直接复制就行】
                if (chars1[i] == chars2[j] && dp[i - 1][j - 1] != -1) {
                    dp[i][j] = dp[i][j] == -1 ? dp[i - 1][j - 1] : Math.min(dp[i - 1][j - 1], dp[i][j]);
                }
            }
        }
        return dp[row - 1][col - 1] == -1 ? Integer.MAX_VALUE : dp[row - 1][col - 1];
    }

    public static void main(String[] args) {

//        char[] x = { 'a', 'b', 'c', 'd' };
//        char[] y = { 'a', 'd' };
//
//        System.out.println(onlyDelete(x, y));

        int str1Len = 20;
        int str2Len = 10;
        int v = 5;
        int testTime = 10000;
        boolean pass = true;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            String str1 = generateRandomString(str1Len, v);
            String str2 = generateRandomString(str2Len, v);
            int ans1 = minCost1(str1, str2);
            int ans2 = minCost2(str1, str2);
//            int ans3 = minCost3(str1, str2);
//            int ans4 = minCost4(str1, str2);
            if (ans1 != ans2 /*|| ans3 != ans4 || ans1 != ans3*/) {
                pass = false;
                System.out.println(str1);
                System.out.println(str2);
                System.out.println(ans1);
                System.out.println(ans2);
//                System.out.println(ans3);
//                System.out.println(ans4);
                break;
            }
        }
        System.out.println("test pass : " + pass);
    }
}
