package com.jason.study.algorithm.skill;

/**
 * 给定一个正整数N，表示有N份青草统一堆放在仓库里
 * 有一只牛和一只羊，牛先吃，羊后吃，它俩轮流吃草
 * 不管是牛还是羊，每一轮能吃的草量必须是：
 * 1，4，16，64…(4的某次方)
 * 谁最先把草吃完，谁获胜
 * 假设牛和羊都绝顶聪明，都想赢，都会做出理性的决定
 * 根据唯一的参数N，返回谁会赢
 *
 * @author JasonC5
 */
public class EatGrass {
    //如果可以，要么全部吃完，要么剩下的不在4^n这个数字上
//    public static String winner1(int n) {
//        if () {
//
//        }
//    }
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            if (!winner2(i).equals(winner(i))) {
                System.out.println(i + "\t" + false);
            }
//            System.out.println(i + "\t:\t" + winner(i));
        }
    }

    public static String winner2(int n) {
        n = n % 5;
        return (n == 0 || n == 2) ? "last" : "first";
    }

    public static String winner(int n) {
        if (n < 5) {
            return (n == 0 || n == 2) ? "last" : "first";
        }
        //先手进入后手
        int want = 1;
        while (want <= n) {
            if (winner(n - want).equals("last")) {
                return "first";
            }
            //这里可能会溢出，造成死循环
            if (want < n / 4) {
                want <<= 2;
            } else {
                break;
            }
        }
        return "last";
    }

}
