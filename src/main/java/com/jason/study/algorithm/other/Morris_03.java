package com.jason.study.algorithm.other;

/**
 * 给定一颗二叉树的头结点head，求以head为头的树中，最小深度是多少
 * --1.二叉树的递归套路
 * --2.Morris遍历
 *
 * @author JasonC5
 */
public class Morris_03 {
    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node(int x) {
            val = x;
        }
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int treeLevel = 7;
        int nodeMaxValue = 5;
        int testTimes = 100000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(treeLevel, nodeMaxValue);
            int ans1 = minHeight1(head);
            int ans2 = minHeight2(head);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

//        Node head = new Node(1);
//        head.right = new Node(2);
//        head.right.left = new Node(4);
//        int ans2 = minHeight2(head);
//        System.out.println(ans2);
    }

    /**
     * 莫里斯遍历
     *
     * @param head
     * @return
     */
    private static int minHeight2(Node head) {
        if (head == null) {
            return 0;
        }
        int curLevel = 0;
        int minHeight = Integer.MAX_VALUE;
        //判断是叶子节点的时候，记录高度
        //第二次到达节点的时候，能知道跳过来的那个，就是叶子节点
        //本层高度+链表长度，就是叶子节点高度
        Node cur = head;
        while (cur != null) {
            if (cur.left == null) {
                //向右移动
                cur = cur.right;
                curLevel++;
            } else {
                //有左孩子，
                //找到左孩子的最右节点，指向自己
                Node rightest = findRightest(cur, cur.left);
                if (rightest.right == cur) {
                    //这个rightest可能是叶子节点
                    if (rightest.left == null) {
                        //此时上个节点的level就是叶子节点的level
                        minHeight = Math.min(minHeight, curLevel);
                    }
                    //重算level = 上个节点的level - 链表
                    int rightBoardSize = rightBoardSize(cur, cur.left);
                    curLevel -= rightBoardSize;
                    //第二次来到该节点了，说明左边遍历完了，把左孩子的最右节点的连接标空，并往右边跳了
                    rightest.right = null;
                    cur = cur.right;
                } else {
                    //第一次到
                    rightest.right = cur;
                    //向左边移动
                    cur = cur.left;
                    curLevel++;
                }
            }
        }
        //完事之后，找下最右的边的高度【Morris后序遍历】
        int finalRight = 1;
        cur = head;
        while (cur.right != null) {
            finalRight++;
            cur = cur.right;
        }
        //最右的边底部，不一定是叶子节点
        if (cur.left == null && cur.right == null) {
            minHeight = Math.min(minHeight, finalRight);
        }
        return minHeight;
    }

    private static Node findRightest(Node cur, Node left) {
        Node reader = left;
        while (reader.right != null && reader.right != cur) {
            reader = reader.right;
        }
        return reader;
    }

    private static int rightBoardSize(Node cur, Node left) {
        Node reader = left;
        int rightBoardSize = 1;
        while (reader.right != null && reader.right != cur) {
            reader = reader.right;
            rightBoardSize++;
        }
        return rightBoardSize;
    }

    /**
     * 二叉树的递归套路
     *
     * @param head
     * @return
     */
    private static int minHeight1(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head, 1);
    }

    private static int process(Node node, int level) {
        if (node == null) {
            return Integer.MAX_VALUE;
        } else if (node.left == null && node.right == null) {
            return level;
        }
        return Math.min(process(node.left, level + 1), process(node.right, level + 1));
    }
//    public static int minHeight1(Node head) {
//        if (head == null) {
//            return 0;
//        }
//        return p(head);
//    }
//
//    // 返回x为头的树，最小深度是多少
//    public static int p(Node x) {
//        if (x.left == null && x.right == null) {
//            return 1;
//        }
//        // 左右子树起码有一个不为空
//        int leftH = Integer.MAX_VALUE;
//        if (x.left != null) {
//            leftH = p(x.left);
//        }
//        int rightH = Integer.MAX_VALUE;
//        if (x.right != null) {
//            rightH = p(x.right);
//        }
//        return 1 + Math.min(leftH, rightH);
//    }
}
