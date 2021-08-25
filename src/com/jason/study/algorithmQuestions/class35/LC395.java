package com.jason.study.algorithmQuestions.class35;

/**
 * 给你一个字符串 s 和一个整数 k ，请你找出 s 中的最长子串， 要求该子串中的每一字符出现次数都不少于 k 。返回这一子串的长度。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "aaabb", k = 3
 * 输出：3
 * 解释：最长子串为 "aaa" ，其中 'a' 重复了 3 次。
 * 示例 2：
 * <p>
 * 输入：s = "ababbc", k = 2
 * 输出：5
 * 解释：最长子串为 "ababb" ，其中 'a' 重复了 2 次， 'b' 重复了 3 次。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-substring-with-at-least-k-repeating-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC395 {
    public static void main(String[] args) {
        System.out.println(longestSubstring("aaabb", 3));
    }

    public static int longestSubstring(String s, int k) {
        char[] str = s.toCharArray();
        int length = str.length;
        int max = 0;
        for (int i = 1; i < 26; i++) {//子串中只有i种字符的时候，每个字符
            int[] charCount = new int[26];
            //当前窗口内有几种字符
            int charKinds = 0;
            //超过k的字符种数
            int mtkKinds = 0;
            //右窗口
            int right = -1;
            for (int left = 0; left < length; left++) {
                //往右推边界
                while (right + 1 < length && !(charKinds == i && charCount[str[right + 1] - 'a'] == 0)/*下一个字符加进来之后种类数不会超*/) {
                    right++;
                    //如果之前数量为0，则窗口内字符种数+1
                    if (charCount[str[right] - 'a']++ == 0) {
                        charKinds++;
                    }
                    //如果加进来的这个数，刚好触发这个字符数量 > k， 则满足种数的+1
                    if (charCount[str[right] - 'a'] == k) {
                        mtkKinds++;
                    }
                }
                //已经推到最右了
                if (mtkKinds == i) {
                    max = Math.max(max, right - left + 1);
                }
                //左边界即将左移,左移之后，是否少一个满足条件的字符
                if (charCount[str[left] - 'a']-- ==k) {
                    mtkKinds--;
                }
                //是否已经降到0了，字符种数-1
                if (charCount[str[left] - 'a'] == 0) {
                    charKinds--;
                }
            }
        }
        return max;
    }
}
