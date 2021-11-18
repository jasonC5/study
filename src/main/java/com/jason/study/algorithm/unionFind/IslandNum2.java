package com.jason.study.algorithm.unionFind;

import java.util.ArrayList;
import java.util.List;

/**
 * .LC305-付费题--岛屿问题2
 * 给定一个矩阵，初始都是0，给定一个list，每次在找到岛中的一个位置，改成1，获取此时矩阵中的岛数量，返回和list长度相同的数字列表。
 *
 * @author JasonC5
 */
public class IslandNum2 {

    //并查集
    public static List<Integer> numIslands2(char[][] grid, int[][] pointers) {
        //并查集 遍历数组，当前元素为'1'的时候 总大小+1，然后查看上、左两个元素是否为1，为1时进行合并，总岛屿数-1
        int r = grid.length;
        int c = grid[0].length;
        UnionFind unionFind = new UnionFind(r, c);
        List<Integer> ans = new ArrayList<>();
        for (int[] pointer : pointers) {
            ans.add(unionFind.connect(pointer[0], pointer[1]));
        }
        return ans;
    }

    static class UnionFind {
        //代表节点下标
        int[] parent;
        //代表节点集合大小
        int[] size;
        //压缩继承链用的辅助记录数组
        int[] help;
        //一共有多少个岛屿
        int sets;
        //列数
        int col;
        //行数
        int row;

        public UnionFind(int m, int n) {
            row = m;
            col = n;
            int leng = m * n;
            parent = new int[leng];
            size = new int[leng];
            help = new int[leng];
            sets = 0;
        }

        private int index(int r, int c) {
            return r * col + c;
        }

        private void union(int r1, int c1, int r2, int c2) {
            //越界，不合并
            if (r1 < 0 || r1 >= row || r2 < 0 || r2 >= row || c1 < 0 || c1 >= col || c2 < 0 || c2 >= col) {
                return;
            }
            int i = index(r1, c1);
            int j = index(r2, c2);
            //任意一个点没岛，结束合并
            if (size[i] == 0 || size[j] == 0) {
                return;
            }
            //已经在同一个岛屿了，不合并
            int parentI, parentJ;
            if ((parentI = find(i)) == (parentJ = find(j))) {
                return;
            }
            //小的往大的里面合并
            int bigger = size[parentI] >= size[parentJ] ? parentI : parentJ;
            int lesser = bigger == parentJ ? parentI : parentJ;
            size[bigger] = size[parentI] + size[parentJ];
            parent[lesser] = bigger;
            sets--;
        }

        public int find(int i) {
            int helpIdx = 0;
            while (parent[i] != i) {
                help[helpIdx++] = i;
                i = parent[i];
            }
            for (int j = helpIdx - 1; j >= 0; j--) {
                parent[help[j]] = i;
            }
            return i;
        }

        /**
         * 空降岛屿的位置
         *
         * @param i
         * @param j
         * @return
         */
        public int connect(int i, int j) {
            int index = index(i, j);
            if (size[index] != 0) {
                //之前已经空降过这个点
                return sets;
            }
            sets++;
            parent[index] = index;
            size[index] = 1;
            union(i - 1, j, i, j);
            union(i + 1, j, i, j);
            union(i, j - 1, i, j);
            union(i, j + 1, i, j);
            return sets;
        }

    }

}
