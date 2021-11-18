package com.jason.study.algorithm.graph;

import java.util.*;

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
public class TopologicalOrderDFS {

    // 题目给的数据结构【有向无环图】
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    public static class HelpNode{
        public DirectedGraphNode node;
        public long nodes;

        public HelpNode(DirectedGraphNode node, long nodes) {
            this.node = node;
            this.nodes = nodes;
        }
    }

    static class DirectedGraphNodeCompare implements Comparator<HelpNode> {
        @Override
        public int compare(HelpNode o1, HelpNode o2) {
            return o1.nodes == o2.nodes ? 0 : (o1.nodes > o2.nodes ? -1 : 1);
        }
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {

        //遍历自己下面所有的点，包括自己（子树大小），获得的点数，所有的节点按照点数从小到大排序，就是一种拓扑序
        //记忆化搜索
        Map<DirectedGraphNode, HelpNode> helpMap = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            //计算所有点的点次
            func(cur, helpMap);
        }
        ArrayList<HelpNode> recordArr = new ArrayList<>();
        //得到Node->点次 哈希表，按点次排序
        for (HelpNode r : helpMap.values()) {
            recordArr.add(r);
        }
        //按点次从小到大排序
        recordArr.sort(new DirectedGraphNodeCompare());
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (HelpNode helpNode : recordArr) {
            ans.add(helpNode.node);
        }
        return ans;
    }

    private static HelpNode func(DirectedGraphNode cur, Map<DirectedGraphNode, HelpNode> helpMap) {
        if (helpMap.containsKey(cur)) {
            return helpMap.get(cur);
        }
        long nodes = 1;
        if (cur.neighbors != null) {
            for (DirectedGraphNode neighbor : cur.neighbors) {
                nodes += func(neighbor, helpMap).nodes;
            }
        }
        HelpNode helpNode = new HelpNode(cur, nodes);
        helpMap.put(cur, helpNode);
        return helpNode;
    }
}
