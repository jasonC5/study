package com.jason.study.algorithm.unionFind;

/**
 * 给定一个二维数组，两两之间是朋友则为1，否则为0
 * 返回一共有多少个朋友圈（相互之间有人认识，就算一个朋友圈）
 *
 * @author JasonC5
 */
public class FriendCircles {

    public static void main(String[] args) {
        int[][] M =
                {{1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                        {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                        {0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                        {0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0},
                        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1},
                        {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1}};
        int circleNum = findCircleNum(M);
        System.out.println(circleNum);
    }

    public static int findCircleNum(int[][] M) {
        if (M == null || M.length == 0) {
            return 0;
        }
        int length = M.length;
        UnionFind unionFind = new UnionFind(length);
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (M[i][j] == 1) {
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.sets;
    }


    static class UnionFind {
        //代表节点下标
        int[] parent;
        //代表节点集合大小
        int[] size;
        //压缩继承链用的辅助记录数组
        int[] help;
        //一共有多少个朋友圈
        int sets;

        public UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            help = new int[n];
            sets = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public void union(int i, int j) {
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
    }


}
