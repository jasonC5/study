package com.jason.study.algorithmQuestions.class37;

import com.jason.study.algorithm.orderedList.Rewrite_SBT_03;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 假设有打乱顺序的一群人站成一个队列，数组 people 表示队列中一些人的属性（不一定按顺序）。每个 people[i] = [hi, ki] 表示第 i 个人的身高为 hi ，前面 正好 有 ki 个身高大于或等于 hi 的人。
 * <p>
 * 请你重新构造并返回输入数组 people 所表示的队列。返回的队列应该格式化为数组 queue ，其中 queue[j] = [hj, kj] 是队列中第 j 个人的属性（queue[0] 是排在队列前面的人）。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
 * 输出：[[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
 * 解释：
 * 编号为 0 的人身高为 5 ，没有身高更高或者相同的人排在他前面。
 * 编号为 1 的人身高为 7 ，没有身高更高或者相同的人排在他前面。
 * 编号为 2 的人身高为 5 ，有 2 个身高更高或者相同的人排在他前面，即编号为 0 和 1 的人。
 * 编号为 3 的人身高为 6 ，有 1 个身高更高或者相同的人排在他前面，即编号为 1 的人。
 * 编号为 4 的人身高为 4 ，有 4 个身高更高或者相同的人排在他前面，即编号为 0、1、2、3 的人。
 * 编号为 5 的人身高为 7 ，有 1 个身高更高或者相同的人排在他前面，即编号为 1 的人。
 * 因此 [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]] 是重新构造后的队列。
 * 示例 2：
 * <p>
 * 输入：people = [[6,0],[5,0],[4,0],[3,2],[2,2],[1,4]]
 * 输出：[[4,0],[5,0],[2,2],[3,2],[1,4],[6,0]]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/queue-reconstruction-by-height
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC406 {
    //链表
    public int[][] reconstructQueue1(int[][] people) {
        // 先把身高从高到低，前面身高大于等于自己的数从小到大排序
        Arrays.sort(people, (o1, o2) -> (o1[0] == o2[0]) ? (o1[1] - o2[1]) : (o2[0] - o1[0]));
        //链表的方式
        LinkedList<int[]> list = new LinkedList<>();
        for (int[] person : people) {
            list.add(person[1], person);
        }
        int[][] ans = new int[people.length][2];
        int idx = 0;
        Iterator<int[]> iterator = list.iterator();
        while(iterator.hasNext()){
            int[] person = iterator.next();
            ans[idx++] = person;
        }
        return ans;
    }

    public int[][] reconstructQueue(int[][] people) {
        // 先把身高从高到低，前面身高大于等于自己的数从小到大排序
        Arrays.sort(people, (o1, o2) -> (o1[0] == o2[0]) ? (o1[1] - o2[1]) : (o2[0] - o1[0]));
        //改写有序表
        SbtList<int[]> sbtList = new SbtList<>();
        for (int[] person : people) {
            sbtList.add(person[1], person);
        }
        int[][] ans = new int[people.length][2];
        for (int i = 0; i < people.length; i++) {
            ans[i] = sbtList.get(i);
        }
        return ans;
    }

    public static class SBTNode<T> {
        public T value;
        public SBTNode<T> l;
        public SBTNode<T> r;
        public int size;

        public SBTNode(T value) {
            this.value = value;
            size = 1;
        }
    }

    private static class SbtList<T> {
        SBTNode<T> root;


        public Integer size() {
            return root == null ? 0 : root.size;
        }


        public void add(int index, T value) {
            SBTNode<T> cur = new SBTNode<T>(value);
            if (root == null) {
                root = cur;
            } else {
                root = add(root, index, cur);
            }
        }

        private SBTNode<T> maintain(SBTNode<T> node) {
            if (node == null) {
                return null;
            }
            int l = node.l == null ? 0 : node.l.size;
            int r = node.r == null ? 0 : node.r.size;
            int ll = node.l == null || node.l.l == null ? 0 : node.l.l.size;
            int lr = node.l == null || node.l.r == null ? 0 : node.l.r.size;
            int rl = node.r == null || node.r.l == null ? 0 : node.r.l.size;
            int rr = node.r == null || node.r.r == null ? 0 : node.r.r.size;
            if (ll > r) {
                node = rightRotate(node);
                node.r = maintain(node.r);
                node = maintain(node);
            } else if (lr > r) {
                node.l = leftRotate(node.l);
                node = rightRotate(node);
                node.l = maintain(node.l);
                node.r = maintain(node.r);
                node = maintain(node);
            } else if (rl > l) {
                node.r = rightRotate(node.r);
                node = leftRotate(node);
                node.l = maintain(node.l);
                node.r = maintain(node.r);
                node = maintain(node);
            } else if (rr > l) {
                node = leftRotate(node);
                node.l = maintain(node.l);
                node = maintain(node);
            }
            return node;
        }

        private SBTNode<T> leftRotate(SBTNode<T> node) {
            SBTNode<T> next = node.r;
            node.r = next.l;
            next.l = node;
            next.size = node.size;
            node.size = (node.l == null ? 0 : node.l.size) + (node.r == null ? 0 : node.r.size) + 1;
            return next;
        }

        private SBTNode<T> rightRotate(SBTNode<T> node) {
            SBTNode<T> next = node.l;
            node.l = next.r;
            next.r = node;
            next.size = node.size;
            node.size = (node.l == null ? 0 : node.l.size) + (node.r == null ? 0 : node.r.size) + 1;
            return next;
        }

        private SBTNode<T> add(SBTNode<T> cur, int index, SBTNode<T> adder) {
            if (cur == null) {
                return adder;
            }
            cur.size++;
            int leftSize = cur.l == null ? 0 : cur.l.size;
            if (index <= leftSize) {
                //往左树上挂
                cur.l = add(cur.l, index, adder);
            } else {
                //往右树上挂
                cur.r = add(cur.r, index - leftSize - 1, adder);
            }
            return maintain(cur);
        }
        
        public T get(int index) {
            if (index >= 0 && index < size()) {
                SBTNode<T> node = get(root, index);
                return node.value;
            } else {
                return null;
            }
        }

        private SBTNode<T> get(SBTNode<T> cur, int index) {
            int leftSize = cur.l == null ? 0 : cur.l.size;
            if (index < leftSize) {
                return get(cur.l, index);
            } else if (index > leftSize) {
                return get(cur.r, index - leftSize - 1);
            } else {
                return cur;
            }
        }

    }

}
