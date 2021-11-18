package com.jason.study.algorithm.tree;

/**
 * @author JasonC5
 */
public class NextNode {
    public static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int data) {
            this.value = data;
        }
    }

    public static void main(String[] args) {
        Node head = new Node(6);
        head.parent = null;
        head.left = new Node(3);
        head.left.parent = head;
        head.left.left = new Node(1);
        head.left.left.parent = head.left;
        head.left.left.right = new Node(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new Node(4);
        head.left.right.parent = head.left;
        head.left.right.right = new Node(5);
        head.left.right.right.parent = head.left.right;
        head.right = new Node(9);
        head.right.parent = head;
        head.right.left = new Node(8);
        head.right.left.parent = head.right;
        head.right.left.left = new Node(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new Node(10);
        head.right.right.parent = head.right;

        Node test = head.left.left;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head.left.left.right;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head.left;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head.left.right;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head.left.right.right;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head.right.left.left;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head.right.left;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head.right;
        System.out.println(test.value + " next: " + getNextNode(test).value);
        test = head.right.right; // 10's next is null
        System.out.println(test.value + " next: " + getNextNode(test));
    }

    /**
     * 获取中序遍历的下一个节点
     *
     * @param node
     * @return
     */
    public static Node getNextNode(Node node) {
        //思路：
        // 中序遍历：左中右，
        // 若该节点有右子节点，则返回右子节点最深的左子节点
        //      若无右子节点，则往上找，如果当前节点是父节点的左孩子，则下一个节点是父节点
        //          如果当前是父节点的右孩子，则父节点已经遍历完了，需要再往上找，直到整颗子树是祖先节点的左孩子，则该祖先节点为下一个打印的节点，
        //          若一直找到最后一个【根节点 parent == null】则当前节点没有下一个节点
        if (node == null) {
            return null;
        }
        if (node.right != null) {
            return findDeepLeft(node.right);
        } else {
            Node parent = node.parent;
            while (parent != null) {
                if (node == parent.left) {
                    return parent;
                } else {
                    node = parent;
                    parent = parent.parent;
                }
            }
            return null;
        }
    }

    private static Node findDeepLeft(Node node) {
        Node pointer = null;
        while (node != null) {
            pointer = node;
            node = node.left;
        }
        return pointer;
    }

}
