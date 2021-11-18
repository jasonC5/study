package com.jason.study.algorithmQuestions.class33;

import java.util.*;

/**
 * 现在你总共有 n 门课需要选，记为 0 到 n-1。
 * <p>
 * 在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们: [0,1]
 * <p>
 * 给定课程总量以及它们的先决条件，返回你为了学完所有课程所安排的学习顺序。
 * <p>
 * 可能会有多个正确的顺序，你只要返回一种就可以了。如果不可能完成所有课程，返回一个空数组。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 2, [[1,0]]
 * 输出: [0,1]
 * 解释: 总共有 2 门课程。要学习课程 1，你需要先完成课程 0。因此，正确的课程顺序为 [0,1] 。
 * 示例 2:
 * <p>
 * 输入: 4, [[1,0],[2,0],[3,1],[3,2]]
 * 输出: [0,1,2,3] or [0,2,1,3]
 * 解释: 总共有 4 门课程。要学习课程 3，你应该先完成课程 1 和课程 2。并且课程 1 和课程 2 都应该排在课程 0 之后。
 * 因此，一个正确的课程顺序是 [0,1,2,3] 。另一个正确的排序是 [0,2,1,3] 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/course-schedule-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC210 {
    public static class Node {
        public int name;
        public int in;
        public List<Node> nexts;

        public Node(int name) {
            this.name = name;
            in = 0;
            nexts = new ArrayList<>();
        }
    }

    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        //只要不循环依赖，就能完成
        //拓扑排序
//        if (prerequisites == null || prerequisites.length == 0) {
//            //没有依赖关系，直接成功
//            return new int[]{0};
//        }
        Map<Integer, Node> nodeMap = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            nodeMap.put(i, new Node(i));
        }
        for (int[] arr : prerequisites) {
            int from = arr[1];
            int to = arr[0];
            Node fromNode = nodeMap.get(from);
            Node toNode = nodeMap.get(to);
            fromNode.nexts.add(toNode);
            //入度++
            toNode.in++;
            nodeMap.put(from, fromNode);
            nodeMap.put(to, toNode);
        }
        int[] ans = new int[numCourses];
        boolean[] flag = new boolean[numCourses];
        Queue<Node> queue = new LinkedList<>();
        int count = 0;
        for (Node node : nodeMap.values()) {
            if (node.in == 0) {
                queue.add(node);
                flag[node.name] = true;
                ans[count++] = node.name;//入度为0
            }
        }
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            for (Node next : node.nexts) {
                if (--next.in == 0) {
                    queue.add(next);
                    flag[next.name] = true;
                    ans[count++] = next.name;//入度为0
                }
            }
        }
        if (count != numCourses) {
            return new int[]{};
        } else {
            return ans;
        }
    }

    public static void main(String[] args) {
//        int[][] arr = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
//        int k = 4;
        int[][] arr = {};
        int k = 1;
        int[] order = findOrder(k, arr);
        Arrays.stream(order).boxed().forEach(x -> System.out.print(x + " "));
    }
}
