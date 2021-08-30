package com.jason.study.algorithmQuestions.class35;

import java.util.PriorityQueue;

// 来自网易
// map[i][j] == 0，代表(i,j)是海洋，渡过的话代价是2
// map[i][j] == 1，代表(i,j)是陆地，渡过的话代价是1
// map[i][j] == 2，代表(i,j)是障碍，无法渡过
// 每一步上、下、左、右都能走，返回从左上角走到右下角最小代价是多少，如果无法到达返回-1
public class Code04_WalkToEnd {

    public static class Node {
        public int row;
        public int col;
        public int cost;

        public Node(int row, int col, int cost) {
            this.row = row;
            this.col = col;
            this.cost = cost;
        }
    }

    public static int minCost(int[][] map) {
        if (map[0][0] == 2) {
            return -1;
        }
        int row = map.length;
        int col = map[0].length;
        boolean[][] visited = new boolean[row][col];
        PriorityQueue<Node> heap = new PriorityQueue<Node>((n1, n2) -> n1.cost - n2.cost);
        add(heap, 0, 0, 0, map, visited);
        while (!heap.isEmpty()) {
            Node cur = heap.poll();
            if (cur.col == row - 1 && cur.col == col - 1) {
                return cur.cost;
            }
            add(heap, cur.row - 1, cur.col, cur.cost, map, visited);
            add(heap, cur.row + 1, cur.col, cur.cost, map, visited);
            add(heap, cur.row, cur.col - 1, cur.cost, map, visited);
            add(heap, cur.row, cur.col + 1, cur.cost, map, visited);
        }
        return -1;
    }

    private static void add(PriorityQueue<Node> heap, int i, int j, int preCost, int[][] map, boolean[][] visited) {
        //没来过，且不能越界
        if (!visited[i][j] && map[i][j] != 2 && i != -1 && i != map.length && j != -1 & j != map[0].length) {
            heap.offer(new Node(i, j, preCost + (map[i][j]) == 0 ? 2 : 1));
            visited[i][j] = true;
        }
    }
}
