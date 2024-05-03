package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Solution {
    final static int[][] directions = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};

    public static void main(String[] args) {
        File file = new File("src/com/cwagnello/input.txt");
        List<String> input = new ArrayList<>();
        try (
                Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                //assume input data is well formed
                input.add(line);
            }
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        char[][] grid = parseInput(input);
        // System.out.println("Day 23 - Part 1: " + (part1(grid) - 1));

        List<Node> junctions = new ArrayList<>();
        findJunctions(grid, start(grid), junctions);
        calculateDistances(grid, junctions, start(grid), end(grid));
        long startTime = System.currentTimeMillis();
        System.out.println("Day 23 - Part 2: " + part2(junctions, start(grid), end(grid)));
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
    }

    public static long part1(char[][] grid) {
        Set<Node> visited = new HashSet<>();
        Node start = start(grid);
        long answer = dfs(grid, start, end(grid), visited, new int[]{0 ,1});

        return answer;
    }

    public static long part2(List<Node> junctions, Node start, Node end) {
        Set<Node> visited = new HashSet<>();
        return dfs2(junctions, start, end, visited);
    }
    private static void findJunctions(char[][] grid, Node start, List<Node> junctions) {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(start);
        Set<Node> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Node current = queue.remove();
            if (!isInBounds(grid, current.row, current.col) || isArock(grid, current) || visited.contains(current)) {
                continue;
            }
            visited.add(current);
            int countEdges = 0;
            for (int[] direction: directions) {
                int dRow = direction[0] + current.row;
                int dCol = direction[1] + current.col;
                Node next = new Node(dRow, dCol);
                if (isInBounds(grid, next.row, next.col) && !isArock(grid, next)) {
                    countEdges++;
                }
                queue.offer(next);
            }
            if (countEdges > 2) {
                junctions.add(current);
            }
        }
        //System.out.println("Junctions: " + junctions);
    }

    private static void calculateDistances(char[][] grid, List<Node> junctions, Node start, Node end) {
        NodeDistances distances = NodeDistances.getInstance();
        junctions.add(0, start);
        junctions.add(end);
        Queue<Node> queue = new LinkedList<>();

        for (Node from : junctions) {
            int steps = 0;
            Set<Node> visited = new HashSet<>();
            queue.offer(from);

            while (!queue.isEmpty()) {
                int size = queue.size();
                steps++;
                for (int i = 0; i < size; i++) {
                    Node current = queue.remove();
                    if (!isInBounds(grid, current.row, current.col) || isArock(grid, current) || visited.contains(current)) {
                        continue;
                    }
                    visited.add(current);

                    for (int[] direction : directions) {
                        int dRow = direction[0] + current.row;
                        int dCol = direction[1] + current.col;
                        Node next = new Node(dRow, dCol);
                        if (junctions.contains(next)) {
                            if (!next.equals(from)) {
                                distances.add(from, next, steps);
                            }
                        } else {
                            queue.offer(next);
                        }
                    }
                }
            }
        }
    }

    private static int dfs2(List<Node> junctions, Node start, Node end, Set<Node> visited) {
        int max = 0;
        if (visited.contains(start)) {
            return max;
        }
        visited.add(start);
        if (start.equals(end)) {
            visited.remove(start);
            int count = 0;
            Node current = start;
            while (current != null) {
                count += NodeDistances.getInstance().getDistance(current, current.prev);
                current = current.prev;
            }
            max = count;
            return max;
        }
        for (Node neighbor: NodeDistances.getInstance().getConnected(start)) {
            neighbor.prev = start;
            max = Math.max(max, dfs2(junctions, neighbor, end, visited));
        }

        visited.remove(start);
        return max;
    }

    private static boolean isArock(char[][] grid, Node current) {
        return grid[current.row][current.col] == '#';
    }

    private static int dfs(char[][] grid, Node start, Node end, Set<Node> visited, int[] dir) {
        int max = 0;
        if (!isInBounds(grid, start.row, start.col) || isArock(grid, start) || !isDirectionCorrect(grid, start, dir) || visited.contains(start)) {
            return max;
        }
        visited.add(start);
        if (start.equals(end)) {
            visited.remove(start);
            int count = 0;
            Node current = start;
            while (current != null) {
                current = current.prev;
                count++;
            }
//            for (char[] row: grid) {
//                System.out.println(new String(row));
//            }
            max = count;
            return max;
        }
        for (int[] direction: directions) {
            int dRow = direction[0] + start.row;
            int dCol = direction[1] + start.col;
            Node next = new Node(dRow, dCol);
            next.prev = start;
            max = Math.max(max, dfs(grid, next, end, visited, direction));
        }

        visited.remove(start);
        return max;
    }

    private static boolean isDirectionCorrect(char[][] grid, Node start, int[] dir) {
        if (grid[start.row][start.col] == '.') {
            return true;
        }
        if (dir[0] == 0 && dir[1] == 1 && grid[start.row][start.col] == '>') {
            return true;
        }
        if (dir[0] == 0 && dir[1] == -1 && grid[start.row][start.col] == '<') {
            return true;
        }
        if (dir[0] == 1 && dir[1] == 0 && grid[start.row][start.col] == 'v') {
            return true;
        }
        if (dir[0] == -1 && dir[1] == 0 && grid[start.row][start.col] == '^') {
            return true;
        }
        return false;
    }
    private static boolean isInBounds(char[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    private static Node start (char[][] grid) {
        for (int col = 0; col < grid[0].length; col++) {
            if (grid[0][col] == '.') {
                return new Node(0, col);
            }
        }
        throw new IllegalStateException("No start coordinates found");
    }

    private static Node end (char[][] grid) {
        int rowMax = grid.length - 1;
        for (int col = 0; col < grid[0].length; col++) {
            if (grid[rowMax][col] == '.') {
                return new Node(rowMax, col);
            }
        }
        throw new IllegalStateException("No end coordinates found");
    }

    public static char[][] parseInput(List<String> input) {
        char[][] grid = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            grid[i] = input.get(i).toCharArray();
        }
        return grid;
    }
}

class Node {
    int row;
    int col;
    Node prev;
    public Node(int row, int col, Node prev) {
        this.row = row;
        this.col = col;
        this.prev = prev;
    }

    public Node(int row, int col) {
        this(row, col, null);
    }

    public Node(Node node) {
        this(node.row, node.col, node.prev);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return row == node.row && col == node.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "(" + row +
                ", " + col + ')';
    }
}

class NodeDistances {
    private static Map<Node, Map<Node, Integer>> map;
    private static NodeDistances instance;
    private NodeDistances() {
    }
    public static NodeDistances getInstance() {
        if(instance == null) {
            instance = new NodeDistances();
            map = new HashMap<>();
        }
        return instance;
    }
    public int getDistance(Node from, Node to) {
        if (map.containsKey(from) && map.get(from).containsKey(to)) {
            return map.get(from).get(to);
        }
        else if (map.containsKey(to) && map.get(to).containsKey(from)) {
            return map.get(to).get(from);
        }
        return 0;
        //throw new IllegalStateException("Node(s) not found in map, from: " + from + ", to: " + to);
    }

    public void add(Node from, Node to, int distance) {
        if (!map.containsKey(from)) {
            map.put(from, new HashMap<>());
        }
        map.get(from).put(to, distance);
    }

    public Set<Node> getConnected(Node node) {
        return map.get(node).keySet();
    }

}