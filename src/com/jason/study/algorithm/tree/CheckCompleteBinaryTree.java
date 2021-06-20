package com.jason.study.algorithm.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 判断二叉树是否是完全二叉树
 * <p>
 * 思路：
 * 中序遍历
 * 若出现有无左孩子，但是有右孩子，直接返回false
 * 当出现第一个左右孩子不满的情况下，标记，后面所有的节点必须都是叶子节点，否则返回false
 *
 * @author JasonC5
 */
public class CheckCompleteBinaryTree {

    public static void main(String[] args) {
        /**
         *           0
         *          1 2
         *         34 56
         */
        BinaryTreeNode root = new BinaryTreeNode(0);
        root.left = new BinaryTreeNode(1);
        root.right = new BinaryTreeNode(2);
        root.left.left = new BinaryTreeNode(3);
        root.left.right = new BinaryTreeNode(4);
        root.right.left = new BinaryTreeNode(5);
        root.right.right = new BinaryTreeNode(6);
        System.out.println(check(root));
    }

    public static boolean check(BinaryTreeNode head) {
        if (head == null) {
            return true;
        }
        Queue<BinaryTreeNode> q = new LinkedList<>();
        q.add(head);
        boolean mark = false;
        while (!q.isEmpty()) {
            BinaryTreeNode node = q.poll();
            if (node == null) {
                continue;
            }
            if (node.left == null && node.right != null) {
                //左孩子为空，右孩子不为空，不符合完全二叉树定义
                return false;
            } else if (mark && node.left != null) {
                //出现过一次左右孩子不满的情况之后，后面只能有叶子节点
                //上面框定了有右孩子必有左孩子，所以只要左孩子为空，这个节点就是叶子节点，否则就是非叶子节点
                return false;
            }
            if (node.left != null) {
                q.add(node.left);
            }
            if (node.right != null) {
                q.add(node.right);
            } else {
                //上面框定了不能有右孩子没左孩子，所以只要右孩子为空，则出现了第一个左右不满的节点
                mark = true;
            }
        }
        return true;
    }


}
