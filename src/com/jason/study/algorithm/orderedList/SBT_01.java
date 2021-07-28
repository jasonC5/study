package com.jason.study.algorithm.orderedList;

/**
 * size balance tree
 *
 * @author JasonC5
 */
public class SBT_01 {
    /**
     * SB树节点Node    --平衡因子 size 子树节点数
     *
     * @param <K>
     * @param <V>
     */
    public static class SBTNode<K extends Comparable<K>, V> {
        public K k;
        public V v;
        public SBTNode<K, V> l;
        public SBTNode<K, V> r;
        public int size;

        public SBTNode(K key, V value) {
            k = key;
            v = value;
            size = 1;
        }
    }

    public static class SizeBalancedTreeMap<K extends Comparable<K>, V> {
        private SBTNode<K, V> root;

        // 同样的，核心方法：左旋、右旋、整理、add、delete
        // 不同的是，左旋、右旋里面会调用整理，整理又会调用左旋右旋，每一个变更过的节点的父节点都需要重新整理【谁的孩子变了要做递归的调整】
        // 这是由于SBT的平衡特性导致的

        private SBTNode<K, V> add(SBTNode<K, V> cur, K key, V value) {
            if (cur == null) {
                return new SBTNode<>(key, value);
            }
            cur.size++;
            if (key.compareTo(cur.k) < 0) {
                cur.l = add(cur.l, key, value);
            } else {
                cur.r = add(cur.r, key, value);
            }
//            cur.size = (cur.l == null ? 0 : cur.l.size )+ ( cur.r == null ? 0 : cur.r.size) + 1; -- 这里就等价于上面的 cur.size++;
            return arrange(cur);
        }

        private SBTNode<K, V> arrange(SBTNode<K, V> cur) {
            if (cur == null) {
                return null;
            }
            //某一节点的子树节点数 大于 其叔叔节点的子树节点数的时候，需要进行调整，先把几个节点数捞出来
            int l = cur.l == null ? 0 : cur.l.size;
            int ll = cur.l == null || cur.l.l == null ? 0 : cur.l.l.size;
            int lr = cur.l == null || cur.l.r == null ? 0 : cur.l.r.size;
            int r = cur.r == null ? 0 : cur.r.size;
            int rl = cur.r == null || cur.r.l == null ? 0 : cur.r.l.size;
            int rr = cur.r == null || cur.r.r == null ? 0 : cur.r.r.size;
            if (ll > r) {
                //一样的右旋
                cur = rightRotate(cur);
                //谁的子发生变动了？
                // (原本的根节点和原本的根节点的左孩子，但是，当前的头节点已经换了，已经换成之前的左孩子了，之前的头节点已经变成现在的头节点的右节点了！！)
                cur.r = arrange(cur.r);
                cur = arrange(cur);
            } else if (lr > r) {
                cur = leftRotate(cur);
                cur = rightRotate(cur);
                cur.l = arrange(cur.l);
                cur.r = arrange(cur.r);
                cur = arrange(cur);
            } else if (rr > l) {
                cur = leftRotate(cur);
                cur.l = arrange(cur.l);
                cur = arrange(cur);
            } else if (rl > l) {
                cur = rightRotate(cur);
                cur = leftRotate(cur);
                cur.l = arrange(cur.l);
                cur.r = arrange(cur.r);
                cur = arrange(cur);
            }
            return cur;
        }

        private SBTNode<K, V> leftRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> next = cur.r;
            cur.r = next.l;
            //上位
            next.size = cur.size;
            next.l = cur;
            //重新计算size
            cur.size = (cur.l == null ? 0 : cur.l.size) + (cur.r == null ? 0 : cur.r.size) + 1;
            return next;
        }

        private SBTNode<K, V> rightRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> next = cur.l;
            cur.l = next.r;
            next.r = cur;
            next.size = cur.size;
            cur.size = (cur.l == null ? 0 : cur.l.size) + (cur.r == null ? 0 : cur.r.size) + 1;
            return next;
        }

        private SBTNode<K, V> delete(SBTNode<K, V> cur, K key) {
            cur.size--;
            if (key.compareTo(cur.k) > 0) {
                cur = delete(cur.r, key);
            } else if (key.compareTo(cur.k) < 0) {
                cur = delete(cur.l, key);
            } else {
                if (cur.l == null && cur.r == null) {
                    //没有孩子节点，直接删
                    cur = null;
                } else if (cur.l == null || cur.r == null) {
                    //有一个为空，另一个孩子顶上
                    cur = cur.l == null ? cur.r : cur.l;//但是下面的节点没发生变化，所以不用计算size
                } else {
                    //两个都不为空
                    //右孩子的最左叶子节点，换上来 --左孩子的最右叶子节点也行，随便来一个
                    //沿途所有节点size--

                    //第一步找，第二步删
//                    SBTNode<K, V> swapNode = cur.r;
//                    while (swapNode.l != null) {
//                        swapNode = swapNode.l;
//                    }
//                    //先在右子树上把这个叶子节点删了
//                    delete(cur.r, swapNode.k);
//                    //夺舍
//                    swapNode.l = cur.l;
//                    swapNode.r = cur.r;
//                    swapNode.size = cur.size;

                    //两步优化成一步,找的过程中就把size减掉，并且最终删掉节点
                    SBTNode<K, V> pre = null;
                    SBTNode<K, V> swapNode = cur.r;
                    swapNode.size--;
                    while (swapNode.l != null) {
                        pre = swapNode;
                        swapNode = swapNode.l;
                        swapNode.size--;
                    }
                    //说明不是该节点的右子节点，而是确实往下跑了
                    if (pre != null) {
                        //接盘
                        pre.l = swapNode.r;
                    }
                    swapNode.l = cur.l;
                    swapNode.r = cur.r;
//                    swapNode.size = cur.size;//这一句应该也可以把
                    swapNode.size = (swapNode.l == null ? 0 : swapNode.l.size) + (swapNode.r == null ? 0 : swapNode.r.size) + 1;
                    cur = swapNode;
                }
            }
            //可调整可不调整 cur=arrange(cur);
            return cur;
        }

        private SBTNode<K, V> getIndex(SBTNode<K, V> cur, int kth) {
            int idx = (cur.l == null ? 0 : cur.l.size) + 1; //当前节点，再自己这颗子树上，的位置index
            if (idx == kth) {
                return cur;
            } else if (idx < kth) {//比我大，去右半边查，但是 着地几个数，要减去半包含自己的那部分
                return getIndex(cur.r, kth - idx);
            } else {//去我左边去查，该查第几个，还查第几个
                return getIndex(cur.l, kth);
            }
        }

        public int size() {
            return root == null ? 0 : root.size;
        }

        private SBTNode<K, V> findLastIndex(K key) {
            SBTNode<K, V> pre = root;
            SBTNode<K, V> cur = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.k) > 0) {
                    cur = cur.r;
                } else if (key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else {
                    //等于了，直接返回
                    return cur;
                }
            }
            return pre;
        }

        /**
         * >= key 的最小 k 的节点
         *
         * @param key
         * @return
         */
        private SBTNode<K, V> findLastNoSmallIndex(K key) {
            SBTNode<K, V> pre = null;
            SBTNode<K, V> cur = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.k) > 0) {
//                    cur = cur.r;
                    //这个节点比我小，我要找不小于我的的最大节点，前提是不小于我，小的不要
                } else if (key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else {
                    //等于了，直接返回
                    return cur;
                }
            }
            return pre;
        }
        /**
         * <= key 的 最大的 k 的 node
         *
         * @param key
         * @return
         */
        private SBTNode<K, V> findLastNoBigIndex(K key) {
            if (key == null) {
                return null;
            }
            SBTNode<K, V> pre = null;
            SBTNode<K, V> cur = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.k) > 0) {
                    cur = cur.r;
                } else if (key.compareTo(cur.k) < 0) {
//                    cur = cur.l;
                    //这个节点比我大，我要找不大于我的的最大节点，前提是比我小或者相等，大的不要
                } else {
                    //等于了，直接返回
                    return cur;
                }
            }
            return pre;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            SBTNode<K, V> lastIndex = findLastIndex(key);
            return lastIndex != null && lastIndex.k.equals(key) ? true : false;
        }

        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            SBTNode<K, V> lastIndex = findLastIndex(key);
            //有这个key
            if (lastIndex != null && lastIndex.k.equals(key)) {
                lastIndex.v = value;
            } else {
                //没有，新增 --新增可能会整理树，要更新root
                root = add(root, key, value);
            }
        }

        public void remove(K key) {
            if (key == null) {
                return;
            }
            if (containsKey(key)) {
                root = delete(root, key);
            }
        }

        public K getIndexKey(int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("invalid parameter.");
            }
            return getIndex(root, index + 1).k;
        }

        public V getIndexValue(int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("invalid parameter.");
            }
            return getIndex(root, index + 1).v;
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }
//            SBTNode<K, V> cur = root;
//            while (cur != null) {
//                if (key.compareTo(cur.k) == 0) {
//                    break;
//                } else if (key.compareTo(cur.k) > 0) {
//                    cur = cur.r;
//                } else {
//                    cur = cur.l;
//                }
//            }
//            return cur.v;
            SBTNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.k) == 0) {
                return lastNode.v;
            } else {
                return null;
            }
        }

        public K firstKey() {
            //最左叶子节点
            if (root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            while (cur.l != null) {
                cur = cur.l;
            }
            return cur.k;
        }

        public K lastKey() {
            //最右叶子节点
            if (root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            while (cur.r != null) {
                cur = cur.r;
            }
            return cur.k;
        }

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            SBTNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.k;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            SBTNode<K, V> lastNoBigNode = findLastNoSmallIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.k;
        }
    }

}
