package com.jason.study.algorithm.windowMaxMin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 假设一个固定大小为W的窗口，依次划过arr，
 * 返回每一次滑出状况的最大值
 * 例如，arr = [4,3,5,4,3,3,6,7], W = 3
 * 返回：[5,5,5,4,6,7]
 * @author JasonC5
 */
public class WindowMax {
    public static void main(String[] args) {
        int[] arr = {4, 3, 5, 4, 3, 3, 6, 7};
        int win = 3;
        List<Integer> winMax = winMax(arr, win);
        System.out.println(winMax);
    }

    private static List<Integer> winMax(int[] arr, int win) {
        List<Integer> ans = new ArrayList<>();
        LinkedList<Integer> list = new LinkedList();
        for (int i = 0; i < arr.length; i++) {
            //把尾部小于自己的都弹出来
            while(!list.isEmpty() && arr[i] >= arr[list.peekLast()]){
                list.pollLast();
            }
            //把自己放进去
            list.addLast(i);
            //记录，并把头部该弹出来的都弹出来
            if (list.peekFirst() == (i - win)) {
                list.pollFirst();
            }
            if (i >= win - 1) {
                ans.add(arr[list.peekFirst()]);
            }
        }
        return ans;
    }
}
