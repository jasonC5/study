package com.jason.study.algorithm.graph;

import java.util.*;

/**
 * 拓扑排序--根据入度
 * 有依赖关系的一批任务，按照可以完成的顺序输出
 *
 * @author JasonC5
 */
public class TopologySort {
    // 有向无环图
    public static List<Node> sortedTopology(Graph<Integer> graph) {
        //根据入度，入度为0的，加入ans，并把以该点为起始点的所有边的结束点的 入度-1，循环
        HashMap<Integer, Node<Integer>> nodes = graph.nodes;
        //入度哈希表
        HashMap<Node, Integer> inMap = new HashMap<>();
        //入度为0的队列
        Queue<Node> zeroInNodeQueue = new LinkedList<>();
        //统计入度
        for (Node node : graph.nodes.values()) {
            inMap.put(node, node.in);
            if (node.in == 0) {
                zeroInNodeQueue.offer(node);
            }
        }
        List<Node> ans = new ArrayList<>();
        while (!zeroInNodeQueue.isEmpty()) {
            Node poll = zeroInNodeQueue.poll();
            ans.add(poll);
            List<Node> nexts = poll.nexts;
            for (Node next : nexts) {
                //不能破坏点内部结构
                inMap.put(next, next.in - 1);
                if (inMap.get(next) == 0) {
                    zeroInNodeQueue.offer(next);
                }
            }
        }
        return ans;
    }

}
