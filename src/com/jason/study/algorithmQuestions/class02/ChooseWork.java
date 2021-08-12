package com.jason.study.algorithmQuestions.class02;

import java.util.Arrays;
import java.util.TreeMap;

/**
 * 给定数组hard和money，长度都为N
 * hard[i]表示i号的难度， money[i]表示i号工作的收入
 * 给定数组ability，长度都为M，ability[j]表示j号人的能力
 * 每一号工作，都可以提供无数的岗位，难度和收入都一样
 * 但是人的能力必须>=这份工作的难度，才能上班
 * 返回一个长度为M的数组ans，ans[j]表示j号人能获得的最好收入
 *
 * @author JasonC5
 */
public class ChooseWork {
    public static void main(String[] args) {

    }

    public static class Job {
        public int money;
        public int hard;

        public Job(int m, int h) {
            money = m;
            hard = h;
        }
    }

    public static int[] getMoneys(Job[] job, int[] ability) {
        //贪心::排序，根据能力从小到大，相同能力的，价值从大到小，相同能力价值最大的，若能力需求大，价值反而小，也删除
        //得到一个单调性数组::能力递增，价值也递增
        Arrays.sort(job, (job1, job2) -> (job1.hard == job2.hard) ? (job2.money - job1.money) : (job1.hard - job2.hard));
        Job pre = job[0];
        //筛选可用
        //难度:价值
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(job[0].hard, job[0].money);
        for (int i = 0; i < job.length; i++) {
            if (job[i].hard != pre.hard && job[i].money > pre.money) {
                pre = job[i];
                map.put(pre.hard, pre.money);
            }
        }
        //加个垫底的，少判断点边界
        map.put(Integer.MIN_VALUE, 0);
        int[] ans = new int[ability.length];
        for (int i = 0; i < ability.length; i++) {
            ans[i] = map.get(map.floorKey(ability[i]));
        }
        return ans;
    }


}
