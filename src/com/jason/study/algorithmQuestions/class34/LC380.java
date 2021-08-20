package com.jason.study.algorithmQuestions.class34;

import java.util.HashMap;
import java.util.Map;

public class LC380 {

    public static void main(String[] args) {
        // 初始化一个空的集合。
        RandomizedSet randomSet = new RandomizedSet();

// 向集合中插入 1 。返回 true 表示 1 被成功地插入。
        randomSet.insert(1);

// 返回 false ，表示集合中不存在 2 。
        randomSet.remove(2);

// 向集合中插入 2 。返回 true 。集合现在包含 [1,2] 。
        randomSet.insert(2);

// getRandom 应随机返回 1 或 2 。
        randomSet.getRandom();

// 从集合中移除 1 ，返回 true 。集合现在包含 [2] 。
        randomSet.remove(1);

// 2 已在集合中，所以返回 false 。
        randomSet.insert(2);

// 由于 2 是集合中唯一的数字，getRandom 总是返回 2 。
        randomSet.getRandom();
    }

    static class RandomizedSet {
        private Map<Integer, Integer> index;
        private Map<Integer, Integer> revIndex;
        private int size;

        /**
         * Initialize your data structure here.
         */
        public RandomizedSet() {
            index = new HashMap();
            revIndex = new HashMap();
            size = 0;
        }

        /**
         * Inserts a value to the set. Returns true if the set did not already contain the specified element.
         */
        public boolean insert(int val) {
            if (index.containsKey(val)) {
                return false;
            }
            index.put(val, size);
            revIndex.put(size, val);
            size++;
            return true;
        }

        /**
         * Removes a value from the set. Returns true if the set contained the specified element.
         */
        public boolean remove(int val) {
            if (!index.containsKey(val)) {
                return false;
            }
            Integer idx = index.get(val);
            Integer cellVal = revIndex.get(size - 1);
            index.put(cellVal, idx);
            index.remove(val);
            revIndex.put(idx, cellVal);
            revIndex.remove(--size);
            return true;
        }

        /**
         * Get a random element from the set.
         */
        public int getRandom() {
            if (size == 0) {
                return -1;
            }
            int random = (int) (Math.random() * (size));
            Integer val = revIndex.get(random);
//            remove(val);
            return val;
        }
    }

}
