package com.jason.study.algorithmQuestions.class36;

import java.util.*;

/**
 * // 来自三七互娱
 * // Leetcode原题 : https://leetcode.com/problems/bus-routes/
 *
 * @author JasonC5
 */
public class Code12_BusRoutes {
    public static void main(String[] args) {
//        int[][] routes = {{7, 12}, {4, 5, 15}, {6}, {15, 19}, {9, 12, 13}};
//        int source = 15;
//        int target = 12;
        int[][] routes = {{1, 2, 7}, {3, 6, 7}};
        int source = 1;
        int target = 6;
        System.out.println(numBusesToDestination(routes, source, target));
    }

    public static int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target){
            return 0;
        }
        int length = routes.length;
        //整理出来每个车站能坐的车
        Map<Integer, Set<Integer>> stationBusMap = new HashMap<>();
        //每辆车经过哪些车站
        Map<Integer, Set<Integer>> busMap = new HashMap<>();
        for (int i = 0; i < length; i++) {
            Set<Integer> set = new HashSet<>();
            for (int station : routes[i]) {
                Set<Integer> list = stationBusMap.getOrDefault(station, new HashSet<>());
                list.add(i);
                stationBusMap.put(station, list);
                set.add(station);
            }
            busMap.put(i, set);
        }
        //整理出来每辆车能换乘那辆车
        Map<Integer, Set<Integer>> transferMap = new HashMap<>();
        for (int i = 0; i < length; i++) {
            Set<Integer> transferSet = new HashSet<>();
            for (int station : routes[i]) {
                Set<Integer> buses = stationBusMap.get(station);
                transferSet.addAll(buses);
            }
            transferMap.put(i, transferSet);
        }
        //宽度优先遍历
        int step = 0;
        Set<Integer> buses = stationBusMap.get(source);
        boolean[] visited = new boolean[busMap.size()];
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.addAll(buses);
        Set<Integer> targetBuses = stationBusMap.get(target);
        if (targetBuses == null || targetBuses.size() == 0){
            return -1;
        }
        while (!queue.isEmpty()) {
            step++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Integer bus = queue.poll();
                visited[bus] = true;
                if (targetBuses.contains(bus)) {
                    return step;
                } else {
                    Set<Integer> next = transferMap.get(bus);
                    for (Integer nextBus : next) {
                        if (!visited[nextBus]) {
                            queue.add(nextBus);
                        }
                    }
                }
            }
        }
        return -1;
    }
}
