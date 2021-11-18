package com.jason.study.algorithmQuestions.class34;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * 给你一个嵌套的整数列表 nestedList 。每个元素要么是一个整数，要么是一个列表；该列表的元素也可能是整数或者是其他列表。请你实现一个迭代器将其扁平化，使之能够遍历这个列表中的所有整数。
 * <p>
 * 实现扁平迭代器类 NestedIterator ：
 * <p>
 * NestedIterator(List<NestedInteger> nestedList) 用嵌套列表 nestedList 初始化迭代器。
 * int next() 返回嵌套列表的下一个整数。
 * boolean hasNext() 如果仍然存在待迭代的整数，返回 true ；否则，返回 false 。
 * 你的代码将会用下述伪代码检测：
 * <p>
 * initialize iterator with nestedList
 * res = []
 * while iterator.hasNext()
 * append iterator.next() to the end of res
 * return res
 * 如果 res 与预期的扁平化列表匹配，那么你的代码将会被判为正确。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nestedList = [[1,1],2,[1,1]]
 * 输出：[1,1,2,1,1]
 * 解释：通过重复调用 next 直到 hasNext 返回 false，next 返回的元素的顺序应该是: [1,1,2,1,1]。
 * 示例 2：
 * <p>
 * 输入：nestedList = [1,[4,[6]]]
 * 输出：[1,4,6]
 * 解释：通过重复调用 next 直到 hasNext 返回 false，next 返回的元素的顺序应该是: [1,4,6]。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/flatten-nested-list-iterator
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC341 {

    public interface NestedInteger {

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return empty list if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }

    public class NestedIterator implements Iterator<Integer> {
        private boolean used;
        private Stack<Integer> stack;
        private List<NestedInteger> list;

        public NestedIterator(List<NestedInteger> nestedList) {
            stack = new Stack<>();
            stack.push(-1);
            used = true;
            list = nestedList;
            hasNext();
        }

        @Override
        public Integer next() {
            Integer ans = null;
            if (!used) {
                ans = get(list, stack);
                used = true;
                //自动找下一个，并把used标回true
                hasNext();
            }
            return ans;
        }

        private Integer get(List<NestedInteger> list, Stack<Integer> stack) {
            int index = stack.pop();
            Integer ans = null;
            if (!stack.isEmpty()) {
                ans = get(list.get(index).getList(), stack);
            } else {
                ans = list.get(index).getInteger();
            }
            //把栈重新压回去
            stack.push(index);
            return ans;
        }

        @Override
        public boolean hasNext() {
            if (stack.isEmpty()) {
                return false;
            } else if (!used) {
                return true;
            }
            //找下一个
            if (findNext(list, stack)) {
                used = false;
            }
            return !used;
        }

        private boolean findNext(List<NestedInteger> list, Stack<Integer> stack) {
            //1.根这弹栈，进入最终的list
            Integer pop = stack.pop();
            //如果栈没空，就继续往下弹，如果在下面一层处理好了就原样压回去，
            if (!stack.isEmpty() && findNext(list.get(pop).getList(), stack)) {
                stack.push(pop);
                return true;
            }
            //如果不能往下了，说明要么本层就是最底层，要么下一层搞不定了，本层往后跳，都需要往后走
//            if (list.get(pop + 1).isInteger()) {
//                stack.push(pop + 1);
//                used = false;
//                return true;
//            } else {
//                //next是
//            }
            //防止有可能是空数组
            for (int i = pop + 1; i < list.size(); i++) {
                if (pickFirst(list.get(i), i, stack)) {
                    return true;
                }
            }
            return false;
        }

        private boolean pickFirst(NestedInteger nestedInteger, int index, Stack<Integer> stack) {
            if (nestedInteger.isInteger()) {
                stack.add(index);
                return true;
            } else {
                List<NestedInteger> actualList = nestedInteger.getList();
                for (int i = 0; i < actualList.size(); i++) {
                    if (pickFirst(actualList.get(i), i, stack)) {
                        stack.add(index);
                        return true;
                    }
                }
            }
            return false;
        }
    }

}
