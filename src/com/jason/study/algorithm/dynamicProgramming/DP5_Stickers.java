package com.jason.study.algorithm.dynamicProgramming;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个字符串str，给定一个字符串类型的数组arr，出现的字符都是小写英文，
 * arr每一个字符串代表一张贴纸，你可以把单个字符剪开使用，
 * 目的是频出str来返回需要至少多少张贴纸可以完成这个任务；
 * 例：str=“babac” arr={"ba","c","abcd"}至少需要两张贴纸 "ba" "abcd",因为使用这两张贴纸，把每一个字符单独剪开，
 * 含有2个a，2个b，1个c，是可以频出str的，所以返回2.
 * <p>
 * ["with","example","science"]
 * "thehat"
 * <p>
 * https://leetcode-cn.com/problems/stickers-to-spell-word/
 *
 * @author JasonC5
 */
public class DP5_Stickers {
    public static void main(String[] args) {
        String target = "atomher";
        String[] arr = {"these","guess","about","garden","him"};
//        String target = "thehat";
//        String[] arr = {"with", "example", "science"};
//        String target = "abc";
//        String[] arr = {"ax", "bx", "cx"};
        int ans1 = minStickers1(arr, target);
        System.out.println(ans1);
        int ans2 = minStickers2(arr, target);
        System.out.println(ans2);
    }

    public static int minStickers1(String[] arr, String target) {
        int length = arr.length;
        int[][] arrIndex = new int[length][26];
        for (int i = 0; i < length; i++) {
            String s = arr[i];
            char[] chars = s.toCharArray();
            for (char c : chars) {
                arrIndex[i][c - 'a']++;
            }
        }
        int ans = process(arrIndex, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private static int process(int[][] arrIndex, String target) {
        if (target == null || target.length() == 0) {
            return 0;
        }
        char[] targetChars = target.toCharArray();
        int[] targetIdx = new int[26];
        for (char c : targetChars) {
            targetIdx[c - 'a']++;
        }
        int length = arrIndex.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < length; i++) {
            // 尝试第一张贴纸是谁
            int[] arr = arrIndex[i];
            //包含target第一个字母的【剪枝】
            if (arr[targetChars[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (targetIdx[j] > 0) {
                        int sub = targetIdx[j] - arr[j];
                        for (int k = 0; k < sub; k++) {
                            builder.append((char) ('a' + j));
                        }
                    }
                }
                min = Math.min(min, process(arrIndex, builder.toString()));
            }
        }
        //如果能拼装成功，加上自己
        return min == Integer.MAX_VALUE ? Integer.MAX_VALUE : (min + 1);
    }

    //dp
    public static int minStickers2(String[] arr, String target) {
        int length = arr.length;
        int[][] arrIndex = new int[length][26];
        for (int i = 0; i < length; i++) {
            String s = arr[i];
            char[] chars = s.toCharArray();
            for (char c : chars) {
                arrIndex[i][c - 'a']++;
            }
        }
        Map<String, Integer> dp = new HashMap<>();
        dp.put("", 0);//base case
        int ans = process2(arrIndex, target, dp);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private static int process2(int[][] arrIndex, String target, Map<String, Integer> dp) {
        if (dp.containsKey(target)) {
            return dp.get(target);
        }
        char[] targetChars = target.toCharArray();
        int[] targetIdx = new int[26];
        for (char c : targetChars) {
            targetIdx[c - 'a']++;
        }
        int length = arrIndex.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < length; i++) {
            // 尝试第一张贴纸是谁
            int[] arr = arrIndex[i];
            //包含target第一个字母的【剪枝】
            if (arr[targetChars[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (targetIdx[j] > 0) {
                        int sub = targetIdx[j] - arr[j];
                        for (int k = 0; k < sub; k++) {
                            builder.append((char) ('a' + j));
                        }
                    }
                }
                min = Math.min(min, process2(arrIndex, builder.toString(), dp));
            }
        }
        //如果能拼装成功，加上自己
        int ans =  min == Integer.MAX_VALUE ? Integer.MAX_VALUE : (min + 1);
        dp.put(target, ans);
        return ans;
    }


}
