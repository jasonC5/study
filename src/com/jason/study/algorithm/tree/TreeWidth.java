package com.jason.study.algorithm.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 查找二叉树的宽度
 *
 * @author JasonC5
 */
public class TreeWidth {

    public static void main(String[] args) {
        /**
         *          如下宽度为2
         *            0
         *          1  2
         *          4   6
         */
        BinaryTreeNode root = new BinaryTreeNode(0);
        root.left = new BinaryTreeNode(1);
        root.right = new BinaryTreeNode(2);
        root.left.left = new BinaryTreeNode(3);
        root.left.right = new BinaryTreeNode(4);
        root.right.left = new BinaryTreeNode(5);
        root.right.right = new BinaryTreeNode(6);
        int width = getTreeWidth(root);
        System.out.println(width);
    }

    private static int getTreeWidth(BinaryTreeNode root) {
        //整体思路：在按层遍历的同时，记录一下层数信息；
        Queue<BinaryTreeNode> q = new LinkedList();
        q.add(root);
        //需要记录两个信息，本层结束节点和下一层结束节点：判断下一层什么时候结束
        BinaryTreeNode cruEnd = root;
        BinaryTreeNode nextEnd = null;
        int width = 0;
        int levelWidth = 0;
        while (!q.isEmpty()) {
            BinaryTreeNode node = q.poll();
            if (node == null) {
                continue;
            }
            if (node.left != null) {
                q.add(node.left);
                nextEnd = node.left;
                levelWidth++;
            }
            if (node.right != null) {
                q.add(node.right);
                nextEnd = node.right;
                levelWidth++;
            }
            if (cruEnd == node) {
                cruEnd = nextEnd;
                nextEnd = null;
                width = Math.max(width, levelWidth);
                levelWidth = 0;
            }
        }
        return width;
    }


}
