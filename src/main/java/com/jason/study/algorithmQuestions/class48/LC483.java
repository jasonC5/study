package com.jason.study.algorithmQuestions.class48;

/**
 * 对于给定的整数 n, 如果n的k（k>=2）进制数的所有数位全为1，则称k（k>=2）是 n 的一个好进制。
 * <p>
 * 以字符串的形式给出 n, 以字符串的形式返回 n 的最小好进制。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入："13"
 * 输出："3"
 * 解释：13 的 3 进制是 111。
 * 示例 2：
 * <p>
 * 输入："4681"
 * 输出："8"
 * 解释：4681 的 8 进制是 11111。
 * 示例 3：
 * <p>
 * 输入："1000000000000000000"
 * 输出："999999999999999999"
 * 解释：1000000000000000000 的 999999999999999999 进制是 11。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/smallest-good-base
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC483 {
    public String smallestGoodBase(String n) {
        //好进制 ： 1 + k + k*k + k*k*k + …… + k^x = n ==> n = (k^(x+1) - 1)/(k -1 )  等比数列求和公式
        //最大的好进制：n-1 : (n-1)^0 + (n-1)^1 = 1 + n - 1 = n （11）
        //最小的可能好进制 ： 2
        //要找到最小的好进制，从下往上枚举
        //二分
        long num = Long.parseLong(n);
        for (int m = (int) (Math.log(num + 1) / Math.log(2)); m > 2; m--) {
            long l = (long) (Math.pow(num, 1.0 / m));
            long r = (long) (Math.pow(num, 1.0 / (m - 1))) + 1L;
            // 二分
            while (l <= r) {
                long mid = l + ((r - l) >> 1);
                long sum = 0L;
                long base = 1L;
                // mid进制，m位1，表示的数字
                for (int i = 0; i < m && sum <= num; i++) {
                    sum += base;
                    base *= mid;
                }
                if (sum < num) {
                    l = mid + 1;
                } else if (sum > num) {
                    r = mid - 1;
                } else {
                    return String.valueOf(mid);
                }
            }
        }
        //保底
        return String.valueOf(num - 1);
    }
}
