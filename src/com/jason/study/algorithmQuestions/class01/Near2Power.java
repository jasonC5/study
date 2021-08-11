package com.jason.study.algorithmQuestions.class01;

/**
 * 给定一个非负整数num，
 * 如何不用循环语句，
 * 返回>=num，并且离num最近的，2的某次方
 * --位运算
 * --hashMap源码
 *
 * @author JasonC5
 */
public class Near2Power {

    public static void main(String[] args) {
        int cap = 1020;
        System.out.println(tableSizeFor(cap));
    }

    private static int tableSizeFor(int num) {
        if (num < 0) {
            return 1;
        }
        num = num - 1;
        num |= num >>> 1;
        num |= num >>> 2;
        num |= num >>> 4;
        num |= num >>> 8;
        num |= num >>> 16;
        return num + 1;
    }


}
