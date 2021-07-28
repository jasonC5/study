package com.jason.study.algorithm.orderedList;

public class AVL_01 {
    /**
     * AVL树节点Node   --平衡因子 h 高度
     * @param <K>
     * @param <V>
     */
    public static class AVLNode<K extends Comparable<K>, V> {
        public K k;
        public V v;
        public AVLNode<K, V> l;
        public AVLNode<K, V> r;
        public int h;

        public AVLNode(K key, V value) {
            k = key;
            v = value;
            h = 1;
        }
    }

    public static class AVLTreeMap<K extends Comparable<K>, V> {
        private AVLNode<K, V> root;
        private int size;

        public AVLTreeMap() {
            root = null;
            size = 0;
        }

        //核心方法：add 、delete 、整理、左旋、右旋、get

        /**
         * 查找
         *
         * @param key
         * @return
         */
        public V get(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.k) == 0) {
                    break;
                } else if (key.compareTo(cur.k) > 0) {
                    cur = cur.r;
                } else {
                    cur = cur.l;
                }
            }
            return cur.v;
        }

        /**
         * 添加节点，只添加，先判断AVL树中没有再调用此方法添加
         *
         * @param cur
         * @param key
         * @param value
         * @return
         */
        private AVLNode<K, V> add(AVLNode<K, V> cur, K key, V value) {
            //在当前节点的子树上找合适的位置挂上键值对节点，然后把子树整理好，最后返回新的头部
            if (cur == null) {
                return new AVLNode<>(key, value);
            }
            if (key.compareTo(cur.k) < 0) {
                cur.l = add(cur.l, key, value);
            } else {
                cur.r = add(cur.r, key, value);
            }
            cur.h = Math.max(cur.l == null ? 0 : cur.l.h, cur.r == null ? 0 : cur.r.h) + 1;
            //整理本颗子树，往上返，按照递归会依次整理到根节点
            return arrange(cur);
        }

        /**
         * 删除节点，执行删除的方法，先得确认树里面有再调用此方法删除
         *
         * @param cur
         * @param key
         * @return
         */
        private AVLNode<K, V> delete(AVLNode<K, V> cur, K key) {
            if (key.compareTo(cur.k) < 0) {
                cur.l = delete(cur.l, key);
            } else if (key.compareTo(cur.k) > 0) {
                cur.r = delete(cur.r, key);
            } else {
                //要删除的就是本节点，三种情况
                if (cur.l == null && cur.r == null) {
                    //没有孩子节点，直接删
                    cur = null;
                } else if (cur.l == null || cur.r == null) {
                    //有一个为空，另一个孩子顶上
                    cur = cur.l == null ? cur.r : cur.l;
                } else {
                    //两个都不为空
                    //右孩子的最左叶子节点，换上来 --左孩子的最右叶子节点也行，随便来一个
                    AVLNode<K, V> swapNode = cur.r;
                    while (swapNode.l != null) {
                        swapNode = swapNode.l;
                    }
                    //先在右子树上把这个叶子节点删了
                    delete(cur.r, swapNode.k);
                    //篡位
                    swapNode.l = cur.l;
                    swapNode.r = cur.r;
                    cur = swapNode;
                }
            }
            if (cur != null) {
                cur.h = Math.max(cur.l == null ? 0 : cur.l.h, cur.r == null ? 0 : cur.r.h) + 1;
            }
            //整理本颗子树，往上返，按照递归会依次整理到根节点
            return arrange(cur);
        }

        /**
         * 将当前节点的子树重新整理成AVL树
         *
         * @param cur
         * @return
         */
        private AVLNode<K, V> arrange(AVLNode<K, V> cur) {
            //整理当前子树
            if (cur == null) {
                return null;
            }
            int leftH = cur.l == null ? 0 : cur.l.h;
            int rightH = cur.r == null ? 0 : cur.r.h;
            if (Math.abs(leftH - rightH) < 2) {
                //无需整理
                return cur;
            }
            if (leftH > rightH) {
                //LL 或者 LR 或者同时满足   --cur.l == null可以省去的理由：leftH > rightH ==> leftH 一定大于0 ==> cur.l.h > 0 ==> cur.l 必不为空
                int llh = /* cur.l == null || */ cur.l.l == null ? 0 : cur.l.l.h;
                int lrh = /* cur.l == null || */ cur.l.r == null ? 0 : cur.l.l.h;
                if (llh >= lrh) {//LL 和 LL LR 同时满足的情况，都按LL处理，只需要一次右旋
                    cur = rightRotate(cur);
                } else {//LR 先左旋再右旋
                    cur = leftRotate(cur);
                    cur = rightRotate(cur);
                }
            } else {
                //RR 或者 RL 或者同时满足
                int rlh = /* cur.r == null || */ cur.r.l == null ? 0 : cur.r.l.h;
                int rrh = /* cur.r == null || */ cur.r.r == null ? 0 : cur.r.l.h;
                if (rrh >= rlh) {//RR 和 RR RL 同时满足的情况，都按LL处理，只需要一次左旋
                    cur = leftRotate(cur);
                } else {//RL 先右旋再左旋
                    cur = rightRotate(cur);
                    cur = leftRotate(cur);
                }
            }
            return cur;
        }

        /**
         * 当前节点向左下方移动
         * 右孩子上位
         * 右孩子的左孩子指针指向当前节点
         * 当前节点的右孩子指针指向右孩子的左孩子
         * 重新计算两个节点的高度【后计算新头的】
         *
         * @param cur
         * @return
         */
        private AVLNode<K, V> leftRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> next = cur.r;
            cur.r = next.l;
            next.l = cur;
            //重新计算高度【next在上面了，后计算】
            cur.h = Math.max(cur.l == null ? 0 : cur.l.h, cur.r == null ? 0 : cur.r.h) + 1;
            next.h = Math.max(next.l == null ? 0 : next.l.h, next.r == null ? 0 : next.r.h) + 1;
            return next;
        }

        /**
         * 当前节点向右下方移动
         * 左孩子上位
         * 左孩子的右孩子指针指向自己，自己的左指针指向左孩子的右孩子
         * 返回左孩子
         *
         * @param cur
         * @return
         */
        private AVLNode<K, V> rightRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> next = cur.l;
            cur.l = next.r;
            next.r = cur;
            //重新计算高度
            cur.h = Math.max(cur.l == null ? 0 : cur.l.h, cur.r == null ? 0 : cur.r.h) + 1;
            next.h = Math.max(next.l == null ? 0 : next.l.h, next.r == null ? 0 : next.r.h) + 1;
            return next;
        }


        //用上面的方法，拼凑出有序表该有的功能方法
        public int size() {
            return size;
        }

        /**
         * <= key 的 最大的 k 的 node
         *
         * @param key
         * @return
         */
        private AVLNode<K, V> findLastNoBigIndex(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> pre = null;
            AVLNode<K, V> cur = root;
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

        /**
         * >= key 的最小 k 的节点
         *
         * @param key
         * @return
         */
        private AVLNode<K, V> findLastNoSmallIndex(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> pre = null;
            AVLNode<K, V> cur = root;
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
         * 找离 key 最近的 节点 ？
         *
         * @param key
         * @return
         */
        private AVLNode<K, V> findLastIndex(K key) {
            AVLNode<K, V> pre = root;
            AVLNode<K, V> cur = root;
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

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            AVLNode<K, V> lastIndex = findLastIndex(key);
            return lastIndex != null && lastIndex.k.equals(key) ? true : false;
        }

        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            AVLNode<K, V> lastIndex = findLastIndex(key);
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
                //同理，删除也可能会调整数，甚至可能删除的就是头结点，所以要重新记录根节点
                root = delete(root, key);
            }
        }

        public K firstKey() {
            //最左叶子节点
            if (root == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
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
            AVLNode<K, V> cur = root;
            while (cur.r != null) {
                cur = cur.r;
            }
            return cur.k;
        }

        /**
         * 不比当前key大的最大节点
         * @param key
         * @return
         */
        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.k;
        }

        /**
         *          ^
         *          |
         *      ---------   ceiling
         *          O       this
         *      ---------   floor
         *          |
         *          V
         */


        /**
         * 不必当前key小的最小节点
         * @param key
         * @return
         */
        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNoBigNode = findLastNoSmallIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.k;
        }
    }

}
