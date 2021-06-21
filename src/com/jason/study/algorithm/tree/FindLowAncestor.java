package com.jason.study.algorithm.tree;

/**
 * 给定一颗二叉树的头节点head，和另外两个节点a和b，返回a和b的最低公共祖先
 *
 * @author JasonC5
 */
public class FindLowAncestor {

    public static void main(String[] args) {
        /**
         *           0                    10
         *          1 2                5    20
         *         34 56            3   8  15   26
         */
        BinaryTreeNode root = new BinaryTreeNode(10);
        root.left = new BinaryTreeNode(5);
        BinaryTreeNode a =  root.right = new BinaryTreeNode(20);
        root.left.left = new BinaryTreeNode(3);
        root.left.right = new BinaryTreeNode(8);
        BinaryTreeNode b = root.right.left = new BinaryTreeNode(15);
        root.right.right = new BinaryTreeNode(26);
        System.out.println(findLowAncestor(root, a, b).value);
    }

    static class Info {
        boolean findA;
        boolean findB;
        BinaryTreeNode lowAncestor;

        public Info(boolean findA, boolean findB, BinaryTreeNode lowAncestor) {
            this.findA = findA;
            this.findB = findB;
            this.lowAncestor = lowAncestor;
        }
    }

    private static BinaryTreeNode findLowAncestor(BinaryTreeNode root, BinaryTreeNode a, BinaryTreeNode b) {
        if (root == null) {
            return null;
        }
        return process(root, a, b).lowAncestor;
    }

    private static Info process(BinaryTreeNode node, BinaryTreeNode a, BinaryTreeNode b) {
        if (node == null) {
            return new Info(false, false, null);
        }
        Info leftInfo = process(node.left, a, b);
        Info rightInfo = process(node.right, a, b);
        boolean findA = leftInfo.findA || rightInfo.findA || node == a;
        boolean findB = leftInfo.findB || rightInfo.findB || node == b;
        BinaryTreeNode lowAncestor = null;
        if (findA && findB) {
            //不讲道理，左子树有最小祖先，就取左子树的，右子树有最小祖先就取右子树的，否则，一定是我自己【因为此时ab都找到了，必有最小祖先】
            if (leftInfo.lowAncestor != null) {
                lowAncestor = leftInfo.lowAncestor;
            } else if (rightInfo.lowAncestor != null) {
                lowAncestor = rightInfo.lowAncestor;
            } else {
                lowAncestor = node;
            }
        }
        return new Info(findA, findB, lowAncestor);
    }

}
