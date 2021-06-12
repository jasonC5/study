package com.jason.study.algorithm.linkedList;

import java.util.HashMap;

/**
 * @author JasonC5
 */
public class RandomPointerLinked {
    public static class RandomPointerNode<T> {
        T value;
        RandomPointerNode next;
        RandomPointerNode random;

        public RandomPointerNode(T value) {
            this.value = value;
        }
    }

    public static void printLikedList(RandomPointerNode head) {
        String rand = "";
        while (head != null) {
            System.out.print(head.value);
            rand += head.random == null ? "" : head.random.value.toString();
            head = head.next;
        }
        System.out.println();
        System.out.println(rand);
    }

    public static void main(String[] args) {
        RandomPointerNode head = new RandomPointerNode(0);
        RandomPointerNode node1 = new RandomPointerNode(1);
        RandomPointerNode node2 = new RandomPointerNode(2);
        RandomPointerNode node3 = new RandomPointerNode(3);
        RandomPointerNode node4 = new RandomPointerNode(4);
        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        head.random = node2;
        node1.random = node2;
        node2.random = node4;
        node3.random = node1;
        node4.random = node3;

        printLikedList(head);
        //用哈希表
        RandomPointerNode copyHead = copyByHashMap(head);
        printLikedList(copyHead);
        //不用哈希表
        RandomPointerNode copyHead2 = copyByO1(head);
        printLikedList(copyHead2);
    }

    private static RandomPointerNode copyByO1(RandomPointerNode head) {
        //创建复制节点放在原节点后面，插在原来node和next中间，源节点.random.next，就是复制节点的random指针
        //构建好之后再把两个链表拆开
        //1.创建复制节点并
        RandomPointerNode reader = head;
        RandomPointerNode copierHead = null;
        while (reader != null) {
            RandomPointerNode copier = new RandomPointerNode(reader.value);
            if (copierHead == null) {
                copierHead = copier;
            }
            copier.next = reader.next;
            reader.next = copier;
            reader = copier.next;
        }
//        printLikedList(head);
        //处理random指针
        reader = head;
        while (reader != null) {
            RandomPointerNode copier = reader.next;
            copier.random = reader.random == null ? null : reader.random.next;
            reader = copier.next;
        }
//        printLikedList(head);
        //拆开两个链表
        reader = head;
        while (reader != null) {
            RandomPointerNode copier = reader.next;
            reader.next = copier.next;
            copier.next = reader.next == null ? null : reader.next.next;
            reader = reader.next;
        }
        return copierHead;
    }

    private static RandomPointerNode copyByHashMap(RandomPointerNode head) {
        HashMap<RandomPointerNode, RandomPointerNode> nodeMap = new HashMap<>();
        RandomPointerNode reader = head;
        //先拷贝一份node
        while (reader != null) {
            RandomPointerNode copyNode = new RandomPointerNode(reader.value);
            nodeMap.put(reader, copyNode);
            reader = reader.next;
        }
        //再构建Next指针和Random指针
        reader = head;
        while (reader != null) {
            nodeMap.get(reader).next = nodeMap.get(reader.next);
            nodeMap.get(reader).random = nodeMap.get(reader.random);
            reader = reader.next;
        }
        return nodeMap.get(head);
    }


}
