package com.jason.study.algorithmQuestions.class52;

import java.util.*;

/**
 * 你的国家有无数个湖泊，所有湖泊一开始都是空的。当第 n 个湖泊下雨的时候，如果第 n 个湖泊是空的，那么它就会装满水，否则这个湖泊会发生洪水。你的目标是避免任意一个湖泊发生洪水。
 * <p>
 * 给你一个整数数组 rains ，其中：
 * <p>
 * rains[i] > 0 表示第 i 天时，第 rains[i] 个湖泊会下雨。
 * rains[i] == 0 表示第 i 天没有湖泊会下雨，你可以选择 一个 湖泊并 抽干 这个湖泊的水。
 * 请返回一个数组 ans ，满足：
 * <p>
 * ans.length == rains.length
 * 如果 rains[i] > 0 ，那么ans[i] == -1 。
 * 如果 rains[i] == 0 ，ans[i] 是你第 i 天选择抽干的湖泊。
 * 如果有多种可行解，请返回它们中的 任意一个 。如果没办法阻止洪水，请返回一个 空的数组 。
 * <p>
 * 请注意，如果你选择抽干一个装满水的湖泊，它会变成一个空的湖泊。但如果你选择抽干一个空的湖泊，那么将无事发生（详情请看示例 4）。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/avoid-flood-in-the-city
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC1488 {

    public static int[] avoidFlood(int[] rains) {
        int[] ans = new int[rains.length];
        // 空数组，无法避免洪水时返回
        int[] invalid = new int[0];
        // 记录哪些湖泊是满的
        Set<Integer> full = new HashSet<>();
        // 每个湖泊，下雨天的集合
        HashMap<Integer, LinkedList<Integer>> map = new HashMap<>();
        for (int days = 0; days < rains.length; days++) {
            if (rains[days] != 0) {
                LinkedList<Integer> lakeRainDays = map.getOrDefault(rains[days], new LinkedList<>());
                lakeRainDays.add(days);
                map.put(rains[days], lakeRainDays);
            }
        }
        // 小根堆，那个湖下次先下雨，在堆顶
        PriorityQueue<Info> heap = new PriorityQueue<>();
        int idx = 0;
        for (int lake : rains) {
            if (lake != 0) {// 下雨
                ans[idx++] = -1;//下雨，干不了活
                if (full.contains(lake)) {
                    return invalid;
                }
                full.add(lake);
                map.get(lake).pollFirst();//把本次下雨的天数弹出来，下一个就是下次下雨的日子
                if (!map.get(lake).isEmpty()) {
                    heap.offer(new Info(map.get(lake).getFirst(), lake));
                }
            } else {// 干活，清空一个湖
                if (heap.isEmpty()) {
                    ans[idx++] = 1;//题意默认
                } else {
                    Info poll = heap.poll();
                    full.remove(poll.lake);
                    ans[idx++] = poll.lake;
                }
            }
        }
        return ans;
    }

    public static class Info implements Comparable<Info> {
        int nextRainDays;
        int lake;

        public Info(int nextRainDays, int lake) {
            this.nextRainDays = nextRainDays;
            this.lake = lake;
        }

        @Override
        public int compareTo(Info o) {
            return nextRainDays - o.nextRainDays;
        }
    }

    public static void main(String[] args) {
        int[] rain = new int[]{1, 0, 2, 0};
        System.out.println(Arrays.toString(avoidFlood(rain)));
    }
}
