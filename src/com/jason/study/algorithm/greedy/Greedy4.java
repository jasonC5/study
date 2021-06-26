package com.jason.study.algorithm.greedy;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 *
 * 4.输入正整数组costs、正整数组profits、正数K、正数M
 * costs[i] 表示项目的花费
 * profits[i] 表示项目在扣除花费之后的利润
 * k表示你只能穿行的最多做K个项目
 * M表示你的初始资金
 * 每做完一个项目，马上获得的收益，可以支持你去做下一个项目，不能并行的做项目
 * 输出：你最后获得的最大钱数
 * 小根堆、大根堆、提前结束
 * @author JasonC5
 */
public class Greedy4 {
    public static void main(String[] args) {
        //打怪升级问题
        //当前成本能获取的最大利润的项目

    }

    public static int findMaximizedCapital(List<Program> programList, int m, int k){
        //小顶堆中按成本排序往外取
        //大顶堆中按利润排序取一个
        PriorityQueue<Program> constHeap = new PriorityQueue(new Comparator<Program>() {
            @Override
            public int compare(Program o1, Program o2) {
                return o1.c-o2.c;
            }
        });
        PriorityQueue<Program> profitHeap = new PriorityQueue(new Comparator<Program>() {
            @Override
            public int compare(Program o1, Program o2) {
                return o2.p-o1.p;
            }
        });
        for (Program program : programList) {
            if (program.p <= 0) {
                continue;
            }
            constHeap.add(program);
        }
        //只能取k个
        int ans = 0;
        for (int i = 0; i < k; i++) {
//            成本是m
            Program poll = constHeap.poll();
            while (poll.c > m) {
                profitHeap.add(poll);
                poll = constHeap.poll();
            }
            if (profitHeap.isEmpty()) {
                return ans;
            }
            Program maxProfit = profitHeap.poll();
            ans += maxProfit.p;
            //手上的资金变多了，下次可以选更高成本的了
            m += maxProfit.p;
        }
        return 0; 
    }
    
    public static class Program {
        public int c;//成本
        public int p;//利润

        public Program(int p, int c) {
            this.p = p;
            this.c = c;
        }
    }
    
}
