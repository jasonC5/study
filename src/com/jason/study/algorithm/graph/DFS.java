package com.jason.study.algorithm.graph;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 深度优先遍历
 *
 * @author JasonC5
 */
public class DFS {
    /**
     * 从头结点开始深度优先便利
     *
     * @param node
     */
    public static void dfs(Node<Integer> node){
        if (node == null) {
            return;
        }
        Set seen = new HashSet();
        Stack<Node<Integer>> stack = new Stack<>();
        seen.add(node);
        stack.push(node);
        System.out.println(node.value);
        while(!stack.isEmpty()){
            Node<Integer> pop = stack.pop();
            for (Node<Integer> next : pop.nexts) {
                if (!seen.contains(next)) {
                    stack.push(pop);
                    stack.push(next);
                    seen.add(next);
                    System.out.println(node.value);
                }
            }
        }
        return;
    }
}
