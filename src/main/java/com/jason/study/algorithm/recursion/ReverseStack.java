package com.jason.study.algorithm.recursion;

import java.util.Stack;

/**
 * 给你一个栈，请你逆序这个栈，不能申请额外的数据结构，只能使用递归，如何实现
 *
 * @author JasonC5
 */
public class ReverseStack {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        //4 3 2 1
        process(stack);
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
        //1 2 3 4
    }

    private static void process(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        //第一步，把栈底的元素拿到，并返回
        int bottom = getBottom(stack);
        //第二步，把栈弹空
        process(stack);
        //第三步，按照返回的顺序把弹出来的放回去
        stack.push(bottom);
    }

    private static int getBottom(Stack<Integer> stack) {
        Integer pop = stack.pop();
        if (stack.isEmpty()) {
            return pop;
        } else {
            //递归地去找
            int bottom = getBottom(stack);
            //弹出来之后，要把拿出来的盖回去【除了底部那个】
            stack.push(pop);
            return bottom;
        }
    }

}
