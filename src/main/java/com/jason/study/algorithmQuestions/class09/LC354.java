package com.jason.study.algorithmQuestions.class09;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 给你一个二维整数数组 envelopes ，其中 envelopes[i] = [wi, hi] ，表示第 i 个信封的宽度和高度。
 * <p>
 * 当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
 * <p>
 * 请计算 最多能有多少个 信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
 * <p>
 * 注意：不允许旋转信封。
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：envelopes = [[5,4],[6,4],[6,7],[2,3]]
 * 输出：3
 * 解释：最多信封的个数为 3, 组合为: [2,3] => [5,4] => [6,7]。
 * 示例 2：
 * <p>
 * 输入：envelopes = [[1,1],[1,1],[1,1]]
 * 输出：1
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= envelopes.length <= 105
 * envelopes[i].length == 2
 * 1 <= wi, hi <= 105
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/russian-doll-envelopes
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC354 {
    public int maxEnvelopes(int[][] envelopes) {
        int length = envelopes.length;
        // 先按长递增，长相同时宽递减排序
        Arrays.sort(envelopes, (a1, a2) -> a1[0] == a2[0] ? a2[1] - a1[1] : a1[0] - a2[0]);
        // 最长递增子序列
        int[] nums = new int[length];
        for (int i = 0; i < envelopes.length; i++) {
            nums[i] = envelopes[i][1];
        }
        int maxLen = 1;
        int[] lenMin = new int[length];
        lenMin[0] = nums[0];
        for (int i = 1; i < length; i++) {
            if (nums[i] > lenMin[maxLen - 1]) {
                // 当前就能拉长最长递增子序列
                lenMin[maxLen++] = nums[i];
            } else if (nums[i] < lenMin[0]) {
                // 比最小的还小
                lenMin[0] = nums[i];
            } else {
                // 当前不能扩张，也不能覆盖最小值，看能不能更新，二分【找 >= nums[i] 的最大位置】
                int left = 0, right = maxLen - 1, find = left;
                while (left <= right) {
                    int mid = left + ((right - left) >> 1);
                    if (lenMin[mid] >= nums[i]){
                        find = mid;
                        right = mid - 1;
                    } else {
                        left = mid+1;
                    }
                }
                lenMin[find] = nums[i];
            }
        }
        return maxLen;
    }
}
