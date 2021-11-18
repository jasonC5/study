package com.jason.study.algorithmQuestions.class01;

/**
 * 一个数组中只有两种字符'G'和’B’，
 * 可以让所有的G都放在左侧，所有的B都放在右侧
 * 或者可以让所有的G都放在右侧，所有的B都放在左侧
 * 但是只能在相邻字符之间进行交换操作，
 * 返回至少需要交换几次
 * --贪心，第x个G挪到x-1位置（第一个挪到第0位，第二个挪到第1位……）
 * --过程中算两个结果：G到左边，B到左边，返回较小的
 *
 * @author JasonC5
 */
public class MinSwapStep {
    // 为了测试
    public static String randomString(int maxLen) {
        char[] str = new char[(int) (Math.random() * maxLen)];
        for (int i = 0; i < str.length; i++) {
            str[i] = Math.random() < 0.5 ? 'G' : 'B';
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int testTime = 1000000;
        System.out.println("start");
        for (int i = 0; i < testTime; i++) {
            String str = randomString(maxLen);
            int ans1 = minSteps1(str);
            int ans2 = minSteps2(str);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finished");
    }

    public static int minSteps1(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        char[] str = s.toCharArray();
        int step1 = 0;
        int gi = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == 'G') {
                step1 += i - (gi++);
            }
        }
        int step2 = 0;
        int bi = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == 'B') {
                step2 += i - (bi++);
            }
        }
        return Math.min(step1, step2);
    }

    private static int minSteps2(String str) {
        char[] chars = str.toCharArray();
        int gCount = 0;
        int bCount = 0;
        int gStep = 0;
        int bStep = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == 'G') {
                gStep += (i - gCount++);
            } else {
                bStep += (i - bCount++);
            }
        }
        return Math.min(gStep, bStep);
    }

}
