package com.jason.study.algorithm.tree;

/**
 * @author JasonC5
 */
public class BinaryTreeNode<T> {
    public T value;
    public BinaryTreeNode left;
    public BinaryTreeNode right;

    public BinaryTreeNode() {
    }

    public BinaryTreeNode(T value) {
        this.value = value;
    }
}
