package com.jason.study.algorithmQuestions.class08;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 1.给定一个字符串str，str表示一个公式，
 * 公式里可能有整数、加减乘除符号和左右括号
 * 返回公式的计算结果，难点在于括号可能嵌套很多层
 * str="48*((70-65)-43)+8*1"，返回-1816。
 * str="3+1*4"，返回7。
 * str="3+(1*4)"，返回7。
 * 【说明】
 * 1.可以认为给定的字符串一定是正确的公式，即不需要对str做公式有效性检查
 * 2.如果是负数，就需要用括号括起来，比如“4*(-3)”但如果负数作为公式的开头或括号部分的开头，则可以没有括号，比如"-3*4"和"(-3*4)"都是合法的。
 * 3.不用考虑计算过程中会发生溢出的情况。
 * <p>
 * -- 括号嵌套题 套路
 * -- 栈 计算
 * -- 子函数：返回两个值：ans 、next
 *
 * @author JasonC5
 */
public class Calc {

    public static int doCalc(String formula) {
        char[] chars = formula.toCharArray();
        return f(chars, 0)[0];
    }

    private static int[] f(char[] chars, int idx) {
        int cur = 0;
        LinkedList<String> queue = new LinkedList<>();// 双端队列   或者用栈，或者直接数组都行
        while (idx < chars.length && chars[idx] != ')') {
            if (chars[idx] >= '0' && chars[idx] <= '9') {//数字
                cur = cur * 10 + (chars[idx++] - '0');//
            } else if (chars[idx] != '(') {//运算法
//                queue.push(String.valueOf(cur));
                add(queue, cur);
                queue.addLast(String.valueOf(chars[idx++]));
                cur = 0;
            } else {// '('
                int[] info = f(chars, idx + 1);
                idx = info[1] + 1;
                cur = info[0];
            }
        }
//        queue.push(String.valueOf(cur));
        add(queue, cur);
        int result = getNum(queue);
        return new int[]{result, idx};// 第一个放 ans， 第二个放结束位置
    }

    private static void add(LinkedList<String> queue, int cur) {
        if (!queue.isEmpty() && ("*".equals(queue.peekLast()) || "/".equals(queue.peekLast()))) {
            // 这里处理掉乘法，计算的时候就能只算加减法，减少复杂度
            String symbol = queue.pollLast();
            int num = Integer.parseInt(queue.pollLast());
            cur = "*".equals(symbol) ? (num * cur) : (num / cur);
        }
        queue.addLast(String.valueOf(cur));
    }

    private static int getNum(LinkedList<String> queue) {
        int res = 0;
        boolean add = true;
        while (!queue.isEmpty()) {
            String cur = queue.pollFirst();
            if ("+".equals(cur)) {
                add = true;
            } else if ("-".equals(cur)) {
                add = false;
            } else {
                int num = Integer.parseInt(cur);
                res += add ? num : (-num);
            }
        }
        return res;
    }


    public static void main(String[] args) {
        String p1 = "1+(2*3-1)*2";
        String p2 = "((1+1)*4/(3-1))+1";
        String p3 = "2/1+3+2*(1+11)";
        System.out.println(doCalc(p1));
        System.out.println(doCalc(p2));
        System.out.println(doCalc(p3));
    }

}
