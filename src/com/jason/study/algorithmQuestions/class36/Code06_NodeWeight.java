package com.jason.study.algorithmQuestions.class36;

import java.util.HashMap;
import java.util.Map;

/**
 * // 来自美团
 * // 有一棵树，给定头节点h，和结构数组m，下标0弃而不用
 * // 比如h = 1, m = [ [] , [2,3], [4], [5,6], [], [], []]
 * // 表示1的孩子是2、3; 2的孩子是4; 3的孩子是5、6; 4、5和6是叶节点，都不再有孩子
 * // 每一个节点都有颜色，记录在c数组里，比如c[i] = 4, 表示节点i的颜色为4
 * // 一开始只有叶节点是有权值的，记录在w数组里，
 * // 比如，如果一开始就有w[i] = 3, 表示节点i是叶节点、且权值是3
 * // 现在规定非叶节点i的权值计算方式：
 * // 根据i的所有直接孩子来计算，假设i的所有直接孩子，颜色只有a,b,k
 * // w[i] = Max {
 * //              (颜色为a的所有孩子个数 + 颜色为a的孩子权值之和),
 * //              (颜色为b的所有孩子个数 + 颜色为b的孩子权值之和),
 * //              (颜色为k的所有孩子个数 + 颜色k的孩子权值之和)
 * //            }
 * // 请计算所有孩子的权值并返回
 *
 * @author JasonC5
 */
public class Code06_NodeWeight {
    /**
     * @param h // 父节点
     * @param m // 树结构
     * @param w // 权重
     * @param c // 颜色
     */
    public static void w(int h, int[][] m, int[] w, int[] c) {
        if (m[h] == null || m[h].length == 0) {
            //叶子节点，无需处理
            return;
        }
        Map<Integer, Integer> colorCount = new HashMap<>();
        Map<Integer, Integer> weight = new HashMap<>();
        //先算孩子的信息，并保存需要的 计数 和 权值
        for (int child : m[h]) {
            w(child, m, w, c);
            colorCount.put(c[child], colorCount.getOrDefault(c[child], 0) + 1);
            weight.put(c[child], weight.getOrDefault(c[child], 0) + w[child]);
        }
        //算自己的权值
        for (Integer color : colorCount.keySet()) {
            w[h] = Math.max(w[h], colorCount.get(color) + weight.get(color));
        }
    }
}
