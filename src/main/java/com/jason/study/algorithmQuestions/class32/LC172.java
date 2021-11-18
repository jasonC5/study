package com.jason.study.algorithmQuestions.class32;

import java.math.BigInteger;

/**
 * 给定一个整数 n，返回 n! 结果尾数中零的数量。
 * 示例 1:
 * <p>
 * 输入: 3
 * 输出: 0
 * 解释:3! = 6, 尾数中没有零。
 * 示例2:
 * <p>
 * 输入: 5
 * 输出: 1
 * 解释:5! = 120, 尾数中有 1 个零.
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/factorial-trailing-zeroes
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC172 {

    public int trailingZeroes(int n) {
        //0~找有多少个5的因子
        int ans = 0;
        while (n != 0) {
            n /= 5;
            ans += n;
        }
        return ans;
    }


}
