package com.jason.study.algorithmQuestions.class42;

/**
 * https://leetcode-cn.com/problems/integer-to-english-words/
 * 将非负整数 num 转换为其对应的英文表示。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：num = 123
 * 输出："One Hundred Twenty Three"
 * 示例 2：
 * <p>
 * 输入：num = 12345
 * 输出："Twelve Thousand Three Hundred Forty Five"
 * 示例 3：
 * <p>
 * 输入：num = 1234567
 * 输出："One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
 * 示例 4：
 * <p>
 * 输入：num = 1234567891
 * 输出："One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety One"
 *  
 * <p>
 * 提示：
 * <p>
 * 0 <= num <= 2^31 - 1
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/integer-to-english-words
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC273 {

    public String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }
        StringBuilder res = new StringBuilder();
        String[] names = {"Billion ", "Million ", "Thousand ", ""};
        int high = 1_000_000_000;
        int highIndex = 0;
        while (num != 0) {
            int cur = num / high;
            num %= high;
            if (cur != 0) {
                res.append(num1To999(cur));
                res.append(names[highIndex]);
            }
            high /= 1000;
            highIndex++;
        }
        return res.substring(0, res.length() - 1);
    }

    private String num1To999(int num) {//XXX  XX X 0
        if (num < 1) {
            return "";
        }
        if (num < 100) {
            return num1To99(num);
        }
        return num1To19(num / 100) + "Hundred " + num1To99(num % 100);
    }

    private String num1To99(int num) {
        if (num < 20) {
            return num1To19(num);
        }
        int high = num / 10;
        String[] tyNames = {"Twenty ", "Thirty ", "Forty ", "Fifty ", "Sixty ", "Seventy ", "Eighty ", "Ninety "};
        return tyNames[high - 2] + num1To19(num % 10);
    }

    private String num1To19(int num) {
        if (num < 1 || num > 19) {
            return "";
        }
        String[] names = {"One ", "Two ", "Three ", "Four ", "Five ", "Six ", "Seven ", "Eight ", "Nine ", "Ten ",
                "Eleven ", "Twelve ", "Thirteen ", "Fourteen ", "Fifteen ", "Sixteen ", "Seventeen", "Eighteen ",
                "Nineteen "};
        return names[num - 1];
    }
}
