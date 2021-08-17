package com.jason.study.algorithmQuestions.class03;

import java.util.*;

/**
 * 给定三个参数：
 * 二叉树的头节点head，树上某个节点target，正数K
 * 从target开始，可以向上走或者向下走
 * 返回与target的距离是K的所有节点
 * --父节点map
 * --set
 * --宽度优先遍历--按层
 *
 * @author JasonC5
 */
public class DistanceKNodes {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void main(String[] args) {
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);

        n3.left = n5;
        n3.right = n1;
        n5.left = n6;
        n5.right = n2;
        n1.left = n0;
        n1.right = n8;
        n2.left = n7;
        n2.right = n4;

        Node root = n3;
        Node target = n5;
        int K = 2;

        List<Node> ans = distanceKNodes(root, target, K);
        for (Node o1 : ans) {
            System.out.println(o1.value);
        }

    }

    /**
     * 从target开始，可以向上走或者向下走 返回与target的距离是K的所有节点
     * 宽度优先遍历【按层】
     *
     * @param root
     * @param target
     * @param k
     * @return
     */
    private static List<Node> distanceKNodes(Node root, Node target, int k) {
        Map<Node, Node> parentMap = new HashMap<>();
        Set<Node> seen = new HashSet<>();
        //先整理parentMap==>深度优先遍历
        handleParentMap(root, parentMap);
        Queue<Node> queue = new LinkedList<>();
        queue.offer(target);
        seen.add(target);
        List<Node> ans = new ArrayList<>();
        int curLevel = 0;
        //宽度有限遍历
        while (!queue.isEmpty()) {
            int curLevelSize = queue.size();
            //这一层有这么多元素，只弹这么多出来
            for (int i = 0; i < curLevelSize; i++) {
                Node node = queue.poll();
                if (curLevel == k) {
                    ans.add(node);
                }
                if (node.left != null && !seen.contains(node.left)) {
                    queue.offer(node.left);
                    seen.add(node.left);
                }
                if (node.right != null && !seen.contains(node.right)) {
                    queue.offer(node.right);
                    seen.add(node.right);
                }
                Node parent;
                if ((parent = parentMap.get(node)) != null && !seen.contains(parent)) {
                    queue.offer(parent);
                    seen.add(parent);
                }
            }
            if (curLevel++ == k) {
                return ans;
            }
        }
        return ans;
    }

    private static void handleParentMap(Node node, Map<Node, Node> parentMap) {
        if (node.left != null) {
            parentMap.put(node.left, node);
            handleParentMap(node.left, parentMap);
        }
        if (node.right != null) {
            parentMap.put(node.right, node);
            handleParentMap(node.right, parentMap);
        }
    }
}
