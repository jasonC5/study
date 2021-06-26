package com.jason.study.algorithm.greedy;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 2.一些项目要占用会议室宣讲，会议室不能同时容纳两个项目，给你每一个项目的开始时间和结束时间，来安排宣讲的日程，要求会议室进行的宣讲的场次最多，返回最多的宣讲场次
 * 结束时间最早的
 *
 * @author JasonC5
 */
public class Greedy2 {

    public static class Program {
        public int start;
        public int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    // 暴力！所有情况都尝试！
    public static int bestArrange1(Program[] programs) {
        if (programs == null || programs.length == 0) {
            return 0;
        }
        return process(programs, 0, 0);
    }

    public static int process(Program[] programs, int done, int timeLine) {
        if (programs.length == 0) {
            return done;
        }
        // 还剩下会议
        int max = done;
        // 当前安排的会议是什么会，每一个都枚举
        for (int i = 0; i < programs.length; i++) {
            if (programs[i].start >= timeLine) {
                Program[] next = copyButExcept(programs, i);
                max = Math.max(max, process(next, done + 1, programs[i].end));
            }
        }
        return max;
    }

    public static Program[] copyButExcept(Program[] programs, int i) {
        Program[] ans = new Program[programs.length - 1];
        int index = 0;
        for (int k = 0; k < programs.length; k++) {
            if (k != i) {
                ans[index++] = programs[k];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int mettingSize = 12;
        int timeMax = 24;
        int timeTimes = 100;
//        int[][] mettings = {{1, 5}, {10, 14}, {7, 8}, {20, 22}, {2, 3}, {3, 4}};
        for (int i = 0; i < timeTimes; i++) {
            Program[] programs = generatePrograms(mettingSize, timeMax);
            int maxNum1 = bestArrangeMy(programs);
            int maxNum2 = bestArrange1(programs);
            if (maxNum1 != maxNum2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finished");
    }

    public static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if (r1 == r2) {
                ans[i] = new Program(r1, r1 + 1);
            } else {
                ans[i] = new Program(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return ans;
    }

    private static int bestArrangeMy(Program[] mettings) {
        if (mettings == null || mettings.length == 0) {
            return 0;
        }
        //1.根据结束时间排序，结束时间早的先排，完成之后根据
        int startTime = 0;
        int meetNum = 0;
        PriorityQueue<Program> priorityQueue = new PriorityQueue<>(new Comparator<Program>() {
            @Override
            public int compare(Program o1, Program o2) {
                return o1.end - o2.end;
            }
        });
        for (Program metting : mettings) {
            priorityQueue.add(metting);
        }
        while (!priorityQueue.isEmpty()) {
            Program poll = priorityQueue.poll();
            if (poll.start >= startTime) {
                meetNum++;
                startTime = poll.end;
            }
        }
        return meetNum;
    }

}
