package com.jason.study.algorithm.linkedList;

/**
 * 删除链表中指定值的元素
 * @author JasonC5
 */
public class RemoveConst {
    //双向链表简单， node.prov.next = node.next   node.next.prov = node.prov,就把自己摘出去了
    //单向链表也简单，前一个指向后一个
    //有可能头部也要删除，第一件事是找到新的头部

    //对数器：List，！=该值的放到List中，重新组装成一个新的链表
    //只实现单向列表吧
    public static void main(String[] args) {
        LinkedListFlip.Node<Integer> head = new LinkedListFlip.Node<>(1);
        LinkedListFlip.Node<Integer> tmp = head;
        for (int i = 2; i < 10; i++) {
            tmp.next = new LinkedListFlip.Node<>(i);
            tmp = tmp.next;
        }
        tmp.next = new LinkedListFlip.Node(1);
        LinkedListFlip.printLikedList(head);
        head = deleteNodeByVal(head, 1);
        LinkedListFlip.printLikedList(head);
    }

    public static LinkedListFlip.Node<Integer> deleteNodeByVal(LinkedListFlip.Node<Integer> head, int deleteVal){
        //先重新定义头
        while (head != null && head.val == deleteVal){
            head = head.next;
        }
        //删完了
        if (head == null) {
            return null;
        }
        //上一个
        LinkedListFlip.Node<Integer> prov = head;
        //head已经不可能需要删除了，从head.next开始找
        LinkedListFlip.Node<Integer> tmp = head.next;
        while (tmp != null) {
            if (tmp.val == deleteVal) {
                prov.next = tmp.next;//前一个的next指向自己的next，前一位不动
            } else {//不删，两个指针都往后移动
                prov = tmp;
            }
            tmp = tmp.next;
        }
        return head;
    }

}
