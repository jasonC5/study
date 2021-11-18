package com.jason.study.algorithm.graph;

import java.util.ArrayList;

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
public class TopologicalOrderDFS2 {

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
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        //算深度，一开始深度都是0,当发现父子
        //如果x做出来的最大深度 > y的最大深度， x 在  y  之前
        
        return ans;
    }
}
