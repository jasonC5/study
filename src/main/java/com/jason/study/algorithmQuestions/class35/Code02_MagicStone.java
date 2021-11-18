package com.jason.study.algorithmQuestions.class35;

import java.util.Arrays;

/**
 * // 来自小红书
 * // [0,4,7] ： 0表示这里石头没有颜色，如果变红代价是4，如果变蓝代价是7
 * // [1,X,X] ： 1表示这里石头已经是红，而且不能改颜色，所以后两个数X无意义
 * // [2,X,X] ： 2表示这里石头已经是蓝，而且不能改颜色，所以后两个数X无意义
 * // 颜色只可能是0、1、2，代价一定>=0
 * // 给你一批这样的小数组，要求最后必须所有石头都有颜色，且红色和蓝色一样多，返回最小代价
 * // 如果怎么都无法做到所有石头都有颜色、且红色和蓝色一样多，返回-1
 *
 * @author JasonC5
 */
public class Code02_MagicStone {
    public static int minCost(int[][] stones) {
        int length = stones.length;
        if ((length & 1) == 1) {
            return -1;//奇数个
        }
        int read = 0, blue = 0, none = 0, cost = 0;
        //安性价比排序，妙啊【先按颜色排序，都是无色，按照变成红色代价比蓝色大多少，越大排越前面【变成蓝色更划算的放前面】，先全部变红色，最后部分变蓝色的时候，只需要前面的变就好了】
        Arrays.sort(stones, (o1, o2) -> o1[0] == 0 && o2[0] == 0 ? ((o2[1] - o2[2]) - (o1[1] - o1[2])) : o1[0] - o2[0]);
        for (int[] stone : stones) {
            if (stone[0] == 1) {
                read++;
            } else if (stone[0] == 2) {
                blue++;
            } else {
                none++;
                cost += stone[1];//先把代价全都算成红色上
            }
        }
        if (read > (length >> 1) || blue > (length >> 1)) {
            return -1;
        }
        for (int i = 0; i < (length >> 1) - blue; i++) {
            cost += (stones[i][2] - stones[i][1]);
        }
        return cost;
    }

    public static void main(String[] args) {
        int[][] stones = { { 1, 5, 3 }, { 2, 7, 9 }, { 0, 6, 4 }, { 0, 7, 9 }, { 0, 2, 1 }, { 0, 5, 9 } };
        System.out.println(minCost(stones));
    }
}
