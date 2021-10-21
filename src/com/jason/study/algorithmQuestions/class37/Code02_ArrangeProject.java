package com.jason.study.algorithmQuestions.class37;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * // 项目规划问题
 * // 刚入职网易互娱，新人mini项目便如火如荼的开展起来。为了更好的项目协作与管理，
 * // 小易决定将学到的甘特图知识用于mini项目时间预估。小易先把项目中每一项工作以任务的形式列举出来，
 * // 每项任务有一个预计花费时间与前置任务表，必须完成了该任务的前置任务才能着手去做该任务。
 * // 作为经验PM，小易把任务划分得井井有条，保证没有前置任务或者前置任务全数完成的任务，都可以同时进行。小易给出了这样一个任务表，请作为程序的你计算需要至少多长时间才能完成所有任务。
 * // 输入第一行为一个正整数T，表示数据组数。
 * // 对于接下来每组数据，第一行为一个正整数N，表示一共有N项任务。
 * // 接下来N行，每行先有两个整数Di和Ki，表示完成第i个任务的预计花费时间为Di天，该任务有Ki个前置任务。
 * // 之后为Ki个整数Mj，表示第Mj个任务是第i个任务的前置任务。
 * // 数据范围：对于所有数据，满足1<=T<=3, 1<=N, Mj<=100000, 0<=Di<=1000, 0<=sum(Ki)<=N*2。
 * @author JasonC5
 */
public class Code02_ArrangeProject {
    public static int dayCount(ArrayList<Integer>[] nums, int[] days, int[] headCount) {
        int maxDay = 0;
        //存放每个任务第几天做完
        int[] countDay = new int[days.length];
        //拓扑排序
        Queue<Integer> head = countHead(headCount);
        while (!head.isEmpty()) {
            int cur = head.poll();
            // 当前任务的时间
            countDay[cur] += days[cur];
            // 依赖当前任务 的任务
            for (int j = 0; j < nums[cur].size(); j++) {
                // 依赖减一
                headCount[nums[cur].get(j)]--;
                // 没有依赖了，加入队列
                if (headCount[nums[cur].get(j)] == 0) {
                    head.offer(nums[cur].get(j));
                }
                // 当前这个任务，的完成时间，取最大值
                countDay[nums[cur].get(j)] = Math.max(countDay[nums[cur].get(j)], countDay[cur]);
            }
        }
        // 过一遍取最大值
        for (int i = 0; i < countDay.length; i++) {
            maxDay = Math.max(maxDay, countDay[i]);
        }
        return maxDay;
    }

    //无前置任务的 放入队列
    private static Queue<Integer> countHead(int[] headCount) {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < headCount.length; i++) {
            if (headCount[i] == 0){
                queue.offer(i); // 没有前驱任务
            }
        }
        return queue;
    }
}
