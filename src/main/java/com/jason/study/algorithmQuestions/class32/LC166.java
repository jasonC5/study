package com.jason.study.algorithmQuestions.class32;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定两个整数，分别表示分数的分子numerator 和分母 denominator，以 字符串形式返回小数 。
 * <p>
 * 如果小数部分为循环小数，则将循环的部分括在括号内。
 * <p>
 * 如果存在多个答案，只需返回 任意一个 。
 * <p>
 * 对于所有给定的输入，保证 答案字符串的长度小于 104 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/fraction-to-recurring-decimal
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC166 {

    public static void main(String[] args) {
        System.out.println(fractionToDecimal(-1, -2147483648));
    }

    public static String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) {
            return "0";
        }
        Map<Long, Integer> index = new HashMap<>();
        StringBuilder ans = new StringBuilder();
        if (numerator < 0 ^ denominator < 0) {
            ans.append("-");
        }
        long num = Math.abs((long)numerator);
        long den = Math.abs((long)denominator);
        long pre = num / den;
        ans.append(pre);
        num %= den;
        if (num == 0){
            return ans.toString();
        }
        ans.append(".");
        index.put(num, ans.length());
        while (true) {
            if (num == 0) {
                return ans.toString();
            }
            num *= 10;
            pre = num / den;
            ans.append(pre);
            num %= den;
            if (index.containsKey(num)) {
                ans.insert(index.get(num), "(");
                ans.append(")");
                return ans.toString();
            } else {
                index.put(num, ans.length());
            }
        }
    }
}
