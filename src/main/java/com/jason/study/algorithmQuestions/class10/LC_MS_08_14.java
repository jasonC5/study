package com.jason.study.algorithmQuestions.class10;

/**
 * 给定一个布尔表达式和一个期望的布尔结果 result，布尔表达式由 0 (false)、1 (true)、& (AND)、 | (OR) 和 ^ (XOR) 符号组成。实现一个函数，算出有几种可使该表达式得出 result 值的括号方法。
 * <p>
 * 示例 1:
 * <p>
 * 输入: s = "1^0|0|1", result = 0
 * <p>
 * 输出: 2
 * 解释: 两种可能的括号方法是
 * 1^(0|(0|1))
 * 1^((0|0)|1)
 * 示例 2:
 * <p>
 * 输入: s = "0&0&0&1^1|0", result = 1
 * <p>
 * 输出: 10
 * 提示：
 * <p>
 * 运算符的数量不超过 19 个
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/boolean-evaluation-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC_MS_08_14 {
    public static int countEval(String s, int result) {
        if (s == null || "".equals(s)) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int length = chars.length;
        int[][][] dp = new int[length][length][2];
        boolean[][] finished = new boolean[length][length];
        int[] ans = process(chars, 0, length - 1, dp, finished);
        return ans[result];
    }

    private static int[] process(char[] chars, int left, int right, int[][][] dp, boolean[][] finished) {
        if (finished[left][right]) {
            return dp[left][right];
        }
        int[] ans = new int[2];
        if (left == right) {
            ans[0] = chars[left] == '0' ? 1 : 0;
            ans[1] = chars[left] == '1' ? 1 : 0;
        } else {
            for (int i = left + 1; i < right; i += 2) {
                int[] leftAns = process(chars, left, i - 1, dp, finished);
                int[] rightAns = process(chars, i + 1, right, dp, finished);
                switch (chars[i]) {
                    case '|':
                        ans[0] += leftAns[0] * rightAns[0];
                        ans[1] += leftAns[1] * rightAns[0] + leftAns[0] * rightAns[1] + leftAns[1] * rightAns[1];
                        break;
                    case '&':
                        ans[0] += leftAns[0] * rightAns[0] + leftAns[1] * rightAns[0] + leftAns[0] * rightAns[1];
                        ans[1] += leftAns[1] * rightAns[1];
                        break;
                    case '^':
                        ans[0] += leftAns[0] * rightAns[0] + leftAns[1] * rightAns[1];
                        ans[1] += leftAns[1] * rightAns[0] + leftAns[0] * rightAns[1];
                        break;
                    default:
                        break;
                }
            }
        }
        finished[left][right] = true;
        dp[left][right] = ans;
        return ans;
    }

    public static void main(String[] args) {
        String s = "1^0|0|1";
        int result = 0;
        System.out.println(countEval(s, result));
    }
}
