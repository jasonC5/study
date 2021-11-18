package com.jason.study.algorithm.other;

/**
 * 给定两棵二叉树的头节点head1和head2
 * 想知道head1中是否有某个子树的结构和head2完全一样
 * 序列化成字符串，判断子串
 *
 * @author JasonC5
 */
public class KMP_03 {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
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

    //暴力方法
    public static boolean containsTree1(Node big, Node small) {
        if (small == null) {
            return true;
        }
        if (big == null) {
            return false;
        }
        if (isSameValueStructure(big, small)) {
            return true;
        }
        return containsTree1(big.left, small) || containsTree1(big.right, small);
    }

    public static boolean isSameValueStructure(Node head1, Node head2) {
        if (head1 == null && head2 != null) {
            return false;
        }
        if (head1 != null && head2 == null) {
            return false;
        }
        if (head1 == null && head2 == null) {
            return true;
        }
        if (head1.value != head2.value) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left)
                && isSameValueStructure(head1.right, head2.right);
    }

    public static void main(String[] args) {
        int bigTreeLevel = 7;
        int smallTreeLevel = 4;
        int nodeMaxValue = 5;
        int testTimes = 100000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node big = generateRandomBST(bigTreeLevel, nodeMaxValue);
            Node small = generateRandomBST(smallTreeLevel, nodeMaxValue);
            boolean ans1 = containsTree1(big, small);
            boolean ans2 = containsTree2(big, small);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }

    private static boolean containsTree2(Node big, Node small) {
        String bigStr = tree2String(big);
        String smallStr = tree2String(small);
        return kmp(bigStr, smallStr) != -1;
    }

    private static int kmp(String bigStr, String smallStr) {
        if (bigStr.length() < smallStr.length()) {
            return -1;
        }
        char[] str1 = bigStr.toCharArray();
        char[] str2 = smallStr.toCharArray();
        int[] next = getNextArr(str2);
        int point1 = 0, point2 = 0;
        while (point1 < str1.length && point2 < str2.length) {
            if (str1[point1] == str2[point2]) {
                point1++;
                point2++;
            } else if (point2 == 0) {
                point1++;
            } else {
                point2 = next[point2];
            }
        }
        return point2 == str2.length ? point1 - point2 : -1;
    }

    private static int[] getNextArr(char[] str) {
        if (str.length < 2) {
            return new int[]{-1};
        }
        int[] next = new int[str.length];
        next[0] = -1;
        next[1] = 0;
        int cn = 0, i = 2;
        while (i < str.length) {
            if (str[cn] == str[i - 1]) {
                next[i++] = ++cn;
            } else if(cn == 0){
                next[i++] = 0;
            } else {
                cn = next[cn];
            }
        }
        return next;
    }

    private static String tree2String(Node head) {
        StringBuilder stringBuilder = new StringBuilder();
        process(head, stringBuilder);
        return stringBuilder.toString();
    }

    private static void process(Node node, StringBuilder stringBuilder) {
        if (node == null) {
            stringBuilder.append("#");
            return;
        } else {
            stringBuilder.append(node.value);
            stringBuilder.append("l");
            process(node.left, stringBuilder);
            stringBuilder.append("r");
            process(node.right, stringBuilder);
        }
    }
}
