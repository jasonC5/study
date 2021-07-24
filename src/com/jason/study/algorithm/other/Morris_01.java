package com.jason.study.algorithm.other;

/**
 * 莫里斯遍历
 *
 * @author JasonC5
 */
public class Morris_01 {

    public static class Node {
        public int value;
        Node left;
        Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    public static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    // for test -- print tree
    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(4);
        head.left = new Node(2);
        head.right = new Node(6);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.right.left = new Node(5);
        head.right.right = new Node(7);
        printTree(head);
        morrisPre(head);
        morrisIn(head);
        morrisPost(head);
    }

    private static void morrisPre(Node head) {
        Node cur = head;
        while (cur != null) {
            if (cur.left == null) {
                System.out.print(cur.value + " ");
                //向右移动
                cur = cur.right;
            } else {
                //有左孩子，
                //找到左孩子的最右节点，指向自己
                Node rightest = findRightest(cur, cur.left);
                if (rightest.right == cur) {
                    //第二次来到该节点了，说明左边遍历完了，把左孩子的最右节点的连接标空，并往右边跳了
                    rightest.right = null;
                    cur = cur.right;
                } else {
                    System.out.print(cur.value + " ");
                    rightest.right = cur;
                    //向左边移动
                    cur = cur.left;
                }
            }
        }
        System.out.println();
    }

    private static Node findRightest(Node node, Node left) {
        Node point = left;
        while (point.right != null && point.right != node) {
            point = point.right;
        }
        return point;
    }

    /**
     * 中序遍历，只到一次的直接打印，到两次的，打印第二次
     * @param head
     */
    private static void morrisIn(Node head) {
        Node cur = head;
        while (cur != null) {
            if (cur.left == null) {
                System.out.print(cur.value + " ");
                //向右移动
                cur = cur.right;
            } else {
                //有左孩子，
                //找到左孩子的最右节点，指向自己
                Node rightest = findRightest(cur, cur.left);
                if (rightest.right == cur) {
                    //第二次来到该节点了，说明左边遍历完了，把左孩子的最右节点的连接标空，并往右边跳了
                    System.out.print(cur.value + " ");
                    rightest.right = null;
                    cur = cur.right;
                } else {
                    rightest.right = cur;
                    //向左边移动
                    cur = cur.left;
                }
            }
        }
        System.out.println();
    }

    /**
     * 后续遍历
     * 第二次到达节点的时候，逆序打印左孩子的右边，最后逆序打印整颗数的右边
     * @param head
     */
    private static void morrisPost(Node head) {
        Node cur = head;
        while (cur != null) {
            if (cur.left == null) {
                //向右移动
                cur = cur.right;
            } else {
                //有左孩子，
                //找到左孩子的最右节点，指向自己
                Node rightest = findRightest(cur, cur.left);
                if (rightest.right == cur) {
                    //第二次来到该节点了，说明左边遍历完了，把左孩子的最右节点的连接标空，并往右边跳了
                    rightest.right = null;
                    //第二次到达，先把左孩子的最右节点置为空，再逆序打印左孩子的右边
                    reversePrintRight(cur.left);
                    cur = cur.right;
                } else {
                    rightest.right = cur;
                    //向左边移动
                    cur = cur.left;
                }
            }
        }
        //为什么要这么打印？因为到不了第三次，模拟第三次
        //最后还要逆序打印头结点的右边逆序
        reversePrintRight(head);
        System.out.println();
    }

    private static void reversePrintRight(Node head) {
        //1.往下找到叶子节点，并把right指针反转,得到底部的指针
        Node tail = reverseEdge(head);
        //2.打印
        Node read = tail;
        while(read != null){
            System.out.print(read.value + " ");
            read = read.right;
        }
        //3.再次反转指针反转回来
        reverseEdge(tail);
    }

    private static Node reverseEdge(Node head) {
        Node cur = head;
        Node pre = null;
        while (cur != null) {
            Node next = cur.right;
            cur.right = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }


}
