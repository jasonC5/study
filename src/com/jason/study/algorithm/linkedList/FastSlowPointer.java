package com.jason.study.algorithm.linkedList;

/**
 * 1）输入链表头节点，奇数长度返回中点，偶数长度返回上中点
 *
 * 2）输入链表头节点，奇数长度返回中点，偶数长度返回下中点
 *
 * 3）输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
 *
 * 4）输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
 * @author JasonC5
 */
public class FastSlowPointer {

    public static void main(String[] args) {
        LinkedListFlip.Node head = new LinkedListFlip.Node(1);
        LinkedListFlip.Node pointer = head;
        for (int i = 2; i <= 6; i++) {
            pointer.next = new LinkedListFlip.Node(i);
            pointer = pointer.next;
        }
        System.out.println(function3(head).val);
    }
    //输入链表头节点，奇数长度返回中点，偶数长度返回上中点
    public static LinkedListFlip.Node function1(LinkedListFlip.Node head) {
        LinkedListFlip.Node faster = head;
        LinkedListFlip.Node slower = head;
        while (faster != null){
            faster = faster.next;
            if (faster == null) {
                break;
            }
            faster = faster.next;
            if (faster == null) {
                break;
            }
            slower = slower.next;
        }
        return slower;
    }

    //输入链表头节点，奇数长度返回中点，偶数长度返回上中点
    public static LinkedListFlip.Node function2(LinkedListFlip.Node head) {
        LinkedListFlip.Node faster = head;
        LinkedListFlip.Node slower = head;
        while (faster != null){
            faster = faster.next;
            if (faster == null) {
                break;
            }
            faster = faster.next;
            slower = slower.next;
        }
        return slower;
    }

    public static LinkedListFlip.Node function3(LinkedListFlip.Node head) {
        LinkedListFlip.Node faster = head;
        LinkedListFlip.Node slower = head;
        LinkedListFlip.Node last = null;
        while (faster != null){
            faster = faster.next;
            if (faster == null) {
                break;
            }
            faster = faster.next;
            if (faster == null) {
                break;
            }
            last = slower;
            slower = slower.next;
        }
        return last;
    }

    public static LinkedListFlip.Node function4(LinkedListFlip.Node head) {
        LinkedListFlip.Node faster = head;
        LinkedListFlip.Node slower = head;
        LinkedListFlip.Node last = null;
        while (faster != null){
            faster = faster.next;
            if (faster == null) {
                break;
            }
            faster = faster.next;
            last = slower;
            slower = slower.next;
        }
        return last;
    }
}
