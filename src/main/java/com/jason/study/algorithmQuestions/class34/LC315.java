package com.jason.study.algorithmQuestions.class34;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个整数数组 nums，按要求返回一个新数组 counts。数组 counts 有该性质： counts[i] 的值是  nums[i] 右侧小于 nums[i] 的元素的数量。
 *
 *  
 *
 * 示例：
 *
 * 输入：nums = [5,2,6,1]
 * 输出：[2,1,1,0]
 * 解释：
 * 5 的右侧有 2 个更小的元素 (2 和 1)
 * 2 的右侧仅有 1 个更小的元素 (1)
 * 6 的右侧有 1 个更小的元素 (1)
 * 1 的右侧有 0 个更小的元素
 *  
 *
 * 提示：
 *
 * 0 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/count-of-smaller-numbers-after-self
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC315 {

    public static class Node {
        public int value;
        public int index;

        public Node(int v, int i) {
            value = v;
            index = i;
        }
    }

    public List<Integer> countSmaller(int[] nums) {
        //逆序对问题
        List<Integer> ans = new ArrayList<>();
        if (nums == null) {
            return ans;
        }
        //chushihua
        for (int i = 0; i < nums.length; i++) {
            ans.add(0);
        }
        if (nums.length < 2) {
            return ans;
        }
        Node[] arr = new Node[nums.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Node(nums[i], i);
        }
        process(arr, 0, arr.length - 1, ans);
        return ans;
    }

    private void process(Node[] arr, int left, int right, List<Integer> ans) {
        if (left == right){
            return;
        }
        int mid = left + ((right - left) >> 1);
        process(arr, left, mid, ans);
        process(arr, mid + 1, right, ans);
        merge(arr, left, mid, right, ans);
    }
    //TODO
    private void merge(Node[] arr, int left, int mid, int right, List<Integer> ans) {
        Node[] help = new Node[right - left + 1];
        int i = help.length - 1;
        int p1 = mid;
        int p2 = right;
        while (p1 >= left && p2 >= mid + 1) {
            if (arr[p1].value > arr[p2].value) {
                ans.set(arr[p1].index, ans.get(arr[p1].index) + p2 - mid);
            }
            help[i--] = arr[p1].value > arr[p2].value ? arr[p1--] : arr[p2--];
        }
        while (p1 >= left) {
            help[i--] = arr[p1--];
        }
        while (p2 >= mid + 1) {
            help[i--] = arr[p2--];
        }
        for (i = 0; i < help.length; i++) {
            arr[left + i] = help[i];
        }
    }

}
