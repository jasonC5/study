package com.jason.study.algorithmQuestions.class34;

import java.util.PriorityQueue;

/**
 * 中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
 * <p>
 * 例如，
 * <p>
 * [2,3,4]的中位数是 3
 * <p>
 * [2,3] 的中位数是 (2 + 3) / 2 = 2.5
 * <p>
 * 设计一个支持以下两种操作的数据结构：
 * <p>
 * void addNum(int num) - 从数据流中添加一个整数到数据结构中。
 * double findMedian() - 返回目前所有元素的中位数。
 * 示例：
 * <p>
 * addNum(1)
 * addNum(2)
 * findMedian() -> 1.5
 * addNum(3)
 * findMedian() -> 2
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-median-from-data-stream
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC295 {

    class MedianFinder {
        PriorityQueue<Integer> maxHeap;
        PriorityQueue<Integer> minHeap;

        /**
         * initialize your data structure here.
         */
        public MedianFinder() {
            maxHeap = new PriorityQueue<Integer>((o1, o2) -> o2 - o1);
            minHeap = new PriorityQueue<Integer>((o1, o2) -> o1 - o2);
        }

        public void addNum(int num) {
            Integer leftPeek = maxHeap.peek();
            if (leftPeek == null || num <= leftPeek) {
                maxHeap.offer(num);
            } else {
                minHeap.offer(num);
            }
            balance();
        }

        private void balance() {
            if (maxHeap.size() - minHeap.size() == 2) {
                minHeap.offer(maxHeap.poll());
            }
            if (minHeap.size() - maxHeap.size() == 2) {
                maxHeap.offer(minHeap.poll());
            }
        }

        public double findMedian() {
            if (maxHeap.size() == minHeap.size()) {
                return maxHeap.size() == 0 ? 0 : (double)(maxHeap.peek() + minHeap.peek()) / 2;
            } else {
                return maxHeap.size() > minHeap.size() ? maxHeap.peek() : minHeap.peek();
            }
        }
    }
}
