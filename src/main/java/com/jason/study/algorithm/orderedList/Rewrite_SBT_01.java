package com.jason.study.algorithm.orderedList;

import java.util.HashSet;

/**
 * 第五节课有讲过，归并排序做的  {@link com.jason.study.algorithm.sort.LC327}
 * <p>
 * <p>
 * 1.给定一个数组arr，和两个整数a和b（a<=b）
 * 求arr中有多少个子数组，累加和在[a,b]这个范围上
 * 返回达标的子数组数量
 *
 * @author JasonC5
 */
public class Rewrite_SBT_01 {

    public static int countRangeSum1(int[] nums, int lower, int upper) {
        int n = nums.length;
        long[] sums = new long[n + 1];
        for (int i = 0; i < n; ++i)
            sums[i + 1] = sums[i] + nums[i];
        return countWhileMergeSort(sums, 0, n + 1, lower, upper);
    }

    private static int countWhileMergeSort(long[] sums, int start, int end, int lower, int upper) {
        if (end - start <= 1)
            return 0;
        int mid = (start + end) / 2;
        int count = countWhileMergeSort(sums, start, mid, lower, upper)
                + countWhileMergeSort(sums, mid, end, lower, upper);
        int j = mid, k = mid, t = mid;
        long[] cache = new long[end - start];
        for (int i = start, r = 0; i < mid; ++i, ++r) {
            while (k < end && sums[k] - sums[i] < lower)
                k++;
            while (j < end && sums[j] - sums[i] <= upper)
                j++;
            while (t < end && sums[t] < sums[i])
                cache[r++] = sums[t++];
            cache[r] = sums[i];
            count += j - k;
        }
        System.arraycopy(cache, 0, sums, start, t - start);
        return count;
    }


    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static int[] generateArray(int len, int varible) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * varible);
        }
        return arr;
    }

//    public static void main(String[] args) {
//        int len = 200;
//        int varible = 50;
//        for (int i = 0; i < 100000; i++) {
//            int[] test = generateArray(len, varible);
//            int lower = (int) (Math.random() * varible) - (int) (Math.random() * varible);
//            int upper = lower + (int) (Math.random() * varible);
//            int ans1 = countRangeSum1(test, lower, upper);
//            int ans2 = countRangeSum2(test, lower, upper);
//            if (ans1 != ans2) {
//                printArray(test);
//                System.out.println(lower);
//                System.out.println(upper);
//                System.out.println(ans1);
//                System.out.println(ans2);
//                break;
//            }
//        }
//    }

    public static void main(String[] args) {
        int[] test = {-3, 1, 2, -2, 2, -1};
        int lower = -3;
        int upper = -1;
        int ans1 = countRangeSum1(test, lower, upper);
        int ans2 = countRangeSum2(test, lower, upper);
        System.out.println(ans1);
        System.out.println(ans2);
    }

    // 子数组，累加和在[a,b]范围范围上
    // sum(b) - sum(a)  在 [a,b]上
    // 若 sum(b) = x ,则 求前面的前缀和范围在[x-b，x-a] 的，有多少个
    // 需要有一个结构，和新方法：add（） 能重复添加， 小于某个数字的数有多少个
    public static int countRangeSum2(int[] nums, int lower, int upper) {
        SizeBalancedTreeSet treeSet = new SizeBalancedTreeSet();
        if (nums == null || nums.length == 0) {
            return 0;
        }
        long ans = 0;
        //先求前缀和
        long sum = 0;
        //得先往结构里面仍个0
        treeSet.add(0);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];//前缀和
            long a = treeSet.lessKeySize(sum - lower + 1);
            long b = treeSet.lessKeySize(sum - upper);
            ans += a - b;
            treeSet.add(sum);
        }
        return (int) ans;
    }

    public static class SBTNode {
        public long key;
        public SBTNode l;
        public SBTNode r;
        public long size; // 不同key的size
        public long all; // 所有的key出现过的总次数

        public SBTNode(long k) {
            key = k;
            size = 1;
            all = 1;
        }
    }

    public static class SizeBalancedTreeSet {
        private SBTNode root;
        private HashSet<Long> set = new HashSet<>();

        public void add(long key) {
            boolean contains = set.contains(key);
            root = add(root, key, contains);
            set.add(key);
        }

        //以node为头的子树上，添加key
        private SBTNode add(SBTNode node, long key, boolean contains) {
            if (node == null) {
                return new SBTNode(key);
            }
            node.all++;
            if (!contains) {
                //一定会加节点
                node.size++;
            }
            if (key < node.key) {
                //往我左树上加
                node.l = add(node.l, key, contains);
            } else if (key > node.key) {
                //往我右树上加
                node.r = add(node.r, key, contains);
            }//else 相等了，all++ 上面已经处理了
            return arrange(node);
        }

        private SBTNode arrange(SBTNode cur) {
            if (cur == null) {
                return null;
            }
            long l = cur.l == null ? 0 : cur.l.size;
            long ll = cur.l == null || cur.l.l == null ? 0 : cur.l.l.size;
            long lr = cur.l == null || cur.l.r == null ? 0 : cur.l.r.size;
            long r = cur.r == null ? 0 : cur.r.size;
            long rl = cur.r == null || cur.r.l == null ? 0 : cur.r.l.size;
            long rr = cur.r == null || cur.r.r == null ? 0 : cur.r.r.size;
            if (ll > r) {
                //一样的右旋
                cur = rightRotate(cur);
                //谁的子发生变动了？
                // (原本的根节点和原本的根节点的左孩子，但是，当前的头节点已经换了，已经换成之前的左孩子了，之前的头节点已经变成现在的头节点的右节点了！！)
                cur.r = arrange(cur.r);
                cur = arrange(cur);
            } else if (lr > r) {
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = arrange(cur.l);
                cur.r = arrange(cur.r);
                cur = arrange(cur);
            } else if (rr > l) {
                cur = leftRotate(cur);
                cur.l = arrange(cur.l);
                cur = arrange(cur);
            } else if (rl > l) {
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
                cur.l = arrange(cur.l);
                cur.r = arrange(cur.r);
                cur = arrange(cur);
            }
            return cur;
        }

        private SBTNode leftRotate(SBTNode cur) {
            //左旋
            long curNum = cur.all - (cur.l == null ? 0L : cur.l.all) - (cur.r == null ? 0L : cur.r.all);
            SBTNode next = cur.r;
            cur.r = next.l;
            next.l = cur;
            //继承你的size
            next.size = cur.size;
            //继承你的all
            next.all = cur.all;
            //重算 cur size
            cur.size = (cur.l == null ? 0L : cur.l.size) + (cur.r == null ? 0L : cur.r.size) + 1;
            //重算 cur all
            cur.all = curNum + (cur.l == null ? 0L : cur.l.all) + (cur.r == null ? 0L : cur.r.all);
            return next;
        }

        private SBTNode rightRotate(SBTNode cur) {
            long curNum = cur.all - (cur.l == null ? 0L : cur.l.all) - (cur.r == null ? 0L : cur.r.all);
            SBTNode next = cur.l;
            cur.l = next.r;
            next.r = cur;
            //继承你的size
            next.size = cur.size;
            //继承你的all
            next.all = cur.all;
            //重算 cur size
            cur.size = (cur.l == null ? 0L : cur.l.size) + (cur.r == null ? 0L : cur.r.size) + 1;
            //重算 cur all
            cur.all = curNum + (cur.l == null ? 0L : cur.l.all) + (cur.r == null ? 0L : cur.r.all);
            return next;
        }

        /**
         * 小于key的总数
         *
         * @param key
         * @return
         */
        public long lessKeySize(long key) {
            SBTNode cur = root;
            long ans = 0;
            while (cur != null) {
                //左边比自己小，右边比自己大，自己的数量 = 自己的all - 左边的all - 右边的all
                long leftCount = cur.l == null ? 0L : cur.l.all;
                long rightCount = cur.r == null ? 0L : cur.r.all;
                long selfAll = cur.all - leftCount - rightCount;
                if (cur.key < key) {
                    cur = cur.r;
                    ans += leftCount + selfAll;
                } else if (cur.key > key) {
                    cur = cur.l;
                } else {//==
                    ans += leftCount;
                    break;
                }
            }
            return ans;
        }
    }

}
