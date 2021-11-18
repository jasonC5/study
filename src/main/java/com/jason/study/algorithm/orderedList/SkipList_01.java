package com.jason.study.algorithm.orderedList;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 跳表
 *
 * @author JasonC5
 */
public class SkipList_01 {
    // 跳表的节点定义，每一个节点，都有 k,v 以及多级索引List
    public static class SkipNode<K extends Comparable<K>, V> {
        public K k;
        public V v;
        public ArrayList<SkipNode<K, V>> indexMansion;//一层一层指针

        public SkipNode(K k, V v) {
            this.k = k;
            this.v = v;
            indexMansion = new ArrayList<SkipNode<K, V>>();
        }

        public boolean isKeyEqual(K key) {
            return (key == null && k == null) || (key != null && k != null && key.compareTo(k) == 0);
        }

        //当前节点的key是否小于 key
        public boolean isKeyLess(K key) {
            return key != null && (k == null || k.compareTo(key) < 0);
        }

    }

    public static class SkipListMap<K extends Comparable<K>, V> {
        private static final double PROBABILITY = 0.5; // 升层系数
        private SkipNode<K, V> head;
        private int size;//结构内数量
        private int maxLevel;//最高索引层数

        public SkipListMap() {
            head = new SkipNode<K, V>(null, null);//初始化头节点
            head.indexMansion.add(null); // 第0层的指针，先占个位置
            size = 0;
            maxLevel = 0;
        }

        /**
         * 新增、改value
         *
         * @param key
         * @param value
         */
        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            //找有没有这个key，找到了修改value，找不到，在合适的位置，新增
            //从最高层索引，推到0层索引
            // 0层上，最右一个，< key 的Node -> >key
            SkipNode<K, V> less = mostRightLessNodeInTree(key);
            SkipNode<K, V> find = less.indexMansion.get(0);
            if (find != null && find.isKeyEqual(key)) {
                find.v = value;
                return;
            }
            //没找到，开始走新增逻辑
            size++;
            //扔色子判断当前节点，搞几层索引
            int newNodeLevel = 0;
            while (Math.random() < PROBABILITY) {
                newNodeLevel++;
            }
            //有没有突破之前的最高层，如果突破了，head的index层数增加，【并且新增加的索引指针全部指向这个新增节点--这个可以和后面合并统一做更方便】
            while (newNodeLevel > maxLevel) {
                head.indexMansion.add(null);
                maxLevel++;
            }
            SkipNode<K, V> newNode = new SkipNode<>(key, value);
            //先把新节点的索引楼盖起来，指针先不指，和上面一样，统一处理
            for (int i = 0; i <= newNodeLevel; i++) {
                newNode.indexMansion.add(null);
            }
            //从高层，到低层，依次将索引维护上
            int curLevel = maxLevel;
            SkipNode<K, V> pre = head;
            while (curLevel >= 0) {
                //先把本层的往右推到合适的位置（下一个就大于 key，或者为空）
                pre = mostRightLessNodeInLevel(key, pre, curLevel);
                //随机出来的索引层高，不一定就比整体层高还高，从自己的层高最高点开始搞
                if (newNodeLevel >= curLevel) {
                    newNode.indexMansion.set(curLevel, pre.indexMansion.get(curLevel));
                    pre.indexMansion.set(curLevel, newNode);
                }
                curLevel--;
            }
            //插完收工
        }

        /**
         * 整个结构内最接近key但是小于key的节点
         *
         * @param key
         * @return
         */
        private SkipNode<K, V> mostRightLessNodeInTree(K key) {
            if (key == null) {
                return null;
            }
            //一层层往下推，直到第0层
            int curLevel = maxLevel;
            SkipNode<K, V> cur = head;
            while (curLevel >= 0) {
                cur = mostRightLessNodeInLevel(key, cur, curLevel--);
            }
            return cur;
        }

        /**
         * 在某一层索引链中，左边最接近key的节点  < 的最大值
         *
         * @param key
         * @param cur
         * @param curLevel
         * @return
         */
        private SkipNode<K, V> mostRightLessNodeInLevel(K key, SkipNode<K, V> cur, int curLevel) {
            SkipNode<K, V> next = cur.indexMansion.get(curLevel);
            while (next != null && next.isKeyLess(key)) {
                cur = next;
                next = cur.indexMansion.get(curLevel);
            }
            return cur;
        }

        /**
         * 删除
         *
         * @param key
         */
        public void remove(K key) {
            if (key == null) {
                return;
            }
            //有就删，没有就不删
            if (containsKey(key)) {
                //有，处理删除    1.size-1    2.从最高节点往下，依次删掉这个节点  3.若发现这个节点删掉之后这层空了，把这层删掉，最大层数-1
                size--;
                SkipNode<K, V> pre = head;
                int curLevel = maxLevel;
                while (curLevel >= 0) {
                    //往右推到最接近key的位置
                    pre = mostRightLessNodeInLevel(key, pre, curLevel);
                    SkipNode<K, V> next = pre.indexMansion.get(curLevel);
                    //该删了,把指针一指就行【链表删除】
                    if (next != null && next.isKeyEqual(key)) {
                        pre.indexMansion.set(curLevel, next.indexMansion.get(curLevel));
                    }
                    //如果这层把它删完之后，就没东西了，把这层干掉，减少查询次数 --0层除外
                    if (0 != curLevel && pre == head && pre.indexMansion.get(curLevel) == null) {
                        pre.indexMansion.remove(curLevel);
                        maxLevel--;
                    }
                    curLevel--;
                }
            }
        }

        /**
         * 是否包含key
         *
         * @param key
         * @return
         */
        private boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            SkipNode<K, V> less = mostRightLessNodeInTree(key);
            //后面一个，==>最后一层索引指针指向的位置
            SkipNode<K, V> find = less.indexMansion.get(0);
            return find != null && find.isKeyEqual(key);
        }

        /**
         * 最后一个key
         *
         * @return
         */
        public K firstKey() {
            SkipNode<K, V> firstNode = head.indexMansion.get(0);
            return firstNode == null ? null : firstNode.k;
        }

        /**
         * 最大的key
         *
         * @return
         */
        public K lastKey() {
            //推到最后
            SkipNode<K, V> pre = head;
            int curLevel = maxLevel;
            while (curLevel >= 0) {
                SkipNode<K, V> nextNode = pre.indexMansion.get(curLevel);
                //推到最后
                while (nextNode != null) {
                    pre = nextNode;
                    nextNode = nextNode.indexMansion.get(curLevel);
                }
                curLevel--;
            }
            return pre == head ? null : pre.k;
        }

        /**
         * 大于 key 的最小的k
         *
         * @param key
         * @return
         */
        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            SkipNode<K, V> pre = mostRightLessNodeInTree(key);
            SkipNode<K, V> next = pre.indexMansion.get(0);
            return next == null ? null : next.k;
        }

        /**
         * 小于key的最大的k
         *
         * @param key
         * @return
         */
        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            SkipNode<K, V> pre = mostRightLessNodeInTree(key);
            SkipNode<K, V> next = pre.indexMansion.get(0);
            return next != null && next.isKeyEqual(key) ? next.k : pre.k;
        }
    }

}
