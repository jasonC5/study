package com.jason.study.algorithm.graph;

import java.util.*;

/**
 * 1）Dijkstra算法必须指定一个源点
 * 2）生成一个源点到各个点的最小距离表，一开始只有一条记录，即原点到自己的最小距离为0，源点到其他所有点的最小距离都为正无穷大
 * 3）从距离表中拿出没拿过记录里的最小记录，通过这个点发出的边，更新源点到各个点的最小距离表，不断重复这一步
 * 4）源点到所有的点记录如果都被拿过一遍，过程停止，最小距离表得到了
 *
 * @author JasonC5
 */
public class Dijkstra {
    public static HashMap<Node<Integer>, Integer> dijkstra1(Node<Integer> from) {
        HashMap<Node<Integer>, Integer> ans = new HashMap<>();
        ans.put(from, 0);
        Set<Node<Integer>> seen = new HashSet<Node<Integer>>();
        Node<Integer> minNode = getMinDistanceAndUnselectedNode(ans, seen);
        while (minNode != null) {
            //这个点到from的距离
            int distance = ans.get(minNode);
            for (Edge edge : minNode.edges) {
                int targetDistance = distance + edge.weight;
                //如果连接点未扫过，直接放入ans
                if (!ans.containsKey(edge.target)) {
                    ans.put(edge.target, targetDistance);
                } else {
                    //对比已经存在的点,若比当前小，则替换
                    ans.put(edge.target, Math.min(ans.get(edge.target), targetDistance));
                }
            }
            seen.add(minNode);
            //继续查找下一个点
            getMinDistanceAndUnselectedNode(ans, seen);
        }
        //从已经解锁的点中，找出离from最近的
        return ans;
    }

    private static Node<Integer> getMinDistanceAndUnselectedNode(HashMap<Node<Integer>, Integer> ans, Set<Node<Integer>> seen) {
        Node minNode = null;
        Integer minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Node<Integer>, Integer> entry : ans.entrySet()) {
            Node<Integer> node = entry.getKey();
            Integer distance = entry.getValue();
            if (seen.contains(node)) {
                continue;
            }
            if (distance > minDistance) {
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }

}
