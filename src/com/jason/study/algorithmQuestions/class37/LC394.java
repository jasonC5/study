package com.jason.study.algorithmQuestions.class37;

/**
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 * <p>
 * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
 * <p>
 * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
 * <p>
 * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "3[a]2[bc]"
 * 输出："aaabcbc"
 * 示例 2：
 * <p>
 * 输入：s = "3[a2[c]]"
 * 输出："accaccacc"
 * 示例 3：
 * <p>
 * 输入：s = "2[abc]3[cd]ef"
 * 输出："abcabccdcdcdef"
 * 示例 4：
 * <p>
 * 输入：s = "abc3[cd]xyz"
 * 输出："abccdcdcdxyz"
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/decode-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC394 {

    static class Info {
        public String str;
        public int next;

        public Info(String str, int next) {
            this.str = str;
            this.next = next;
        }
    }


    public static String decodeString(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        char[] chars = s.toCharArray();
        return process(chars, 0).str;
    }

    //3[a]2[bc]
    public static Info process(char[] chars, int index) {
        int count = 0;
        StringBuilder pre = new StringBuilder();
        for (int i = index; i < chars.length; i++) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                count = count * 10 + (chars[i] - '0');
            } else if (chars[i] == '[') {
                //获取子过程信息
                Info info = process(chars, i + 1);
                i = info.next;
                for (int j = 0; j < count; j++) {
                    pre.append(info.str);
                }
                count = 0;
            } else if (chars[i] == ']') {
                return new Info(pre.toString(), i);
            } else {// 字母
                pre.append(chars[i]);
            }
        }
        return new Info(pre.toString(), chars.length);
    }

    public static void main(String[] args) {
        String s = "3[a]2[bc]";
        System.out.println(decodeString(s));
    }


}
