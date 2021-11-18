package com.jason.study.algorithmQuestions.class02;

import java.util.HashMap;

/**
 * 设计有setAll功能的哈希表
 * put、get、setAll方法，时间复杂度O(1)
 * --时间变量
 *
 * @author JasonC5
 */
public class SetAll {
    public static class Value<V> {
        public V value;
        public long time;

        public Value(V value, long time) {
            this.value = value;
            this.time = time;
        }
    }

    public static class MyHashMap<K, V> {
        public HashMap<K, Value> map;
        public long nextTime;
        private Value<V> setAll;

        public MyHashMap() {
            this.map = new HashMap<>();
            this.nextTime = 0;
            this.setAll = new Value<V>(null, -1);
        }

        public void put(K key, V value) {
            map.put(key, new Value<V>(value, nextTime++));
        }

        public V get(K key) {
            Value<V> value = map.get(key);
            return value == null ? null : (value.time > setAll.time ? value.value : setAll.value);
        }

        public void putAll(V value) {
            setAll = new Value<V>(value, nextTime++);
        }

    }

}
