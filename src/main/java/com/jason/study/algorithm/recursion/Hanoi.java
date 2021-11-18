package com.jason.study.algorithm.recursion;

/**
 * 汉诺塔问题
 * 1.三层汉诺塔问题
 * 2.N层汉诺塔问题
 *
 * @author JasonC5
 */
public class Hanoi {

    public static void main(String[] args) {
//        O(2^n - 1)
        hanoi1(3);
        System.out.println("#####################");
        hanoi2(3);
        System.out.println("#####################");
    }

    //6个方法合并一个过程
    private static void hanoi2(int n) {
        mov(n, "left", "right", "mid");
    }

    private static void mov(int n, String from, String to, String mid) {
        if (n == 1) {//base case
            System.out.println("mov 1 " + from + " --> " + to);
            return;
        }
        mov(n - 1, from, mid, to);
        System.out.println("mov " + n + " " + from + " --> " + to);
        mov(n - 1, mid, to, from);
    }

    //6个过程，相互调用
    public static void hanoi1(int n) {
        left2Right(n);
    }

    private static void left2Right(int n) {
        if (n == 1) { // base case
            System.out.println("mov 1 left --> right");
            return;
        }
        left2Mid(n - 1);
        System.out.println("mov " + n + " left --> right");
        mid2Right(n - 1);
    }

    private static void mid2Left(int n) {
        if (n == 1) { // base case
            System.out.println("mov 1 mid --> Left");
            return;
        }
        mid2Right(n - 1);
        System.out.println("mov " + n + " mid --> Left");
        right2Left(n - 1);
    }

    private static void left2Mid(int n) {
        if (n == 1) { // base case
            System.out.println("mov 1 left --> mid");
            return;
        }
        left2Right(n - 1);
        System.out.println("mov " + n + " left --> mid");
        right2Mid(n - 1);
    }

    private static void right2Left(int n) {
        if (n == 1) { // base case
            System.out.println("mov 1 right --> left");
            return;
        }
        right2Mid(n - 1);
        System.out.println("mov " + n + " right --> left");
        mid2Left(n - 1);
    }

    private static void right2Mid(int n) {
        if (n == 1) { // base case
            System.out.println("mov 1 right --> mid");
            return;
        }
        right2Left(n - 1);
        System.out.println("mov " + n + " right --> mid");
        left2Mid(n - 1);
    }

    private static void mid2Right(int n) {
        if (n == 1) { // base case
            System.out.println("mov 1 mid --> right");
            return;
        }
        mid2Left(n - 1);
        System.out.println("mov " + n + " mid --> right");
        left2Right(n - 1);
    }


}
