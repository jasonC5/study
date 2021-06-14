package com.jason.study.algorithm.tree;

import java.util.Stack;

/**
 * 遍历二叉树
 *
 * @author JasonC5
 */
public class BinaryTreeErgodic {

    public static final int BEFORE = 0;
    public static final int MIDDLE = 1;
    public static final int AFTER = 2;

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
        ergodicByRecursion(root, BEFORE);
        System.out.println();
        beforeErgodicByStack(root);
        System.out.println();
        System.out.println();
        ergodicByRecursion(root, MIDDLE);
        System.out.println();
        middleErgodicByStack(root);
        System.out.println();
        System.out.println();
        ergodicByRecursion(root, AFTER);
        System.out.println();
        afterErgodicByStack(root);
    }

    /**
     * 递归遍历
     *
     * @param node
     * @param type
     */
    public static void ergodicByRecursion(BinaryTreeNode node, int type) {
        if (node == null) {
            return;
        }
        if (type == BEFORE) {
            System.out.print(node.value + " ");
        }
        ergodicByRecursion(node.left, type);
        if (type == MIDDLE) {
            System.out.print(node.value + " ");
        }
        ergodicByRecursion(node.right, type);
        if (type == AFTER) {
            System.out.print(node.value + " ");
        }

    }

    //一个结论：链表先序遍历 和 后续遍历，对于一个元素X 先序遍历X前面的元素 和 后续遍历X后面的元素 的交集， 有且仅有x的

    //不用递归，栈实现

    /**
     * 先序
     */
    public static void beforeErgodicByStack(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        //先序：中左右， 栈反向：右左中，最后一个不用入栈（已入栈就要出栈）
        Stack<BinaryTreeNode> stack = new Stack<>();
//        if (node.right != null) {
//            stack.push(node.right);
//        }
//        if (node.left != null) {
//            stack.push(node.left);
//        }
//        System.out.print(node.value + " ");
        stack.push(node);
        while (!stack.isEmpty()) {
            BinaryTreeNode pop = stack.pop();
            if (pop.right != null) {
                stack.push(pop.right);
            }
            if (pop.left != null) {
                stack.push(pop.left);
            }
            System.out.print(pop.value + " ");
        }
    }

    /**
     * 中序 栈实现
     * 左中右
     */
    public static void middleErgodicByStack(BinaryTreeNode root) {
        if (root == null) {
            return;
        }
//        BinaryTreeNode node = root;
        Stack<BinaryTreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
//            BinaryTreeNode reader = root;
//            while (root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            }
//            if (!stack.isEmpty()) {
            else {
                BinaryTreeNode pop = stack.pop();
                System.out.print(pop.value + " ");
                root = pop.right;
            }
        }
    }

    /**
     * 后序遍历
     *
     * @param root
     */
    private static void afterErgodicByStack(BinaryTreeNode root) {
        //左右中
        //两个栈实现，先 中右左  放到临时栈里，然后弹出临时栈
        if (root == null) {
            return;
        }
        Stack<BinaryTreeNode> stack = new Stack<>();
        stack.push(root);
        Stack<BinaryTreeNode> tmp = new Stack<>();
        while (!stack.isEmpty()) {
            BinaryTreeNode pop = stack.pop();
            if (pop.left != null) {
                stack.push(pop.left);
            }
            if (pop.right != null) {
                stack.push(pop.right);
            }
            tmp.push(pop);
        }
        while (!tmp.isEmpty()) {
            System.out.print(tmp.pop().value + " ");
        }
    }
}
