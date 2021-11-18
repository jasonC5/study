package com.jason.study.algorithmQuestions.class32;

/**
 * 统计所有小于非负整数 n 的质数的数量。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：n = 10
 * 输出：4
 * 解释：小于 10 的质数一共有 4 个, 它们是 2, 3, 5, 7 。
 * 示例 2：
 * <p>
 * 输入：n = 0
 * 输出：0
 * 示例 3：
 * <p>
 * 输入：n = 1
 * 输出：0
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/count-primes
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC204 {

    public static void main(String[] args) {
        System.out.println(countPrimes2(10000));
        System.out.println(countPrimes(10000));
    }

    public static int countPrimes(int n) {
        if (n < 3) {
            return 0;
        }
        //统计有多少个不是素数，剩下的，就是素数
        boolean[] map = new boolean[n];
        //先排除所有的偶数，（2占着1的位置）
        int count = n / 2;
        for (int i = 3; i * i < n; i += 2) {
            if (map[i]) {
                continue;
            }
            for (int j = i * i; j < n; j += 2 * i) {
                if (!map[j]) {
                    map[j] = true;
                    count--;
                }
            }
        }
        return count;
    }

    public static int countPrimes2(int n) {
        if (n < 3) {
            return 0;
        }
        // j已经不是素数了，f[j] = true;
        boolean[] f = new boolean[n];
        int count = n / 2; // 所有偶数都不要，还剩几个数
        // 跳过了1、2    3、5、7、
        for (int i = 3; i * i < n; i += 2) {
            if (f[i]) {
                continue;
            }
            // 3 -> 3 * 3 = 9   3 * 5 = 15   3 * 7 = 21
            // 7 -> 7 * 7 = 49  7 * 9 = 63
            // 13 -> 13 * 13  13 * 15
            for (int j = i * i; j < n; j += 2 * i) {
                if (!f[j]) {
                    --count;
                    f[j] = true;
                }
            }
        }
        return count;
    }
}
