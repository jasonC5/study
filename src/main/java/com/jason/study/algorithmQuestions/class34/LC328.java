package com.jason.study.algorithmQuestions.class34;


public class LC328 {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        System.out.println(oddEvenList(head));
    }

    public static ListNode oddEvenList(ListNode head) {
        if (head == null){
            return null;
        }
        ListNode cur = head;
        ListNode evenHead = head.next;
        ListNode curEven = head.next;
        ListNode next = evenHead == null ? null : evenHead.next;
        while (next != null) {
            curEven.next = next.next;
            curEven = curEven.next;
            ListNode nextNext = next.next == null ? null : next.next.next;
            cur.next = next;
            next.next = evenHead;
            cur = next;
            next = nextNext;
        }
        return head;
    }
}
