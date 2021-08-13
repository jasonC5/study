package com.jason.study.algorithmQuestions.class32;

/**
 * 202. 快乐数
 * 编写一个算法来判断一个数 n 是不是快乐数。
 * <p>
 * 「快乐数」定义为：
 * <p>
 * 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
 * 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
 * 如果 可以变为  1，那么这个数就是快乐数。
 * 如果 n 是快乐数就返回 true ；不是，则返回 false 。
 *
 * @author JasonC5
 */
public class LC202 {

    public boolean isHappy(int n) {
        while (n != 1 && n != 4) {
            n = next(n);
        }
        return n == 1;
    }

    //每个位置上的平方和
    private int next(int n) {
        int num = 0;
        while (n > 0) {
            int x = n % 10;
            num += x * x;
            n = n / 10;
        }
        return num;
    }

}
