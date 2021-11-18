package com.jason.study.algorithm.linkedList;

import java.util.*;
import java.util.concurrent.LinkedTransferQueue;

/**
 * 如何使用队列结构实现栈结构
 *
 * @author JasonC5
 */
public class StackByQueue<T> {
    Queue<Integer> queue0 = new LinkedTransferQueue<Integer>();
    Queue<Integer> queue1 = new LinkedTransferQueue<Integer>();
    volatile int findDataQueue = 0;//0开始放,谁是空的谁当临时队列
    Map<Integer, Queue> stackMap = new HashMap<>();
    //两个队列循环用，放到stackMap中，根据 findDataQueue 的值确定谁是数据队列，另外一个是临时队列
    {
        stackMap.put(0, queue0);
        stackMap.put(1, queue1);
    }

    public T pop() {//弹出
        Queue dataQueue = stackMap.get(findDataQueue);//数据队列
        Queue tmpQueue = stackMap.get(findDataQueue ^= 1);//临时队列  //0^1 = 1    1^1 = 0  此时改变  findDataQueue 的值，相当于就交换了两个队列
        if (dataQueue.isEmpty()) {
            return null;//数据队列是空的，直接返回空
        }
        T tmp = null;
        while (dataQueue.size() > 1 && (tmp = (T) dataQueue.poll()) != null) {//剩一个
            tmpQueue.offer(tmp);
        }
        T ans = (T) dataQueue.poll();//队列的底部，需要返回给用户的
//        findDataQueue ^= 1;
        return ans;
    }

    public void push(T t) {
        Queue dataQueue = stackMap.get(findDataQueue);//数据队列
        dataQueue.offer(t);
    }

    public static void main(String[] args) {
        StackByQueue<Integer> stack = new StackByQueue<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
