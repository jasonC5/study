package com.jason.study.algorithmQuestions.class05;

/**
 * @author JasonC5
 */
public class LeftRightSameTreeNumber {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static class Info {
        public int ans;
        public String str;

        public Info(int a, String s) {
            ans = a;
            str = s;
        }
    }

    public static int sameNumber(Node head) {
        if (head == null) {
            return 0;
        }
        return sameNumber(head.left) + sameNumber(head.right) + (isSame(head.left, head.right) ? 1 : 0);
    }

    private static boolean isSame(Node left, Node right) {
        if (left == null ^ right == null) {
            return false;
        }
        if (left == null && right == null) {
            return true;
        }
        return toString(left).equals(toString(right));
    }

    private static String toString(Node node) {
        if (node == null) {
            return "#,";
        }
        return node.value + "," + toString(node.left) + toString(node.right);
    }

    public static int sameNumber2(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).ans;
    }

    private static Info process(Node node) {
        if (node == null) {
            return new Info(0, "#,");
        }
        Info left = process(node.left);
        Info right = process(node.right);
        //用hash，O(n)，不用hash直接比较字符串：O(n * logn)
        return new Info(left.ans + right.ans + (left.str.equals(right.str) ? 1 : 0), getHash(node.value + "," + left.str + right.str));
    }

    private static String getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
// 如果算出来的值为负数则取其绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash + "";
    }


    public static void main(String[] args) {
        int maxLevel = 8;
        int maxValue = 4;
        int testTime = 100000;
        System.out.println("start");
        for (int i = 0; i < testTime; i++) {
            Node head = randomBinaryTree(maxLevel, maxValue);
            int ans1 = sameNumber1(head);
            int ans2 = sameNumber2(head);
            if (ans1 != ans2) {
                System.out.println("error！");
                System.out.println(ans1);
                System.out.println(ans2);
            }
        }
        System.out.println("end");

    }

    // 时间复杂度O(N * logN)
    public static int sameNumber1(Node head) {
        if (head == null) {
            return 0;
        }
        return sameNumber1(head.left) + sameNumber1(head.right) + (same(head.left, head.right) ? 1 : 0);
    }

    public static boolean same(Node h1, Node h2) {
        if (h1 == null ^ h2 == null) {
            return false;
        }
        if (h1 == null && h2 == null) {
            return true;
        }
        // 两个都不为空
        return h1.value == h2.value && same(h1.left, h2.left) && same(h1.right, h2.right);
    }


    public static Node randomBinaryTree(int restLevel, int maxValue) {
        if (restLevel == 0) {
            return null;
        }
        Node head = Math.random() < 0.2 ? null : new Node((int) (Math.random() * maxValue));
        if (head != null) {
            head.left = randomBinaryTree(restLevel - 1, maxValue);
            head.right = randomBinaryTree(restLevel - 1, maxValue);
        }
        return head;
    }

}
