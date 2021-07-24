package com.jason.study.algorithm.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * https://leetcode-cn.com/problems/falling-squares/
 * 落方块问题--max
 * <p>
 * 第 i 个掉落的方块（positions[i] = (left, side_length)）是正方形，其中left 表示该方块最左边的点位置(positions[i][0])，side_length 表示该方块的边长(positions[i][1])。
 * <p>
 * 每个方块的底部边缘平行于数轴（即 x 轴），并且从一个比目前所有的落地方块更高的高度掉落而下。在上一个方块结束掉落，并保持静止后，才开始掉落新方块。
 * <p>
 * 方块的底边具有非常大的粘性，并将保持固定在它们所接触的任何长度表面上（无论是数轴还是其他方块）。邻接掉落的边不会过早地粘合在一起，因为只有底边才具有粘性。
 * <p>
 * <p>
 * <p>
 * 返回一个堆叠高度列表ans 。每一个堆叠高度ans[i]表示在通过positions[0], positions[1], ..., positions[i]表示的方块掉落结束后，目前所有已经落稳的方块堆叠的最高高度。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/falling-squares
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class SegmentTree_02 {
    public static void main(String[] args) {
        int[][] arr = {{46, 18}, {34, 1}, {77, 40}, {38, 90}, {16, 5}, {1, 11}, {70, 79}, {2, 56}, {67, 14}, {19, 67}};
        List<Integer> list = new SegmentTree_02().fallingSquares(arr);
        list.stream().forEach(System.out::println);
    }


    //把下落的点，映射到一个连续的区间内，节省空间
    public HashMap<Integer, Integer> index(int[][] positions) {
        //存放所有的方块的起始位置和结束位置
        TreeSet<Integer> pos = new TreeSet<>();
        for (int[] cube : positions) {
            pos.add(cube[0]);//起始位置
            pos.add(cube[0] + cube[1] - 1);//结束位置
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        //将这些点映射到0~pos.size的区间内，节省空间
        for (Integer index : pos) {
            map.put(index, ++count);
        }
        return map;
    }

    public List<Integer> fallingSquares(int[][] positions) {
        List<Integer> ans = new ArrayList<>();
        if (positions == null) {
            return ans;
        }
        int globalMax = 0;
        //所有方块起点终点映射到的点阵
        HashMap<Integer, Integer> pointers = index(positions);
        int pointCount = pointers.size();
        SegmentTree segmentTree = new SegmentTree(pointCount);
        for (int[] cube : positions) {
            //找到映射的点位
            Integer left = pointers.get(cube[0]);
            Integer right = pointers.get(cube[0] + cube[1] - 1);
            //找到范围内的最高点【未落下之前】
            int start = segmentTree.query(left, right, 1, pointCount, 1);
            int top = start + cube[1];
            //落下
            segmentTree.update(left, right, top, 1, pointCount, 1);
            //统计高度
            globalMax = Math.max(globalMax, top);
            ans.add(globalMax);
        }
        return ans;
    }

    //只处理update的线段树
    private class SegmentTree {
        private int[] max;
        private int[] change;
        private boolean[] update;

        public SegmentTree(int pointCount) {
            int len = pointCount + 1;
            max = new int[len << 2];
            change = new int[len << 2];
            update = new boolean[len << 2];
        }

        public int query(int L, int R, int l, int r, int rt) {
            if (L <= l && r <= R) {
                return max[rt];
            }
            //没包住，需要下发
            int mid = (r + l) >> 1;
            //需要先把懒缓存处理下去，再处理子任务
            pushDown(rt, mid - l + 1, r - mid);
            //任务下发
            int leftMax = Integer.MIN_VALUE;
            if (L <= mid) {
                leftMax = query(L, R, l, mid, rt << 1);
            }
            int rightMax = Integer.MIN_VALUE;
            if (R > mid) {
                rightMax = query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return Math.max(leftMax, rightMax);
        }

        public void update(int L, int R, int C, int l, int r, int rt) {
            //包住了，懒缓存
            if (l >= L && r <= R) {
                update[rt] = true;
                change[rt] = C;
                max[rt] = C;
                //需要删除之前的加缓存【覆盖操作】
                return;
            }
            //没包住，需要下发
            int mid = (r + l) >> 1;
            //需要先把懒缓存处理下去，再处理子任务
            pushDown(rt, mid - l + 1, r - mid);
            //缓存处理完了，处理子任务
            //任务下发
            if (L <= mid) {
                update(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            //任务处理完了，处理本节点下的最大值
            pushUp(rt);
        }

        private void pushUp(int rt) {
            max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
        }

        private void pushDown(int rt, int ln, int rn) {
            //把之前的任务往下发
            if (update[rt]) {
                int val = change[rt];
                update[rt] = false;
                change[rt] = 0;

                update[rt << 1] = true;
                update[rt << 1 | 1] = true;

                change[rt << 1] = val;
                change[rt << 1 | 1] = val;

                max[rt << 1] = val;
                max[rt << 1 | 1] = val;

            }
        }
    }
}
