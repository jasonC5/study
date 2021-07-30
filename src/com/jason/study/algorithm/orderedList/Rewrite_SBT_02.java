package com.jason.study.algorithm.orderedList;

import java.util.Arrays;

/**
 *
 * https://leetcode-cn.com/problems/sliding-window-median/
 * 有一个滑动窗口（讲过的）：
 * 1）L是滑动窗口最左位置、R是滑动窗口最右位置，一开始LR都在数组左侧
 * 2）任何一步都可能R往右动，表示某个数进了窗口
 * 3）任何一步都可能L往右动，表示某个数出了窗口
 * 想知道每一个窗口状态的中位数
 *
 * @author JasonC5
 */
public class Rewrite_SBT_02 {

    // 一个接口，可以往里面放数字，可以放重复数字
    // 可以删除一个数字
    // 可以找到指定位置的数
    public static void main(String[] args) {
//        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
//        int win = 3;
        int[] nums = {1, 4, 2, 3};
        int win = 4;


        Arrays.stream(medianSlidingWindow(nums, win)).boxed().forEach(System.out::println);
    }

    public static class SBTNode<K extends Comparable<K>> {
        public K key;
        public SBTNode<K> l;
        public SBTNode<K> r;
        public int size;

        public SBTNode(K k) {
            key = k;
            size = 1;
        }
    }

    //key
    public static class Node implements Comparable<Node> {
        public int index;
        public int value;

        public Node(int i, int v) {
            index = i;
            value = v;
        }

        @Override
        public int compareTo(Node o) {
            return value != o.value ? Integer.valueOf(value).compareTo(o.value)
                    : Integer.valueOf(index).compareTo(o.index);
        }
    }

    public static class SizeBalancedTreeMap<K extends Comparable<K>> {
        private SBTNode<K> root;

        public void add(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }
            SBTNode<K> lastNode = findLastIndex(key);
            if (lastNode == null || key.compareTo(lastNode.key) != 0) {
                root = add(root, key);
            }
        }

        public void remove(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }
            if (containsKey(key)) {
                root = remove(root, key);
            }
        }

        private SBTNode<K> add(SBTNode<K> node, K key) {
            if (node == null) {
                return new SBTNode<>(key);
            }
            node.size++;
            if (key.compareTo(node.key) < 0) {
                node.l = add(node.l, key);
            } else {
                node.r = add(node.r, key);
            }
            return maintail(node);
        }

        public SBTNode<K> remove(SBTNode<K> node, K key) {
            node.size--;
            if (key.compareTo(node.key) < 0) {
                node.l = remove(node.l, key);
            } else if (key.compareTo(node.key) > 0) {
                node.r = remove(node.r, key);
            } else {
                if (node.l == null && node.r == null) {
                    node = null;
                } else if (node.l == null || node.r == null) {
                    node = node.l == null ? node.r : node.l;
                } else {
                    //右树的最左节点来替
                    SBTNode<K> pre = null;
                    SBTNode<K> next = node.r;
                    next.size--;//要从这个子树上删掉一个节点
                    while (next.l != null) {
                        pre = next;
                        next = next.l;
                        next.size--;
                    }
                    //右树的最左子树，不一定是叶子节点，
                    if (pre != null) {
                        pre.l = next.r;
                        //不能放下面，否则就相互引用了，导致死循环
                        next.r = node.r;
                    }
                    next.l = node.l;
                    next.size = node.size;//next.size = (next.l == null ? 0:next.l.size )+(next.r == null ? 0:next.r.size)+1
                    node = next;
                }
            }
//            return maintail(node);
            return node;
        }

        private SBTNode<K> maintail(SBTNode<K> node) {
            int l = node.l == null ? 0 : node.l.size;
            int r = node.r == null ? 0 : node.r.size;
            int ll = node.l == null || node.l.l == null ? 0 : node.l.l.size;
            int lr = node.l == null || node.l.r == null ? 0 : node.l.r.size;
            int rl = node.r == null || node.r.l == null ? 0 : node.r.l.size;
            int rr = node.r == null || node.r.r == null ? 0 : node.r.r.size;
            if (ll > r) {
                node = rightRotate(node);
                node.r = maintail(node.r);
                node = maintail(node);
            } else if (lr > r) {
                node.l = leftRotate(node.l);
                node = rightRotate(node);
                node.l = maintail(node.l);
                node.r = maintail(node.r);
                node = maintail(node);
            } else if (rl > l) {
                node.r = rightRotate(node.r);
                node = leftRotate(node);
                node.l = maintail(node.l);
                node.r = maintail(node.r);
                node = maintail(node);
            } else if (rr > l) {
                node = leftRotate(node);
                node.l = maintail(node.l);
                node = maintail(node);
            }
            return node;
        }

        private SBTNode<K> leftRotate(SBTNode<K> node) {
            SBTNode<K> next = node.r;
            node.r = next.l;
            next.l = node;
            next.size = node.size;
            node.size = (node.l == null ? 0 : node.l.size) + (node.r == null ? 0 : node.r.size) + 1;
            return next;
        }

        private SBTNode<K> rightRotate(SBTNode<K> node) {
            SBTNode<K> next = node.l;
            node.l = next.r;
            next.r = node;
            next.size = node.size;
            node.size = (node.l == null ? 0 : node.l.size) + (node.r == null ? 0 : node.r.size) + 1;
            return next;
        }


        public K getIndexKey(int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("invalid parameter.");
            }
            return getIndex(root, index + 1).key;
        }

        private SBTNode<K> getIndex(SBTNode<K> node, int i) {
            int leftIndex = node.l == null ? 0 : node.l.size;
            if (leftIndex + 1 == i) {
                return node;
            } else if (i <= leftIndex) {
                return getIndex(node.l, i);
            } else {
                return getIndex(node.r, i - leftIndex - 1);
            }
        }

        public int size() {
            return root == null ? 0 : root.size;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }
            SBTNode<K> lastNode = findLastIndex(key);
//            return lastNode != null && key.compareTo(lastNode.key) == 0 ? true : false;
            return lastNode != null && key.compareTo(lastNode.key) == 0;
        }

        private SBTNode<K> findLastIndex(K key) {
            SBTNode<K> pre = null;
            SBTNode<K> cur = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.key) == 0) {
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }
            return pre;
        }


    }

    public static double[] medianSlidingWindow(int[] nums, int k) {
        SizeBalancedTreeMap<Node> map = new SizeBalancedTreeMap<>();
        //先把前k-1个放进去
        for (int i = 0; i < k - 1; i++) {
            map.add(new Node(i, nums[i]));
        }
        double[] ans = new double[nums.length - k + 1];
        int index = 0;
        //放一个，取出来一个，求中位数
        for (int i = k - 1; i < nums.length; i++) {
            map.add(new Node(i, nums[i]));
            int size = map.size();
            if ((size & 1) == 0) {
                //偶数，找中间两个
                Node node1 = map.getIndexKey(size / 2 -1);
                Node node2 = map.getIndexKey(size / 2);
                ans[index++] = ((double) node1.value + (double) node2.value) / 2;
            } else {
                Node node1 = map.getIndexKey(size / 2);
                ans[index++] = node1.value;
            }
            map.remove(new Node(i - k + 1, nums[i - k + 1]));
        }
        return ans;
    }


}
