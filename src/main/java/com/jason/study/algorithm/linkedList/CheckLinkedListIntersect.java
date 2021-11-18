package com.jason.study.algorithm.linkedList;

/**
 * 判断两个链表是否相交（可能有环可能无环）
 * <p>
 * 1.判断里两个链表是否有环
 * 2.1 两个链表都没有环，找到更长的一个链表，先往下走差值的步数，然后两个指针同步往下走，
 * 2.2 两个链表一个有环一个无环，则必不相交
 * 2.3 若两链表都有环，则分四种情况
 * 3.1两个链表不相交，各自有环
 * 3.2两个链表相交，焦点在入环点之前
 * 3.3两个链表相交，交点就是环点
 * --3.2和3.3有一个统一判断条件，拥有相同的入环点【FindRingStartPoint】
 * 3.4两个链表相交，分别在不同点入环，
 *
 * @author JasonC5
 */
public class CheckLinkedListIntersect {

    public static void main(String[] args) {
        LinkedListFlip.Node head1 = new LinkedListFlip.Node(1);
        LinkedListFlip.Node head2 = new LinkedListFlip.Node(2);
        LinkedListFlip.Node node3 = new LinkedListFlip.Node(3);
        LinkedListFlip.Node node4 = new LinkedListFlip.Node(4);
        LinkedListFlip.Node node5 = new LinkedListFlip.Node(5);
        LinkedListFlip.Node node6 = new LinkedListFlip.Node(6);
        LinkedListFlip.Node node7 = new LinkedListFlip.Node(7);
        LinkedListFlip.Node node8 = new LinkedListFlip.Node(8);
        LinkedListFlip.Node node9 = new LinkedListFlip.Node(9);
        LinkedListFlip.Node node10 = new LinkedListFlip.Node(10);
        LinkedListFlip.Node node11 = new LinkedListFlip.Node(11);

        head1.next = node3;
        node3.next = node5;
        head2.next = node4;
        node4.next = node6;
        node5.next = node7;
        node6.next = node9;
        node7.next = node8;
        node8.next = node9;
        node9.next = node10;
        node10.next = node11;
        node11.next = node8;

        /**
         *          1       2
         *          3       4
         *          5       6
         *              ↓
         *              7   相交点7
         *              8   ←---
         *              9       ↑
         *              10      ↑
         *              11  ----↑
         */

        boolean intersect = checkIsIntersect(head1, head2);
        System.out.println(intersect);
    }

    private static boolean checkIsIntersect(LinkedListFlip.Node head1, LinkedListFlip.Node head2) {
        LinkedListFlip.Node list1RingNode = FindRingStartPoint.findRingStartNodeByHash(head1);
        LinkedListFlip.Node list2RingNode = FindRingStartPoint.findRingStartNodeByHash(head2);
        if (list1RingNode == null && list2RingNode == null) {
            int length1 = linkedListLength(head1);
            int length2 = linkedListLength(head2);
            //比较出更长的链表，供下一步使用
            LinkedListFlip.Node longer = length1 > length2 ? head1 : head2;
            LinkedListFlip.Node shorter = longer == head1 ? head2 : head1;
            //先走差值步数
            for (int i = 0; i < length1 - length2; i++) {
                longer = longer.next;
            }
            //逐步下跳
            while (longer != null) {
                if (longer == shorter) {
                    //找到交点
                    return true;
                }
                longer = longer.next;
                shorter = shorter.next;
            }
            //走完了还没找到交点，不相交
            return false;
        } else if (list1RingNode != null && list2RingNode != null) {
            //同一个入环点，说明在入环之前就相交了
            if (list1RingNode == list2RingNode) {
                return true;
            }
            //入环点不同，要么不相交，要么两个入环点在一个环内，那么从其中一个入环点下跳，必定能到达另一个入环点
            LinkedListFlip.Node pointer = list1RingNode.next;
            while (pointer != list1RingNode) {
                if (pointer == list2RingNode) {
                    return true;
                }
                //转了一圈了，还没碰到链表2的入环点，不相交
                pointer = pointer.next;
            }
            return false;
        } else {
            //一个有环，一个无环，必不相交
            return false;
        }
    }

    public static int linkedListLength(LinkedListFlip.Node head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }

}
