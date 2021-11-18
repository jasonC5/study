package com.jason.study.algorithmQuestions.class36;

/**
 * // 来自美团
 * // () 分值为2
 * // (()) 分值为3
 * // ((())) 分值为4
 * // 也就是说，每包裹一层，分数就是里面的分值+1
 * // ()() 分值为2 * 2
 * // (())() 分值为3 * 2
 * // 也就是说，每连接一段，分数就是各部分相乘，以下是一个结合起来的例子
 * // (()())()(()) -> (2 * 2 + 1) * 2 * 3 -> 30
 * // 给定一个括号字符串str，已知str一定是正确的括号结合，不会有违规嵌套
 * // 返回分数
 *
 * @author JasonC5
 */
public class Code04_ComputeExpressionValue {

    public static void main(String[] args) {

        String str1 = "(()())()(())";
        System.out.println(sores(str1));

        // (()()) + (((()))) + ((())())
        // (()()) -> 2 * 2 + 1 -> 5
        // (((()))) -> 5
        // ((())()) -> ((2 + 1) * 2) + 1 -> 7
        // 所以下面的结果应该是175
        String str2 = "(()())(((())))((())())";
        System.out.println(sores(str2));

        // (()()()) + (()(()))
        // (()()()) -> 2 * 2 * 2 + 1 -> 9
        // (()(())) -> 2 * 3 + 1 -> 7
        // 所以下面的结果应该是63
        String str3 = "(()()())(()(()))";
        System.out.println(sores(str3));
    }

    private static int sores(String str) {
        //递归 找到第一个右括号之后，计算自己的分数，并且返回下一个位置，向上返回
        return process(str.toCharArray(), 0)[0];
    }

    private static int[] process(char[] s, int index) {
        if (s[index] == ')') {
            return new int[]{1, index + 1};
        }
        int ans = 1;
        while (index < s.length && s[index] != ')') {
            int[] info = process(s, index + 1);
            ans *= info[0] + 1;
            index = info[1];
        }
        return new int[]{ans, index + 1};
    }
}
