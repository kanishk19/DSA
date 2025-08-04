package interview.question.topics.graph.med_keys_and_rooms;

import java.util.*;

//https://leetcode.com/problems/keys-and-rooms/\
public class Solution {

    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        if(rooms == null || rooms.isEmpty())
            return true;

        Set<Integer> visited = new HashSet<>();
//        measurePerformance(0, rooms, visited);
        traverseGraph(0, rooms, visited);
        return (visited.size() == rooms.size());
    }

    private void traverseGraph(int source, List<List<Integer>> rooms, Set<Integer> visited){
        visited.add(source);
        traverseGraphDFS(source, rooms, visited);
    }

//    private void measurePerformance(int source, List<List<Integer>> rooms, Set<Integer> visited){
//        // Measure DFS time
//        long startDFS = System.nanoTime();
//        traverseGraphDFS(0, rooms, visited);
//        long endDFS = System.nanoTime();
//        long dfsTime = endDFS - startDFS;
//
//        System.out.println("DFS Execution Time: " + dfsTime + " ns");
//
//        // Measure BFS time
//        visited.clear(); // Reset the visited set
//        long startBFS = System.nanoTime();
//        traverseGraphBFS(0, rooms, visited);
//        long endBFS = System.nanoTime();
//        long bfsTime = endBFS - startBFS;
//
//        System.out.println("BFS Execution Time: " + bfsTime + " ns");
//        visited.clear();
//    }

    private void traverseGraphBFS(int source, List<List<Integer>> rooms, Set<Integer> visited){
        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(source);
        visited.add(source);

        while(!queue.isEmpty()){
            int currRoom = queue.poll();
            List<Integer> neighborRooms = rooms.get(currRoom);

            for(int room: neighborRooms){
                if(visited.contains(room))
                    continue;
                visited.add(room);
                queue.add(room);
            }
        }
    }

    private void traverseGraphDFS(int source, List<List<Integer>> rooms, Set<Integer> visited){
        List<Integer> neighborRooms = rooms.get(source);
        for(int neighbor: neighborRooms){
            if(visited.contains(neighbor))
                continue;
            visited.add(neighbor);
            traverseGraphDFS(neighbor, rooms, visited);
        }
    }
}
