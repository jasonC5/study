package com.jason.study.algorithm.linkedList;

import java.util.Objects;
import java.util.Stack;

/**
 * 判断回文串
 *
 * @author JasonC5
 */
public class CheckPalindrome {

    public static void main(String[] args) {
        LinkedListFlip.Node head = new LinkedListFlip.Node(1);
        head.next = new LinkedListFlip.Node(2);
        head.next.next = new LinkedListFlip.Node(3);
        head.next.next.next = new LinkedListFlip.Node(2);
        head.next.next.next.next = new LinkedListFlip.Node(1);
//        head.next.next.next.next.next = new LinkedListFlip.Node(1);
//        head.next = new LinkedListFlip.Node(2);
//        head.next.next = new LinkedListFlip.Node(1);
//        System.out.println(checkUseContainer1(head));
        System.out.println(checkNotUseContainer(head));
    }

    private static boolean checkNotUseContainer(LinkedListFlip.Node head) {
        //1.快慢指针找到中间节点
        //2.右边改变结构从右往左指‘
        //3.左指针右指针往中间缩，若两边value不相等则返回false，任意一个为空则终止返回true，【记下来不返回】
        //4.将两个链表重新整理回一个链表，返回ans
        if (head == null){
            //没有数据，是否是对称的？这里认为是
            return true;
        }
        boolean ans = false;
        LinkedListFlip.Node mid = FastSlowPointer.function1(head);
        if (mid.next == null){
//            此时说明只有一个节点，直接返回
            return true;
        }
        //取到中间点的下一个
        LinkedListFlip.Node midNext = mid.next;
        //将中间点置为空
        mid.next = null;
        //打印一下
        LinkedListFlip.printLikedList(head);
        LinkedListFlip.printLikedList(midNext);
        //将右半边翻转
        LinkedListFlip.Node pre = null;
        while (midNext != null) {
            //temp指针向后移动一个
            LinkedListFlip.Node tmp = midNext.next;
            //当前指针的next指给前面
            midNext.next = pre;
            //上一个指针挪过来
            pre = midNext;
            //当前指针后移动
            midNext = tmp;
        }
        //此时per已经成为了右半边的头结点
        LinkedListFlip.printLikedList(pre);
        LinkedListFlip.Node leftReader = head;
        LinkedListFlip.Node rightReader = pre;
        while (!(ans = (leftReader == null || rightReader == null))) {
            if (leftReader.val != rightReader.val) {
                ans = false;
                break;
            }
            leftReader = leftReader.next;
            rightReader = rightReader.next;
        }
        //pre还原回去【上一步反过来】
        while (pre != null) {
            //temp指针向后移动一个
            LinkedListFlip.Node tmp = pre.next;
            //当前指针的next指给前面
            pre.next = midNext;
            //上一个指针挪过来
            midNext = pre;
            //当前指针后移动
            pre = tmp;
        }
        mid.next = midNext;
        System.out.println("linkedList ：");
        LinkedListFlip.printLikedList(head);
        return ans;
    }

    public static boolean checkUseContainer1(LinkedListFlip.Node head) {
        //用容器-栈：入栈然后出栈==逆序，再和原顺序对比，若一直则是回文结构
        Stack<LinkedListFlip.Node> stack = new Stack<>();
        LinkedListFlip.Node reader = head;
        while (reader != null) {
            stack.push(reader);
            reader = reader.next;
        }
        reader = head;
        while (reader != null) {
            if (!Objects.equals(stack.pop().val, reader.val)) {
                return false;
            }
            reader = reader.next;
        }
        return true;
    }

}
