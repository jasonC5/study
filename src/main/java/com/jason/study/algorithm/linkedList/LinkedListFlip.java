package com.jason.study.algorithm.linkedList;

import java.util.ArrayList;
import java.util.List;

/**
 * 链表翻转
 *
 * @author JasonC5
 */
public class LinkedListFlip {

    public static void main(String[] args) {
        int[] arr = {1,2,3,4};
        Node head = buildLinkedList(arr);//对数器写生成随机链表
        Node head2 = buildLinkedList(arr);
        printLikedList(head);
        head = flipLinkedList(head);
        printLikedList(head);
        //对数器
        head2 = flipLinkedListByQueue(head2);
        printLikedList(head2);
    }

    public static void printLikedList(Node head) {
        while (head != null) {
            System.out.print(head.val);
            head = head.next;
        }
        System.out.println();
    }

    private static Node buildLinkedList(int[] arr) {
        Node head = null ;
        Node tmp = null;
        for (int num : arr) {
            Node node = new Node<Integer>(num);
            if (head == null){
                head = node;
                tmp = node;
            } else {
                tmp.next = node;
                tmp = node;
            }
        }
        return head;
    }

    public static Node flipLinkedList(Node head) {
        Node next = null;
        Node prov = null;
        while (head != null) {
            next = head.next;
            head.next = prov;
            prov = head;
            head = next;
        }
        return prov;
    }
    //对数器
    public static Node flipLinkedListByQueue(Node head){
        if (head == null){
            return head;
        }
        List<Node> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        list.get(0).next = null;
        for (int i = 1; i < list.size(); i++) {
            list.get(i).next = list.get(i-1);
        }
        return list.get(list.size()-1);
    }


    public static class Node<T> {
        T val;
        Node next;

        public Node(T val) {
            this.val = val;
        }
    }
}
