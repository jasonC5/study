package com.jason.study.algorithmQuestions.class01;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 给定一个文件目录的路径，
 * 写一个函数统计这个目录下所有的文件数量并返回
 * 隐藏文件也算，但是文件夹不算
 * --队列、宽度优先遍历
 * --栈、深度优先遍历
 *
 * @author JasonC5
 */
public class CountFiles {
    public static void main(String[] args) {
        String path = "D:\\apache-tomcat-6.0.37";
        System.out.println(getFileNumber(path));
        System.out.println(getFileNumber2(path));
    }

    public static int getFileNumber2(String folderPath) {
        File root = new File(folderPath);
        if (!root.isDirectory() && !root.isFile()) {
            return 0;
        }
        if (root.isFile()) {
            return 1;
        }
        Stack<File> stack = new Stack<>();
        stack.add(root);
        int files = 0;
        while (!stack.isEmpty()) {
            File folder = stack.pop();
            for (File next : folder.listFiles()) {
                if (next.isFile()) {
                    files++;
                }
                if (next.isDirectory()) {
                    stack.push(next);
                }
            }
        }
        return files;
    }

    private static int getFileNumber(String path) {
        File file = new File(path);
        Queue<File> queue = new LinkedList<>();
        queue.offer(file);
        int ans = 0;
        while (!queue.isEmpty()) {
            File poll = queue.poll();
            for (File son : poll.listFiles()) {
                if (son.isDirectory()) {
                    queue.offer(son);
                } else if (son.isFile()) {
                    ans++;
                }
            }
        }
        return ans;
    }

}
