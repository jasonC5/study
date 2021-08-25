package com.jason.study.algorithmQuestions.class35;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 * 示例 2:
 * <p>
 * 输入: nums = [1], k = 1
 * 输出: [1]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/top-k-frequent-elements
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC347 {

    public class Info {
        int num;
        int count;

        public Info(int num) {
            this.num = num;
            count = 0;
        }
    }

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Info> infoMap = new HashMap<>();
        for (int num : nums) {
            Info info = infoMap.getOrDefault(num, new Info(num));
            info.count++;
            infoMap.put(num, info);
        }

        PriorityQueue<Info> heap = new PriorityQueue<>((o1, o2) -> o1.count - o2.count);
        for (int num : infoMap.keySet()) {
            Info info = infoMap.get(num);
            if (heap.size() < k || info.count > heap.peek().count) {
                heap.offer(info);
            }
            if (heap.size() > k) {
                heap.poll();
            }
        }
        int[] ans = new int[k];
        int idx = 0;
        while (!heap.isEmpty()) {
            ans[idx++] = heap.poll().num;
        }
        return ans;
    }
}
