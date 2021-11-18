package com.jason.study.algorithm.skill;

/**
 * 定义一种数：可以表示成若干（数量>1）连续正数和的数
 * 比如:
 * 5 = 2+3，5就是这样的数
 * 12 = 3+4+5，12就是这样的数
 * 1不是这样的数，因为要求数量大于1个、连续正数和
 * 2 = 1 + 1，2也不是，因为等号右边不是连续正数
 * 给定一个参数N，返回是不是可以表示成若干连续正数和的数
 *
 * @author JasonC5
 */
public class MSumToN {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
//            System.out.println(i + "\t:\t" + judge(i));
            if (judge2(i) ^ judge(i)) {
                System.out.println(i + "\t" + false);
            }
        }
    }
    //观察得到只有在0/1/2/4/8/16…… 2的几次幂的时候才是false
    public static boolean judge2(int n) {
        return n != (n & -n);
    }

    private static boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += j;
                if (sum == n) {
                    return true;
                }
                if (sum > n) {
                    break;
                }
            }
        }
        return false;
    }

}
