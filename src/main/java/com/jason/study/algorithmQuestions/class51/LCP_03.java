package com.jason.study.algorithmQuestions.class51;

import java.util.HashSet;
import java.util.Set;

/**
 * 力扣团队买了一个可编程机器人，机器人初始位置在原点(0, 0)。小伙伴事先给机器人输入一串指令command，机器人就会无限循环这条指令的步骤进行移动。指令有两种：
 * <p>
 * U: 向y轴正方向移动一格
 * R: 向x轴正方向移动一格。
 * 不幸的是，在 xy 平面上还有一些障碍物，他们的坐标用obstacles表示。机器人一旦碰到障碍物就会被损毁。
 * <p>
 * 给定终点坐标(x, y)，返回机器人能否完好地到达终点。如果能，返回true；否则返回false。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：command = "URR", obstacles = [], x = 3, y = 2
 * 输出：true
 * 解释：U(0, 1) -> R(1, 1) -> R(2, 1) -> U(2, 2) -> R(3, 2)。
 * 示例 2：
 * <p>
 * 输入：command = "URR", obstacles = [[2, 2]], x = 3, y = 2
 * 输出：false
 * 解释：机器人在到达终点前会碰到(2, 2)的障碍物。
 * 示例 3：
 * <p>
 * 输入：command = "URR", obstacles = [[4, 2]], x = 3, y = 2
 * 输出：true
 * 解释：到达终点后，再碰到障碍物也不影响返回结果。
 *  
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/programmable-robot
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LCP_03 {
    public static boolean robot(String command, int[][] obstacles, int x, int y) {
        int length = command.length();
        int[][] path = new int[length][2];
        char[] chars = command.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int[] point = path[i];
            int[] pre = i == 0 ? new int[]{0, 0} : path[i - 1];
            if (chars[i] == 'U') {
                point[0] = pre[0];
                point[1] = pre[1] + 1;
            } else {
                point[0] = pre[0] + 1;
                point[1] = pre[1];
            }
        }
        //是否能到达目的地
        if (!passing(path, x, y)) {
            return false;
        }
        // 中间是否会碰到障碍
        for (int[] obstacle : obstacles) {
            if (obstacle.length == 0 || obstacle[0] > x || obstacle[1] > y) {
                continue;
            }
            if (passing(path, obstacle[0], obstacle[1])) {
                return false;
            }
        }
        return true;
    }

    public static boolean passing(int[][] path, int x, int y) {
        int length = path.length;
        int count = Math.min(x / path[length - 1][0], y / path[length - 1][1]);
        if (path[length - 1][0] * count == x && path[length - 1][1] * count == y) {
            return true;
        }
        int xStart = path[length - 1][0] * count, yStart = path[length - 1][1] * count;
        for (int[] route : path) {
            if (xStart + route[0] == x && yStart + route[1] == y) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String command = "URR";
        int[][] obstacles = new int[][]{{4, 2}};
        int x = 3, y = 2;
        System.out.println(robot(command, obstacles, x, y));
        System.out.println(robot2(command, obstacles, x, y));
    }

    // TODO 可以改成二进制版本
    public static boolean robot2(String command, int[][] obstacles, int x, int y) {
        char[] chars = command.toCharArray();
        Set<Integer> cache = new HashSet<>();
        cache.add(0);
        int pointX = 0, pointY = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == 'U') {
                pointY += 1;
            } else {
                pointX += 1;
            }
            int point = pointX << 10 | pointY;
            cache.add(point);
        }
        //是否能到达目的地
        if (!meet(cache, pointX, pointY, x, y)) {
            return false;
        }
        // 中间是否会碰到障碍
        for (int[] obstacle : obstacles) {
            if (obstacle.length > 0 && obstacle[0] <= x && obstacle[1] <= y && meet(cache, pointX, pointY, obstacle[0], obstacle[1])) {
                return false;
            }
        }
        return true;
    }

    private static boolean meet(Set<Integer> cache, int pointX, int pointY, int x, int y) {
        int count = Math.min(x / pointX, y / pointY);
        int offsetX = x - count * pointX;
        int offsetY = y - count * pointY;
        return cache.contains(offsetX << 10 | offsetY);
    }


}
