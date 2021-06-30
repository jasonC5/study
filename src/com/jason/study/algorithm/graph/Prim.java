package com.jason.study.algorithm.graph;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * P算法--最小生成树
 * 1）可以从任意节点出发来寻找最小生成树
 * 2）某个点加入到被选取的点中后，解锁这个点出发的所有新的边
 * 3）在所有解锁的边中选最小的边，然后看看这个边会不会形成环
 * 4）如果会，不要当前边，继续考察剩下解锁的边中最小的边，重复3）
 * 5）如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2）
 * 6）当所有点都被选取，最小生成树就得到了
 *
 * @author JasonC5
 */
public class Prim {

    public static Set<Edge> prim(Graph<Integer> graph) {
        //解锁的点
        HashSet<Node> nodeSet = new HashSet<>();
        //解锁的边
        Set<Edge> result = new HashSet<>(); // 依次挑选的的边在result里
        //小根堆放边，每次弹出一条最小边
        PriorityQueue<Edge> minHeap = new PriorityQueue<Edge>((e1, e2) -> e1.weight - e2.weight);
        for (Node<Integer> node : graph.nodes.values()) { // 随便挑了一个点
            //随便找一个点开始
            if (nodeSet.contains(node)) {
                //已经解锁过该节点了，不处理
                continue;
            }
            nodeSet.add(node);
            //边放入小跟堆
            for (Edge edge : node.edges) {
                minHeap.add(edge);
            }
            while (!minHeap.isEmpty()) {
                Edge edge = minHeap.poll();
                Node<Integer> target = edge.target;
                if (!nodeSet.contains(target)){
                    result.add(edge);
                    nodeSet.add(target);
                    if (nodeSet.size() == graph.nodes.size()) {
                        //所有点都解锁了，最小生成树达成
                        return result;
                    }
                    for (Edge next : target.edges) {
                        minHeap.add(next);
                    }
                }
            }
            //若可保证无森林，则直接break即可
//            break;
        }
        return result;
    }

    // 请保证graph是连通图
    // graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
    // 返回值是最小连通图的路径之和
    public static int prim(int[][] graph) {

        return 0;
    }
}
