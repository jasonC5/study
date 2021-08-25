package com.jason.study.algorithmQuestions.class35;

import java.util.ArrayList;
import java.util.List;

/**
 * 写一个程序，输出从 1 到 n 数字的字符串表示。
 * <p>
 * 1. 如果 n 是3的倍数，输出“Fizz”；
 * <p>
 * 2. 如果 n 是5的倍数，输出“Buzz”；
 * <p>
 * 3.如果 n 同时是3和5的倍数，输出 “FizzBuzz”。
 * <p>
 * 示例：
 * <p>
 * n = 15,
 * <p>
 * 返回:
 * [
 * "1",
 * "2",
 * "Fizz",
 * "4",
 * "Buzz",
 * "Fizz",
 * "7",
 * "8",
 * "Fizz",
 * "Buzz",
 * "11",
 * "Fizz",
 * "13",
 * "14",
 * "FizzBuzz"
 * ]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/fizz-buzz
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC412 {


    public List<String> fizzBuzz(int n) {
        List<String> ans = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(i % 3 == 0 ? "Fizz" : "");
            stringBuilder.append(i % 5 == 0 ? "Buzz" : "");
            stringBuilder.append(stringBuilder.length() == 0 ? i : "");
            ans.add(stringBuilder.toString());
        }
        return ans;
    }

    public List<String> fizzBuzz2(int n) {
        List<String> ans = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            String str = i % 3 == 0 ? "Fizz" : "";
            str += i % 5 == 0 ? "Buzz" : "";
            str += str.length() == 0 ? i : "";
            ans.add(str);
        }
        return ans;
    }

    public List<String> fizzBuzz3(int n) {
        List<String> ans = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            boolean flag3 = i % 3 == 0, flag5 = i % 5 == 0;
            String str = flag3 && flag5 ? "FizzBuzz" : (flag3 ? "Fizz" : (flag5 ? "Buzz" : (i + "")));
            ans.add(str);
        }
        return ans;
    }
}
