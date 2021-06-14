package com.jason.study.algorithm.linkedList;

import java.util.HashSet;
import java.util.Set;

/**
 * 查找环起始点
 *
 * @author JasonC5
 */
public class FindRingStartPoint {

    public static void main(String[] args) {
        LinkedListFlip.Node head = new LinkedListFlip.Node(0);
        LinkedListFlip.Node node1 = new LinkedListFlip.Node(1);
        LinkedListFlip.Node node2 = new LinkedListFlip.Node(2);
        LinkedListFlip.Node node3 = new LinkedListFlip.Node(3);
        LinkedListFlip.Node node4 = new LinkedListFlip.Node(4);
        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node2;
//        LinkedListFlip.Node ringStarter = findRingStartNodeByHash(head);
        LinkedListFlip.Node ringStarter = findRingStartNodeNoHash(head);
        System.out.println(ringStarter == null ? "null" : ringStarter.val);
    }

    public static LinkedListFlip.Node findRingStartNodeNoHash(LinkedListFlip.Node head) {
        //快慢指针
        //一个走一步，一个走两步
        //什么时候相遇了，快指针回到起点，都走一步，再次相遇，相遇点就是环开始点
        if (head == null || head.next == null || head.next.next == null){
            return null;
        }
        LinkedListFlip.Node faster = head;
        LinkedListFlip.Node slower = head;
        while (true){
            if (faster.next == null || faster.next.next == null) {
                return null;
            }
            faster = faster.next.next;
            slower = slower.next;
            if (slower == faster){
                break;
            }
        }
        faster = head;
        while (true) {
            faster = faster.next;
            slower = slower.next;
            if (faster == slower) {
                return slower;
            }
        }
    }

    public static LinkedListFlip.Node findRingStartNodeByHash(LinkedListFlip.Node head) {
        Set<LinkedListFlip.Node> set = new HashSet<>();
        LinkedListFlip.Node reader = head;
        while (reader != null) {
            if (set.contains(reader)) {
                return reader;
            }
            set.add(reader);
            reader = reader.next;
        }
        return null;
    }




}
