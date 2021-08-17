package com.jason.study.algorithmQuestions.class33;

/**
 * 给你一个长度为n的整数数组nums，其中n > 1，返回输出数组output，其中 output[i]等于nums中除nums[i]之外其余各元素的乘积。
 * <p>
 * <p>
 * <p>
 * 示例:
 * <p>
 * 输入: [1,2,3,4]
 * 输出: [24,12,8,6]
 * <p>
 * <p>
 * 提示：题目数据保证数组之中任意元素的全部前缀元素和后缀（甚至是整个数组）的乘积都在 32 位整数范围内。
 * <p>
 * 说明: 请不要使用除法，且在O(n) 时间复杂度内完成此题。
 * <p>
 * 进阶：
 * 你可以在常数空间复杂度内完成这个题目吗？（ 出于对空间复杂度分析的目的，输出数组不被视为额外空间。）
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/product-of-array-except-self
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC238 {
    public int[] productExceptSelf(int[] nums) {
        int length = nums.length;
        //后缀积
        int[] postProducts = new int[length];
        postProducts[length - 1] = nums[length - 1];
        for (int index = length - 2; index >= 0; index--) {
            postProducts[index] = nums[index] * postProducts[index + 1];
        }
        int preProduct = 1;
        for (int i = 0; i < length - 1; i++) {
            postProducts[i] = preProduct * postProducts[i + 1];
            preProduct *= nums[i];
        }
        postProducts[length - 1] = preProduct;
        return postProducts;
    }
}
