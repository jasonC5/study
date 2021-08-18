package com.jason.study.algorithmQuestions.class33;

import java.util.*;

/**
 * 
 * 你这个学期必须选修 numCourses 门课程，记为   0   到   numCourses - 1 。
 *
 * 在选修某些课程之前需要一些先修课程。 先修课程按数组   prerequisites 给出，其中   prerequisites[i] = [ai, bi] ，表示如果要学习课程   ai 则 必须 先学习课程    bi 。
 *
 * 例如，先修课程对   [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
 * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
 *
 *    
 *
 * 示例 1：
 *
 * 输入：numCourses = 2, prerequisites = [[1,0]]
 * 输出：true
 * 解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
 * 示例 2：
 *
 * 输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
 * 输出：false
 * 解释：总共有 2 门课程。学习课程 1 之前，你需要先完成   课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/course-schedule
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author JasonC5
 */
public class LC207 {

    public static class Node{
        public int name;
        public int in;
        public List<Node> nexts;

        public Node(int name) {
            this.name = name;
            in = 0;
            nexts = new ArrayList<>();
        }
    }
    
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //只要不循环依赖，就能完成
        //拓扑排序
        if (prerequisites == null || prerequisites.length == 0) {
            //没有依赖关系，直接成功
            return true;
        }
        Map<Integer, Node> nodeMap = new HashMap<>();
        for (int[] arr : prerequisites) {
            int from = arr[1];
            int to = arr[0];
            Node fromNode = nodeMap.getOrDefault(from, new Node(from));
            Node toNode = nodeMap.getOrDefault(to, new Node(to));
            fromNode.nexts.add(toNode);
            //入度++
            toNode.in++;
            nodeMap.put(from, fromNode);
            nodeMap.put(to, toNode);
        }
        Queue<Node> queue = new LinkedList<>();
        int count = 0;
        for (Node node : nodeMap.values()) {
            if (node.in == 0){
                queue.add(node);
                count++;//入度为0
            }
        }
        while(!queue.isEmpty()){
            Node node = queue.poll();
            for (Node next : node.nexts) {
                if (--next.in == 0){
                    queue.add(next);
                    count++;//入度为0
                }
            }
        }
        return count == nodeMap.size();
    }
}
