package com.jason.study.algorithm.tree;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树序列化、反序列化
 *
 * @author JasonC5
 */
public class BinaryTreeSerialize {
    public static void main(String[] args) {
        /**
         *            0
         *          1  2
         *          4   6
         */
        BinaryTreeNode root = new BinaryTreeNode(0);
        root.left = new BinaryTreeNode(1);
        root.right = new BinaryTreeNode(2);
//        root.left.left = new BinaryTreeNode(3);
        root.left.right = new BinaryTreeNode(4);
//        root.right.left = new BinaryTreeNode(5);
        root.right.right = new BinaryTreeNode(6);
        LevelTraversal.binaryTreeTraversalByLevel(root);
        System.out.println();
        Queue queue = new LinkedList<>();
        serializeBinaryTree(root, queue);
//        printQueue(queue);
        System.out.println();
        BinaryTreeNode newRoot = deserialize(queue);
        LevelTraversal.binaryTreeTraversalByLevel(newRoot);

    }

    private static BinaryTreeNode deserialize(Queue queue) {
        Object poll;
        if (queue == null || queue.isEmpty() || (poll = queue.poll()) == null) {
            return null;
        }
        BinaryTreeNode root = new BinaryTreeNode(poll);
        root.left = deserialize(queue);
        root.right = deserialize(queue);
        return root;
    }

    private static void printQueue(Queue queue) {
        while (!queue.isEmpty()) {
            Object poll = queue.poll();
            if (poll == null) {
                System.out.print("# ");
            } else {
                System.out.print(poll + " ");
            }
        }
    }

    //按照先序遍历的方式序列化
    public static void serializeBinaryTree(BinaryTreeNode root, Queue queue) {
        if (root == null) {
            queue.add(null);
        } else {
            queue.add(root.value);
            serializeBinaryTree(root.left, queue);
            serializeBinaryTree(root.right, queue);
        }

    }

}
