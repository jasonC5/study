package com.jason.study.algorithm.tree;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 把一颗多叉树，序列化成一个二叉树，再把这个二叉树，反序列化成一个多叉树
 * 思路：
 * 序列化：
 * 从头节点开始，只用左子树，右子树弃用
 * 左子树挂载第一个子节点，子节点的右子树挂载其他的子节点【都用右子节点相连】
 * 左子树的左节点挂载自己的第一个子节点
 * 以此类推
 * 反序列化：
 * 头节点还是头节点，左子节点为第一个子节点，左子节点一直调用右子节点放入List，整个是头节点的子节点列表
 * 遍历列表，查找有左子树的节点，就有子节点，没有左子节点的就没有子节点，有左子节点的重复第二步【用队列先入先出，然后根据自己的左子节点判断是否有子节点继续以上步骤】
 *
 * @author JasonC5
 */
public class LC431 {
    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    public static void main(String[] args) {

    }

    /**
     * 加密
     *
     * @param node
     * @return
     */
    public static TreeNode encode(Node node) {
        if (node == null) {
            return null;
        }
        TreeNode root = new TreeNode(node.val);
        if (node.children != null) {
            root.left = doEncode(node.children);
        }
        return root;
    }

    private static TreeNode doEncode(List<Node> children) {
        TreeNode first = null;
        TreeNode node = null;
        for (Node child : children) {
            if (first == null) {
                first = new TreeNode(child.val);
                node = first;
            } else {
                node.right = new TreeNode(child.val);
            }
            node.left = doEncode(child.children);
            node = node.right;
        }
        return first;
    }

    /**
     * 解密
     *
     * @param head
     * @return
     */
    public static Node decode(TreeNode head) {
        if (head == null) {
            return null;
        }
//        Node node = new Node(head.val);
//        if (head.left != null) {
//            node.children = doDecode(head.left);
//        }
//        return node;
        return new Node(head.val, doDecode(head));
    }

    private static List<Node> doDecode(TreeNode root) {
        if (root.left == null) {
            return null;
        }
        List<Node> children = new ArrayList<>();
        while (root != null) {
//            Node node = new Node(root.val);
//            node.children = doDecode(root);
            Node node = new Node(root.val, doDecode(root));
            children.add(node);
            root = root.right;
        }
        return children;
    }


}
