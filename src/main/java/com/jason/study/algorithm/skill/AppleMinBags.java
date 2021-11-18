package com.jason.study.algorithm.skill;

/**
 * 小虎去买苹果，商店只提供两种类型的塑料袋，每种类型都有任意数量。
 * 1）能装下6个苹果的袋子
 * 2）能装下8个苹果的袋子
 * 小虎可以自由使用两种袋子来装苹果，但是小虎有强迫症，他要求自己使用的袋子数量必须最少，且使用的每个袋子必须装满。
 * 给定一个正整数N，返回至少使用多少袋子。如果N无法让使用的每个袋子必须装满，返回-1
 *
 * @author JasonC5
 */
public class AppleMinBags {
    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            if (ans2(i) != process(i)) {
                System.out.println(i);
                return;
            }
        }
        System.out.println("complete!");
    }

    static int PAG1 = 8;
    static int PAG2 = 6;

    public static int process(int x) {
        int min1 = x / PAG1;
        while (min1 >= 0) {
            int last = x - min1 * PAG1;
            if (last % PAG2 == 0) {
                return last / PAG2 + min1;
            }
            min1--;
        }
        return -1;
    }

    public static int ans2(int n) {
        if ((n & 1) != 0) {
            return -1;
        }
        if (n < 18) {
            if (n == 0) {
                return 0;
            } else if (n == 6 || n == 8) {
                return 1;
            } else if (n == 12 || n == 14 || n == 16) {
                return 2;
            }
            return -1;
        }

        //大于18的时候
        return 3 + (n - 18) / 8;

    }

}
