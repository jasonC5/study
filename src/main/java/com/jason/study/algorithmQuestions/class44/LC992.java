package com.jason.study.algorithmQuestions.class44;

import java.util.HashMap;

/**
 * 给定一个正整数数组 A，如果 A的某个子数组中不同整数的个数恰好为 K，则称 A 的这个连续、不一定不同的子数组为好子数组。
 * <p>
 * （例如，[1,2,3,1,2] 中有3个不同的整数：1，2，以及3。）
 * <p>
 * 返回A中好子数组的数目。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：A = [1,2,1,2,3], K = 2
 * 输出：7
 * 解释：恰好由 2 个不同整数组成的子数组：[1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2].
 * 示例 2：
 * <p>
 * 输入：A = [1,2,1,3,4], K = 3
 * 输出：3
 * 解释：恰好由 3 个不同整数组成的子数组：[1,2,1,3], [2,1,3], [1,3,4].
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= A.length <= 20000
 * 1 <= A[i] <= A.length
 * 1 <= K <= A.length
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/subarrays-with-k-different-integers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC992 {

    //双指针+词频统计【这种思路不行】
    public static int subarraysWithKDistinct(int[] nums, int k) {
        int length = nums.length;
        int left = 0, right = 0;
        int[] times = new int[length];
        times[nums[right]] = 1;
        int kinds = 1, ans = 0;
        while (right < length || kinds >= k) {
            if (kinds <= k) {
                if (right == length - 1) {
                    break;
                }
                if (times[nums[++right]]++ == 0) {
                    kinds++;
                }
            } else {
                if (--times[nums[left++]] == 0) {
                    kinds--;
                }
            }
            if (k == kinds) {
                ans++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,1,2,3};
        int k = 2;
        System.out.println(subarraysWithKDistinct3(nums, k));
    }

    //方法2：2个滑动窗口+词频统计 一个k种，一个k-1种
    public static int subarraysWithKDistinct2(int[] nums, int k) {
        int length = nums.length;
        int kLeft = 0/*种数为k的左边界*/, k1Left = 0;/*种数为k-1的左边界*/
        int[] kTimes = new int[length + 1];//种数为k的词频统计
        int[] k1Times = new int[length + 1];//种数为k-1的的词频统计
        int kindK = 0, kindK1 = 0;//两个种数标记
        int ans = 0;
        for (int right = 0; right < length; right++) {
            if (kTimes[nums[right]]++ == 0) {
                kindK++;
            }
            if (k1Times[nums[right]]++ == 0) {
                kindK1++;
            }
            //如果种类超了，缩左边
            while (kindK == k + 1) {
                if (--kTimes[nums[kLeft++]] == 0) {
                    kindK--;
                }
            }
            while (kindK1 == k) {
                if (--k1Times[nums[k1Left++]] == 0) {
                    kindK1--;
                }
            }
            ans += k1Left - kLeft;
        }
        return ans;
    }


    //方法3：.<=k种有几个【x】，<=k-1有几个【y】，那么严格=k 【x-y】，算两个数组，一减，算和
    public static int subarraysWithKDistinct3(int[] nums, int k) {
        return kindLessThen(nums, k) - kindLessThen(nums, k - 1);
    }

    private static int kindLessThen(int[] nums, int k) {
        int left = 0, ans = 0;
        int length = nums.length;
        int[] times = new int[length + 1];//种数为k的词频统计
        int kind = 0;
        //以right结尾的字串，有多少个种类<=k个
        for (int right = 0; right < length; right++) {
            if (times[nums[right]]++ == 0) {
                kind++;
            }
            while (kind > k) {
                if (--times[nums[left++]] == 0) {
                    kind--;
                }
            }
            ans += right - left + 1;
        }
        return ans;
    }
}
