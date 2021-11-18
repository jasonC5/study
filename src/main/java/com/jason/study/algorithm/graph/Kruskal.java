package com.jason.study.algorithm.graph;

import java.util.*;

/**
 * K算法--最小生成树
 * 1）总是从权值最小的边开始考虑，依次考察权值依次变大的边
 * 2）当前的边要么进入最小生成树的集合，要么丢弃
 * 3）如果当前的边进入最小生成树的集合中不会形成环，就要当前边
 * 4）如果当前的边进入最小生成树的集合中会形成环，就不要当前边
 * 5）考察完所有边之后，最小生成树的集合也得到了
 * @author JasonC5
 */
public class Kruskal {

    static class UnionFind{
        // key 某一个节点， value key节点往上的节点
        private HashMap<Node, Node> fatherMap;
        // key 某一个集合的代表节点, value key所在集合的节点个数
        private HashMap<Node, Integer> sizeMap;

        public UnionFind() {
            this.fatherMap = new HashMap<>();
            this.sizeMap = new HashMap<>();
        }

        public void makeSets(Collection<Node<Integer>> values) {
            this.fatherMap = new HashMap<>();
            this.sizeMap = new HashMap<>();
            for (Node<Integer> value : values) {
                fatherMap.put(value, value);
                sizeMap.put(value, 1);
            }
        }

        //查看是否在一个集合中，若在则形成环，
        public boolean isSameSet(Node n1, Node n2) {
            //代表节点相同则在一个集合中
            return find(n1) == find(n2);
        }

        private Node find(Node node) {
//            Node father;
//            return (father = fatherMap.get(node)) == node ? node : find(father);
            Stack<Node> path = new Stack<>();
            Node father;
            while (node != (father = fatherMap.get(node))){
                path.add(node);
                node = father;
            }
            //压缩路径
            while (!path.isEmpty()){
                Node pop = path.pop();
                fatherMap.put(pop, father);
            }
            return father;
        }

        public void union(Node n1, Node n2) {
            Node fatherA,fatherB;
            //本身就在一个集合内，不合并
            if ((fatherA = find(n1)) == (fatherB = find(n2))) {
                return;
            }
            //小的合入大的中
            Integer size1 = sizeMap.get(fatherA);
            Integer size2 = sizeMap.get(fatherB);
            //谁大谁当爹
            Node father = size1 >= size2 ? fatherA : fatherB;
            Node son = father == fatherA ? fatherB : fatherA;
            sizeMap.put(father, size1 + size2);
            sizeMap.remove(son);
            fatherMap.put(son, father);
        }
    }

    public static Set<Edge> kruskalMST(Graph<Integer> graph) {
        UnionFind unionFind = new UnionFind();
        unionFind.makeSets(graph.nodes.values());
        // 从小的边到大的边，依次弹出，小根堆！
        PriorityQueue<Edge> minHeap = new PriorityQueue<Edge>((e1, e2)->e1.weight - e2.weight);
        for (Edge edge : graph.edges) {
            minHeap.offer(edge);
        }
        Set<Edge> result = new HashSet<>();
        while (!minHeap.isEmpty()) {
            Edge edge = minHeap.poll();
            if (!unionFind.isSameSet(edge.source, edge.target)) {
                result.add(edge);
                unionFind.union(edge.source, edge.target);
            }
        }
        return result;
    }
}
