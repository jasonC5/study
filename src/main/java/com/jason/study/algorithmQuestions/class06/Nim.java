package com.jason.study.algorithmQuestions.class06;

/**
 * im博弈				√
 * 给定一个正数数组arr
 * 先手和后手每次可以选择在一个位置拿走若干值，
 * 值要大于0，但是要小于该处的剩余
 * 谁最先拿空arr，谁赢。根据arr，返回谁赢
 * --异或……
 * --异或和为0，后首赢，非0，先手赢
 *
 * @author JasonC5
 */
public class Nim {

    public static void printWinner(int[] arr) {
        int eor = 0;
        for (int num : arr) {
            eor ^= num;
        }
        System.out.println(eor == 0 ? "last win" : "first win");
    }
}
