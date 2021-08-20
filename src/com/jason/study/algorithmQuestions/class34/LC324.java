package com.jason.study.algorithmQuestions.class34;

/**
 * 给你一个整数数组 nums，将它重新排列成 nums[0] < nums[1] > nums[2] < nums[3]... 的顺序。
 * <p>
 * 你可以假设所有输入数组都可以得到满足题目要求的结果。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,5,1,1,6,4]
 * 输出：[1,6,1,5,1,4]
 * 解释：[1,4,1,5,1,6] 同样是符合题目要求的结果，可以被判题程序接受。
 * 示例 2：
 * <p>
 * 输入：nums = [1,3,2,2,3,1]
 * 输出：[2,3,1,3,1,2]
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= nums.length <= 5 * 104
 * 0 <= nums[i] <= 5000
 * 题目数据保证，对于给定的输入 nums ，总能产生满足题目要求的结果
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/wiggle-sort-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC324 {
    public void wiggleSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        //1. 改写快速排序，求 length/2 小
        int N = nums.length;
        findIndexNum(nums, 0, nums.length - 1, N / 2);
        //2. 切牌问题
        if ((N & 1) == 0) {
            shuffle(nums, 0, nums.length - 1);
            reverse(nums, 0, nums.length - 1);
        } else {
            shuffle(nums, 1, nums.length - 1);
        }
    }

    private int findIndexNum(int[] nums, int left, int right, int k) {
        int pivot = 0;//随机种子
        int[] range = null;//等于第k小的范围
        while (left < right) {
            //随机位置，把数组对于该数字调整成   小   等   大  的三个区间
            pivot = nums[left + (int) (Math.random() * (right - left + 1))];
            range = partition(nums, left, right, pivot);
            if (range[0] <= k && k <= range[1]) {
                return nums[k];
            } else if (k < range[0]) {
                right = range[0] - 1;
            } else {
                left = range[1] + 1;
            }
        }
        return nums[left];
    }

    private int[] partition(int[] nums, int left, int right, int pivot) {
        int less = left - 1;//左括号
        int more = right + 1;//右括号
        int cur = left;
        while (cur < more) {
            if (nums[cur] < pivot) {
                swap(nums, ++less, cur++);
            } else if (nums[cur] > pivot) {
                swap(nums, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[]{less + 1, more - 1};
    }


    public static void shuffle(int[] nums, int l, int r) {
        while (r - l + 1 > 0) {
            int lenAndOne = r - l + 2;
            int bloom = 3;
            int k = 1;
            while (bloom <= lenAndOne / 3) {
                bloom *= 3;
                k++;
            }
            int m = (bloom - 1) / 2;
            int mid = (l + r) / 2;
            rotate(nums, l + m, mid, mid + m);
            cycles(nums, l - 1, bloom, k);
            l = l + bloom - 1;
        }
    }

    public static void cycles(int[] nums, int base, int bloom, int k) {
        for (int i = 0, trigger = 1; i < k; i++, trigger *= 3) {
            int next = (2 * trigger) % bloom;
            int cur = next;
            int record = nums[next + base];
            int tmp = 0;
            nums[next + base] = nums[trigger + base];
            while (cur != trigger) {
                next = (2 * cur) % bloom;
                tmp = nums[next + base];
                nums[next + base] = record;
                cur = next;
                record = tmp;
            }
        }
    }

    public static void rotate(int[] arr, int l, int m, int r) {
        reverse(arr, l, m);
        reverse(arr, m + 1, r);
        reverse(arr, l, r);
    }

    public static void reverse(int[] arr, int l, int r) {
        while (l < r) {
            swap(arr, l++, r--);
        }
    }

    public static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

}
