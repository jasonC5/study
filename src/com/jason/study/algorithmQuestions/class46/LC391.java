package com.jason.study.algorithmQuestions.class46;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我们有 N 个与坐标轴对齐的矩形, 其中 N > 0, 判断它们是否能精确地覆盖一个矩形区域。
 * <p>
 * 每个矩形用左下角的点和右上角的点的坐标来表示。例如，一个单位正方形可以表示为 [1,1,2,2]。( 左下角的点的坐标为 (1, 1) 以及右上角的点的坐标为 (2, 2) )。
 * <p>
 * <p>
 * <p>
 * 示例 1:
 * <p>
 * rectangles = [
 * [1,1,3,3],
 * [3,1,4,2],
 * [3,2,4,4],
 * [1,3,2,4],
 * [2,3,3,4]
 * ]
 * <p>
 * 返回 true。5个矩形一起可以精确地覆盖一个矩形区域。
 * <p>
 * <p>
 * <p>
 * <p>
 * 示例2:
 * <p>
 * rectangles = [
 * [1,1,2,3],
 * [1,3,2,4],
 * [3,1,4,2],
 * [3,2,4,4]
 * ]
 * <p>
 * 返回 false。两个矩形之间有间隔，无法覆盖成一个矩形。
 * <p>
 * <p>
 * <p>
 * <p>
 * 示例 3:
 * <p>
 * rectangles = [
 * [1,1,3,3],
 * [3,1,4,2],
 * [1,3,2,4],
 * [3,2,4,4]
 * ]
 * <p>
 * 返回 false。图形顶端留有间隔，无法覆盖成一个矩形。
 * <p>
 * <p>
 * <p>
 * <p>
 * 示例 4:
 * <p>
 * rectangles = [
 * [1,1,3,3],
 * [3,1,4,2],
 * [1,3,2,4],
 * [2,2,4,4]
 * ]
 * <p>
 * 返回 false。因为中间有相交区域，虽然形成了矩形，但不是精确覆盖。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/perfect-rectangle
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC391 {
    public boolean isRectangleCover(int[][] rectangles) {
        // 两个条件：
        // 1.所有矩形的面积相加 = 形成的外部矩形的面积
        // 2.四个顶点出现的次数 = 1 ，剩余所有的点出现的次数 = 偶数次
        long areaSum = 0;
        int x1 = Integer.MAX_VALUE, x2 = Integer.MIN_VALUE, y1 = Integer.MAX_VALUE, y2 = Integer.MIN_VALUE;
        Map<String, Integer> pointCount = new HashMap<>();
        for (int[] rectangle : rectangles) {
            int tx1 = rectangle[0], ty1 = rectangle[1], tx2 = rectangle[2], ty2 = rectangle[3];
            // 更新外边界
            x1 = Math.min(x1, tx1);
            x2 = Math.max(x2, tx2);
            y1 = Math.min(y1, ty1);
            y2 = Math.max(y2, ty2);
            // 面积
            areaSum += (long) (tx2 - tx1) * (ty2 - ty1);
            //四个点
            List<String> points = Arrays.asList(tx1 + "_" + ty1, tx1 + "_" + ty2, tx2 + "_" + ty1, tx2 + "_" + ty2);
            for (String point : points) {
                Integer times = pointCount.getOrDefault(point, 0);
                pointCount.put(point, times + 1);
            }
        }
        // 所有小矩形的面积==组成的大矩形
        if ((long) (x2 - x1) * (y2 - y1) != areaSum) {
            return false;
        }
        List<String> point = Arrays.asList(x1 + "_" + y1, x1 + "_" + y2, x2 + "_" + y1, x2 + "_" + y2);
        for (String p : point) {
            if (!pointCount.containsKey(p) || pointCount.get(p) != 1) {
                return false;
            }
            pointCount.remove(p);
        }
        for (int count : pointCount.values()) {
            if ((count & 1) == 1) {//出现奇数次，不符合要求
                return false;
            }
        }
        return true;
    }
}
