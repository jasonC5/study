package com.jason.study.algorithm.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 有一颗多叉树代表公司的层级结构，每个员工有一个自己的快乐值happy，选择节点参加聚会，选节点的时候不能选直接上下级， 返回能邀请到的情况中最大happy值
 *
 * @author JasonC5
 */
public class MaxHappy {
    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
            children = new ArrayList<>();
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    static class Info {
        int goHappy;
        int notGoHappy;

        public Info(int goHappy, int notGoHappy) {
            this.goHappy = goHappy;
            this.notGoHappy = notGoHappy;
        }
    }

    public static int findMaxHappy(Node boss) {
        Info info = process(boss);
        return info.goHappy > info.notGoHappy ? info.goHappy : info.notGoHappy;
    }

    private static Info process(Node node) {
        if (node == null) {
            return new Info(0, 0);
        }
        int goHappy = node.val;
        int notGoHappy = 0;
        if (node.children != null) {
            for (Node child : node.children) {
                Info childInfo = process(child);
                goHappy += childInfo.notGoHappy;
                notGoHappy += Math.max(childInfo.goHappy, childInfo.notGoHappy);
            }
        }
        return new Info(goHappy, notGoHappy);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxNexts = 7;
        int maxHappy = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Node boss = genarateBoss(maxLevel, maxNexts, maxHappy);
            if (findMaxHappy(boss) != findMaxHappy2(boss)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
    // for test
    public static Node genarateBoss(int maxLevel, int maxNexts, int maxHappy) {
        if (Math.random() < 0.02) {
            return null;
        }
        Node boss = new Node((int) (Math.random() * (maxHappy + 1)));
        genarateNexts(boss, 1, maxLevel, maxNexts, maxHappy);
        return boss;
    }

    // for test
    public static void genarateNexts(Node e, int level, int maxLevel, int maxNexts, int maxHappy) {
        if (level > maxLevel) {
            return;
        }
        int nextsSize = (int) (Math.random() * (maxNexts + 1));
        for (int i = 0; i < nextsSize; i++) {
            Node next = new Node((int) (Math.random() * (maxHappy + 1)));
            e.children.add(next);
            genarateNexts(next, level + 1, maxLevel, maxNexts, maxHappy);
        }
    }
    
    //----------------------------对数器

    public static int findMaxHappy2(Node boss) {
        if (boss == null) {
            return 0;
        }
        return process1(boss, false);
    }

    public static int process1(Node cur, boolean up) {
        if (up) { // 如果cur的上级来的话，cur没得选，只能不来
            int ans = 0;
            for (Node next : cur.children) {
                ans += process1(next, false);
            }
            return ans;
        } else { // 如果cur的上级不来的话，cur可以选，可以来也可以不来
            int p1 = cur.val;
            int p2 = 0;
            for (Node next : cur.children) {
                p1 += process1(next, true);
                p2 += process1(next, false);
            }
            return Math.max(p1, p2);
        }
    }

}
