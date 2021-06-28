package com.jason.study.algorithm.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * 宽度优先遍历
 *
 * @author JasonC5
 */
public class BFS {

    /**
     * 从头节点开始宽度优先遍历
     *
     * @param node
     */
    public static void bfs(Node<Integer> node) {
        if (node == null) {
            return;
        }
        Set seen = new HashSet();
        Queue<Node<Integer>> q = new LinkedList();
        q.add(node);
        seen.add(node);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Node<Integer> poll = q.poll();
                System.out.println(poll.value);
                for (Node<Integer> next : node.nexts) {
                    if (!seen.contains(next)) {
                        q.offer(next);
                        seen.add(next);
                    }
                }
            }
        }
        return;
    }


}
