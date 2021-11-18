package com.jason.study.algorithm.linkedList;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 如何使用栈结构实现队列结构，如何使用队列结构实现栈结构
 *
 * @author JasonC5
 */
public class QueueByStack<T> {
    //利用栈实现队列
    Stack dataStack = new Stack();
    Stack tmpStack = new Stack();
//    volatile int status = 0;//0开始放
//    Map<Integer, Stack> stackMap = new HashMap<>();
//    {
//        stackMap.put(0,stack0);
//        stackMap.put(1,stack1);
//    }

    public T pop(){//弹出
        if (dataStack.empty()){
            return null;
        }
        //从数据栈中捯出来到临时栈
        T t = null;
        //先从数据栈捯到临时栈
        while (!dataStack.empty() && (t = (T)dataStack.pop()) != null){
            tmpStack.push(t);
        }
        //从临时栈中弹出栈顶元素
        T ans = (T)tmpStack.pop();
        //再把数据再倒回数据栈（不能直接换引用的原因是数据顺序是反的，倒回去换顺序）
        while (!tmpStack.empty() && (t = (T)tmpStack.pop()) != null){
            dataStack.push(t);
        }
        return ans;
    }

    public void push(T t){
        dataStack.push(t);//
    }

    public static void main(String[] args) {
        QueueByStack<Integer> queue = new QueueByStack<Integer>();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        queue.push(4);
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
    }
}
