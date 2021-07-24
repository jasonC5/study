package com.jason.study.algorithm.other;

/**
 * 线段树01--实现
 */
public class SegmentTree_01 {

    public static class SegmentTree {
        //传进来的原数组，但是从1开始
        private int[] arr;
        //arr的长度
        private int len;
        //线段树的区间和数组
        private int[] sum;
        //累加 懒加载缓存
        private int[] lazy;
        //修改 懒加载缓存
        private int[] change;
        //修改 懒加载标记
        private boolean[] update;

        public SegmentTree(int[] source) {
            this.len = source.length + 1;
            this.arr = new int[len];
            for (int i = 1; i <= source.length; i++) {
                this.arr[i] = source[i - 1];
            }
            //4 * len，够用
            sum = new int[len << 2]; // 用来支持脑补概念中，某一个范围的累加和信息
            lazy = new int[len << 2]; // 用来支持脑补概念中，某一个范围沒有往下傳遞的纍加任務
            change = new int[len << 2]; // 用来支持脑补概念中，某一个范围有没有更新操作的任务
            update = new boolean[len << 2]; // 用来支持脑补概念中，某一个范围更新任务，更新成了什么
        }

        public void build(int left, int right, int rt) {
            if (left == right) {
                sum[rt] = arr[left];
                return;
            }
            //下推子任务赋值
            int mid = (left + right) >> 1;
            build(left, mid, rt << 1);
            build(mid + 1, right, rt << 1 | 1);
            //赋值完成，求和
            pushUp(rt);
        }


        // L~R  所有的值变成C
        // l~r  rt
        public void update(int L, int R, int C, int l, int r, int rt) {
            //包住了，懒缓存
            if (l >= L && r <= R) {
                sum[rt] = C * (r - l + 1);
                update[rt] = true;
                change[rt] = C;
                //需要删除之前的加缓存【覆盖操作】
                lazy[rt] = 0;
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
            //任务处理完了，求本届点的和
            pushUp(rt);
        }

        // L~R, C 任务！
        // rt，l~r
        public void add(int L, int R, int C, int l, int r, int rt) {
            //L到R范围内，全部加上C， 当前任务范围：从l,到r，当前任务根节点-rt  --root
            //全包住了，懒缓存
            if (l >= L && r <= R) {
                sum[rt] += C * (r - l + 1);
                lazy[rt] += C;
                return;
            }
            //没包住，需要下发
            int mid = (r + l) >> 1;
            //需要先把懒缓存处理下去，再处理子任务
            pushDown(rt, mid - l + 1, r - mid);
            //任务下发
            if (L <= mid) {
                add(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                add(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            //任务处理完了，求本届点的和
            pushUp(rt);
        }

        private void pushUp(int rt) {
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        private void pushDown(int rt, int ln, int rn) {
            //处理update缓存
            if (update[rt]) {
                //往下发
                int updateVal = change[rt];
                update[rt] = false;
                change[rt] = 0;
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                change[rt << 1] = updateVal;
                change[rt << 1 | 1] = updateVal;
                sum[rt << 1] = updateVal * ln;
                sum[rt << 1 | 1] = updateVal * rn;
                //里面的值都被覆盖了，累加值肯定得清空，否则会影响下次任务
                lazy[rt << 1] = 0;
                lazy[rt << 1 | 1] = 0;
            }
            //处理加缓存
            if (lazy[rt] != 0) {
                int addVal = lazy[rt];
                lazy[rt] = 0;
                lazy[rt << 1] += addVal;
                lazy[rt << 1 | 1] += addVal;
                sum[rt << 1] += addVal * ln;
                sum[rt << 1 | 1] += addVal * rn;
            }
        }

        // 1~6 累加和是多少？ 1~8 rt
        public long query(int L, int R, int l, int r, int rt) {
            if (l >= L && r <= R) {
                return sum[rt];
            }
            //没包住，需要下发
            long ans = 0L;
            int mid = l + ((r - l) >> 1);
            //马上要往下发任务了，懒缓存先处理，下推
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                ans += query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }
    }

//    public static void main(String[] args) {
//        int[] origin = {1, 2, 3, 4, 5};
//        SegmentTree seg = new SegmentTree(origin);
//        int S = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
//        int N = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
//        int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
//        // 区间生成，必须在[S,N]整个范围上build
//        seg.build(S, N, root);
//        Right rig = new Right(origin);
//        long sum,sum2;
//        // 区间修改，可以改变L、R和C的值，其他值不可改变
//        seg.update(1, 3, 1, S, N, root);
//        rig.update(1, 3, 1);
//        seg.update(2, 5, 2, S, N, root);
//        rig.update(2, 5, 2);
//        sum = seg.query(1, 5, S, N, root);
//        sum2 = rig.query(1, 5);
//        System.out.println(sum == sum2);
//
//        seg.add(1, 5, 1, S, N, root);
//        rig.add(1, 5, 1);
//        seg.add(2, 3, 1, S, N, root);
//        rig.add(2, 3, 1);
//        sum = seg.query(1, 5, S, N, root);
//        sum2 = rig.query(1, 5);
//        System.out.println(sum == sum2);
//
//        seg.update(1, 3, 1, S, N, root);
//        rig.update(1, 3, 1);
//        seg.add(2, 3, -1, S, N, root);
//        rig.add(2, 3, -1);
//        sum = seg.query(1, 5, S, N, root);
//        sum2 = rig.query(1, 5);
//        System.out.println(sum == sum2);
//
//        seg.add(2, 3, -1, S, N, root);
//        rig.add(2, 3, -1);
//        seg.update(1, 3, 1, S, N, root);
//        rig.update(1, 3, 1);
//        sum = seg.query(1, 5, S, N, root);
//        rig.query(1, 5);
//        sum2 = rig.query(1, 5);
//        System.out.println(sum == sum2);
//    }


    public static void main(String[] args) {
        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "complete" : "failed"));
    }

    //对数器
    public static boolean test() {
        int len = 10;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
            seg.build(S, N, root);
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C, S, N, root);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C, S, N, root);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R, S, N, root);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int[] genarateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    //暴力
    public static class Right {
        public int[] arr;

        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }
    }

}
