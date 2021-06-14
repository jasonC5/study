package com.jason.study.algorithm.tree;

/**
 * @author JasonC5
 */
public class BinaryTreeNode<T> {
    T value;
    BinaryTreeNode left;
    BinaryTreeNode right;

    public BinaryTreeNode() {
    }

    public BinaryTreeNode(T value) {
        this.value = value;
    }
}
