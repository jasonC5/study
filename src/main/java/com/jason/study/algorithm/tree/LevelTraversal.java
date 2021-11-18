package com.jason.study.algorithm.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 按层遍历
 *
 * @author JasonC5
 */
public class LevelTraversal {

    public static void main(String[] args) {
        /**
         *            0
         *          1  2
         *        3 4 5 6
         */
        BinaryTreeNode root = new BinaryTreeNode(0);
        root.left = new BinaryTreeNode(1);
        root.right = new BinaryTreeNode(2);
        root.left.left = new BinaryTreeNode(3);
        root.left.right = new BinaryTreeNode(4);
        root.right.left = new BinaryTreeNode(5);
        root.right.right = new BinaryTreeNode(6);
        binaryTreeTraversalByLevel(root);
    }

    public static void binaryTreeTraversalByLevel(BinaryTreeNode head) {
        Queue<BinaryTreeNode> q = new LinkedList();
        q.add(head);
        while (!q.isEmpty()) {
            BinaryTreeNode node = q.poll();
            if (node == null){
                continue;
            }
            if (node.left != null) {
                q.add(node.left);
            }
            if (node.right != null) {
                q.add(node.right);
            }
            System.out.print(node.value + " ");
        }
    }

}
