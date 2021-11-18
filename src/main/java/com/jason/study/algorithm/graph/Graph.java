package com.jason.study.algorithm.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 结构化图数据
 *
 * @author JasonC5
 */
public class Graph<T> {

    //所有的点
    public HashMap<T, Node<T>> nodes;
    //所有的边
    public HashSet<Edge> edges;

    public Graph(HashMap<T, Node<T>> nodes, HashSet<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public Graph() {
    }
}

//点
class Node<T> {
    public T value;
    //入度
    public int in;
    //出度
    public int out;
    //相邻点（子节点）
    public List<Node<T>> nexts;
    //子节点边
    public List<Edge> edges;

    public Node(T value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<Node<T>>();
        edges = new ArrayList<>();
    }
}

//边
class Edge {
    //权重
    public int weight;
    //源节点
    public Node source;
    //目标节点
    public Node target;

    public Edge(int weight, Node source, Node target) {
        this.weight = weight;
        this.source = source;
        this.target = target;
    }
}


