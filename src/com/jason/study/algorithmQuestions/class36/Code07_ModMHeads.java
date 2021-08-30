package com.jason.study.algorithmQuestions.class36;

/**
 * // 来自腾讯
 * // 给定一个单链表的头节点head，每个节点都有value(>0)，给定一个正数m
 * // value%m的值一样的节点算一类
 * // 请把所有的类根据单链表的方式重新连接好，返回每一类的头节点
 *
 * @author JasonC5
 */
public class Code07_ModMHeads {

    public static class Node {
        public int value;
        public Node next;
    }

    public static class Ht {
        public Node h;
        public Node t;

        public Ht(Node a) {
            h = a;
            t = a;
        }
    }

    public static Node[] split(Node h, int m) {
        Node[] ans = new Node[m];//只存放头节点
        Node[] tail = new Node[m];//存放尾节点，缩减时间复杂度
        while (h != null) {
            Node next = h.next;
            next.next = null;
            int idx = next.value % m;
            if (ans[idx] == null) {
                ans[idx] = h;
                tail[idx] = h;
            } else {
//                Node reader = ans[idx];
//                //可用搞一个容器放头和尾节点
//                while (reader.next != null) {
//                    reader = reader.next;
//                }
//                reader.next = h;
                tail[idx].next = h;
                tail[idx] = h;
            }
            h = next;
        }
        return ans;
    }

}
