package com.jason.study.algorithmQuestions.class39;

/**
 * // 真实笔试，忘了哪个公司，但是绝对大厂
 * // 一个子序列的消除规则如下:
 * // 1) 在某一个子序列中，如果'1'的左边有'0'，那么这两个字符->"01"可以消除
 * // 2) 在某一个子序列中，如果'3'的左边有'2'，那么这两个字符->"23"可以消除
 * // 3) 当这个子序列的某个部分消除之后，认为其他字符会自动贴在一起，可以继续寻找消除的机会
 * // 比如，某个子序列"0231"，先消除掉"23"，那么剩下的字符贴在一起变成"01"，继续消除就没有字符了
 * // 如果某个子序列通过最优良的方式，可以都消掉，那么这样的子序列叫做“全消子序列”
 * // 一个只由'0'、'1'、'2'、'3'四种字符组成的字符串str，可以生成很多子序列，返回“全消子序列”的最大长度
 * // 字符串str长度 <= 200
 * // 体系学习班，代码46节，第2题+第3题
 * --范围尝试
 *
 * @author JasonC5
 */
public class Code05_0123Disappear {

    public static void main(String[] args) {
        String str1 = "010101";
        System.out.println(maxDisappear(str1));

        String str2 = "021331";
        System.out.println(maxDisappear(str2));
    }

    private static int maxDisappear(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return disappear(str.toCharArray(), 0, str.length() - 1);
    }

    private static int disappear(char[] arr, int left, int right) {
        if (left >= right) {
            return 0;
        }
        if (left == right - 1) {
            return (arr[left] == '1' && arr[right] == '0') || (arr[left] == '2' && arr[right] == '3') ? 2 : 0;
        }
        // left ~ right 至少有2个
        // 两种情况：要left 不要left
//        不要，简单
        int p1 = disappear(arr, left + 1, right);
        // 要，左边是0或者2才能要，要不然消不掉
        if (arr[left] != '0' && arr[left] != '2') {
            return p1;
        }
        //现在可以要了，等一个可以消掉的，凑一对，然后把中间的求个值，后面的求个值，相加
        char wait = arr[left] == '0' ? '1' : '3';
        int p2 = 0;
        for (int i = left + 1; i <= right; i++) {
            if (arr[i] == wait) {//等到了，算
                p2 = Math.max(p2, 2 + disappear(arr, left + 1, i - 1) + disappear(arr, i + 1, right));
            }
        }
        return Math.max(p1, p2);
    }
}
