package com.jason.study.algorithm.unionFind;

/**
 * 给你一个由'1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * <p>
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 * <p>
 * 此外，你可以假设该网格的四条边均被水包围。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：grid = {
 * {'1','1','1','1','0'},
 * {'1','1','0','1','0'},
 * {'1','1','0','0','0'},
 * {'0','0','0','0','0'}
 * }
 * 输出：1
 * 示例 2：
 * <p>
 * 输入：grid = {
 * {'1','1','0','0','0'},
 * {'1','1','0','0','0'},
 * {'0','0','1','0','0'},
 * {'0','0','0','1','1'}
 * }
 * 输出：3
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/number-of-islands
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class IslandNum {

    public static void main(String[] args) {
//        char[][] island = {
//                {'1', '1', '1', '1', '0'},
//                {'1', '1', '0', '1', '0'},
//                {'1', '1', '0', '0', '0'},
//                {'0', '0', '0', '0', '0'}
//        };

//        char[][] island = {
//                {'1', '1', '0', '0', '0'},
//                {'1', '1', '0', '0', '0'},
//                {'0', '0', '1', '0', '0'},
//                {'0', '0', '0', '1', '1'}
//        };

        char[][] island = {
                {'1','1','1'},
                {'0','1','0'},
                {'1','1','1'}
        };

        System.out.println(numIslands2(island));
    }

    public static int numIslands1(char[][] grid) {
        //深度优先
        int r = grid.length;
        int c = grid[0].length;
        int ans = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == '1') {
                    ans++;
                    process(grid, i, j);
                }
            }
        }
        return ans;
    }

    private static void process(char[][] grid, int i, int j) {
        //判断是否越界，是否需要处理
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != '1') {
            return;
        }
        grid[i][j] = '0';
        process(grid, i - 1, j);
        process(grid, i + 1, j);
        process(grid, i, j - 1);
        process(grid, i, j + 1);
    }

    //并查集
    public static int numIslands2(char[][] grid) {
        //并查集 遍历数组，当前元素为'1'的时候 总大小+1，然后查看上、左两个元素是否为1，为1时进行合并，总岛屿数-1
        int r = grid.length;
        int c = grid[0].length;
        UnionFind find = new UnionFind(r * c);

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == '1') {
                    find.sets++;
                    if (i > 0 && grid[i - 1][j] == '1') {
                        find.union(i * c + j, (i - 1) * c + j);
                    }
                    if (j > 0 && grid[i][j - 1] == '1') {
                        find.union(i * c + j, i * c + j - 1);
                    }
                }
            }
        }
        return find.sets;
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

        public UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            help = new int[n];
            sets = 0;
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
