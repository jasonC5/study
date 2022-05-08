package com.jason.study.algorithmQuestions.class09;

import java.util.ArrayList;
import java.util.List;

/**
 * 给你一个由若干括号和字母组成的字符串 s ，删除最小数量的无效括号，使得输入的字符串有效。
 * <p>
 * 返回所有可能的结果。答案可以按 任意顺序 返回。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "()())()"
 * 输出：["(())()","()()()"]
 * 示例 2：
 * <p>
 * 输入：s = "(a)())()"
 * 输出：["(a())()","(a)()()"]
 * 示例 3：
 * <p>
 * 输入：s = ")("
 * 输出：[""]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-invalid-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC301 {
    public List<String> removeInvalidParentheses(String s) {
        List<String> ans = new ArrayList<>();
        process(s, ans, 0, 0, new char[]{'(', ')'});
        return ans;
    }

    private void process(String s, List<String> ans, int checkIdx, int deleteIdx, char[] par) {
        int count = 0;
        for (int i = checkIdx; i < s.length(); i++) {
            count += s.charAt(i) == par[0] ? 1 : (s.charAt(i) == par[1] ? -1 : 0);
            if (count < 0) {
                for (int j = deleteIdx; j <= i; j++) {
                    if (s.charAt(j) == par[1] && (j == deleteIdx || s.charAt(j - 1) != s.charAt(j))) {
                        process(s.substring(0, j) + s.substring(j + 1, s.length()), ans, i, j, par);
                    }
                }
                return;
            }
        }
        String reversed = new StringBuilder(s).reverse().toString();
        if (par[0] == '(') {
            // 从右往左，删除左括号
            process(reversed, ans, 0, 0, new char[]{')', '('});
        } else {
            ans.add(reversed);
        }
    }
}
