package com.jason.study.algorithm.tree;

/**
 * 打印纸条折痕--微软面试题
 * 一条纸条向上对折n次，从上到下打印折痕【凹、凸】
 * 第一次对折：凹
 * 第二次对折：凹 凹 凸
 * 第三次对折：凹 凹 凸 凹 凹 凸 凹 凸 凹
 * 每一次对折，会在之前的折痕上面出现一条凹折痕，下面出现一条凸折痕
 * @author JasonC5
 */
public class PrintNoteCrease {
    public static void main(String[] args) {
        printCase(4);
    }

    private static void printCase(int n) {
        //i作为层数
        //第一层只有一个节点：凹
        //一个每个节点的左子孩子是凹，右子孩子是凸
        //整个折痕的打印，就是这么一个二叉树的中序遍历
        //递归实现
        printProcess(1,n,"凹");
    }

    /**
     *
     * @param i 当前层数
     * @param n 总共层数
     * @param b 当前节点是凹还是凸
     */
    private static void printProcess(int i, int n, String b) {
        if (i > n){
            return;
        }
        printProcess(i+1, n, "凹");
        System.out.print(b + " ");
        printProcess(i+1, n, "凸");
    }
}
