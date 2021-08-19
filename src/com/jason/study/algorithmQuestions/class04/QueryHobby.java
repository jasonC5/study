package com.jason.study.algorithmQuestions.class04;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryHobby {
    /*
     * 今日头条原题
     *
     * 数组为{3, 2, 2, 3, 1}，查询为(0, 3, 2)。意思是在数组里下标0~3这个范围上，有几个2？返回2。
     * 假设给你一个数组arr，对这个数组的查询非常频繁，请返回所有查询的结果
     *
     */
    //暴力解
    public static class QueryBox1 {
        private int[] arr;

        public QueryBox1(int[] arr) {
            this.arr = arr;
        }

        public int query(int left, int right, int target) {
            int ans = 0;
            for (int i = left; i <= right; i++) {
                ans += arr[i] == target ? 1 : 0;
            }
            return ans;
        }
    }

    public static class QueryBox2 {
        private int[] arr;
        Map<Integer, List<Integer>> existMap;

        public QueryBox2(int[] arr) {
            this.arr = arr;
            existMap = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                List<Integer> existIndexes = existMap.getOrDefault(arr[i], new ArrayList<>());
                existIndexes.add(i);
                existMap.put(arr[i], existIndexes);
            }
        }

        public int query(int left, int right, int target) {
            if (!existMap.containsKey(target)){
                return 0;
            }
            List<Integer> indexes = existMap.get(target);
            int ans = 0;
            //这里用二分法更好，偷个懒
            for (int i = 0; i < indexes.size(); i++) {
                if (indexes.get(i) < left) {
                    continue;
                } if (indexes.get(i) > right) {
                    break;
                } else {
                    ans++;
                }
            }
            return ans;
        }
    }

    public static int[] generateRandomArray(int len, int value) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value) + 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        int len = 300;
        int value = 20;
        int testTimes = 1000;
        int queryTimes = 1000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(len, value);
            int N = arr.length;
            QueryBox1 box1 = new QueryBox1(arr);
            QueryBox2 box2 = new QueryBox2(arr);
            for (int j = 0; j < queryTimes; j++) {
                int a = (int) (Math.random() * N);
                int b = (int) (Math.random() * N);
                int L = Math.min(a, b);
                int R = Math.max(a, b);
                int v = (int) (Math.random() * value) + 1;
                if (box1.query(L, R, v) != box2.query(L, R, v)) {
                    System.out.println("Oops!");
                }
            }
        }
        System.out.println("test end");
    }

}
