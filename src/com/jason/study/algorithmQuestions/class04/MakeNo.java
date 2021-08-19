package com.jason.study.algorithmQuestions.class04;

/**
 * 生成长度为size的达标数组，什么叫达标？
 * 达标：对于任意的 i<k<j，满足 [i] + [j] != [k] * 2
 * 给定一个正数size，返回长度为size的达标数组
 * --左边偶数，右边奇数，怎么都能达标
 * --分治
 *
 * @author JasonC5
 */
public class MakeNo {

    // 检验函数
    public static boolean isValid(int[] arr) {
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            for (int k = i + 1; k < N; k++) {
                for (int j = k + 1; j < N; j++) {
                    if (arr[i] + arr[j] == 2 * arr[k]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int N = 1; N < 1000; N++) {
            int[] arr = makeNo(N);
            if (!isValid(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
        System.out.println(isValid(makeNo(1042)));
        System.out.println(isValid(makeNo(2981)));
    }

    private static int[] makeNo(int size) {
        if (size == 1) {
            return new int[]{1};
        }
        int half = (size + 1) / 2;
        int[] help = makeNo(half);
        int[] ans = new int[size];
        int i = 0;
//        i<k<j，满足 [i] + [j] != [k] * 2  只要我满足一个奇数一个偶数，就不可能满足这个条件
        for (; i < half; i++) {
            ans[i] = 2 * help[i];
        }
        for (; i < size; i++) {
            ans[i] = 2 * help[i - half] + 1;
        }
        return ans;
    }

}
