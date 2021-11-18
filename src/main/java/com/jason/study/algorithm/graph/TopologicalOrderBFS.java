package com.jason.study.algorithm.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * // OJ链接：https://www.lintcode.com/problem/topological-sorting
 *描述
 * 给定一个有向图，图节点的拓扑排序定义如下:
 *
 * 对于图中的每一条有向边 A -> B , 在拓扑排序中A一定在B之前.
 * 拓扑排序中的第一个节点可以是图中的任何一个没有其他节点指向它的节点.
 * 针对给定的有向图找到任意一种拓扑排序的顺序.
 *
 * 你可以假设图中至少存在一种拓扑排序
 * 有关图的表示详情请看这里
 *
 * 图结点的个数 <= 5000
 * 样例
 * 样例 1：
 *
 * 输入：
 *
 * graph = {0,1,2,3#1,4#2,4,5#3,4,5#4#5}
 * 输出：
 *
 * [0, 1, 2, 3, 4, 5]
 * 解释：
 *
 * 图如下所示:
 *
 * picture
 *
 * 拓扑排序可以为:
 * [0, 1, 2, 3, 4, 5]
 * [0, 2, 3, 1, 5, 4]
 * ...
 * 您只需要返回给定图的任何一种拓扑顺序。
 * @author JasonC5
 */
public class TopologicalOrderBFS {

    // 题目给的数据结构【有向无环图】
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        //入度 入度为0的时候，
        HashMap<DirectedGraphNode, Integer> indegreeMap = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            indegreeMap.put(cur, 0);
        }
        for (DirectedGraphNode cur : graph) {
            //每到一个点，入度+1
            for (DirectedGraphNode next : cur.neighbors) {
                indegreeMap.put(next, indegreeMap.get(next) + 1);
            }
        }
        //入度为0的点,塞到队列中，下面的点入度-1
        Queue<DirectedGraphNode> q = new LinkedList<>();
        for (DirectedGraphNode cur : indegreeMap.keySet()) {
            if (indegreeMap.get(cur) == 0) {
                q.add(cur);
            }
        }
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        while (!q.isEmpty()) {
            DirectedGraphNode poll = q.poll();
            ans.add(poll);
            for (DirectedGraphNode neighbor : poll.neighbors) {
                indegreeMap.put(neighbor, indegreeMap.get(neighbor) -1);
                if (indegreeMap.get(neighbor) == 0) {
                    q.offer(neighbor);
                }
            }
        }
        return ans;
    }
}
