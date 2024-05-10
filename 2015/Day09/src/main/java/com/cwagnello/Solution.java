package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        File file = new File("src/main/resources/input.txt");
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

        int n = getUniqueNames(input);
        int[][] matrix = buildAdjacencyMatrix(input, n);

        Set<Integer> visited = new HashSet<>();
        int[] nodes = new int[matrix.length];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = i;
        }
        int[] branch = new int[matrix.length];
        List<int[]> paths = new ArrayList<>();
        permutations(matrix.length, nodes, branch, -1, visited, paths);

        System.out.println("2015 Day09 - part1: " + part1(matrix, paths));
        System.out.println("2015 Day09 - part2: " + part2(matrix, paths));
    }

    private static int part1(int[][] matrix, List<int[]> paths) {
        int min = Integer.MAX_VALUE;
        for (int[] path : paths) {
            min = Math.min (min, calculateSum(path, matrix));
        }
        return min;
    }

    private static int part2(int[][] matrix, List<int[]> paths) {
        int max = Integer.MIN_VALUE;
        for (int[] path : paths) {
            max = Math.max (max, calculateSum(path, matrix));
        }
        return max;
    }

    private static int calculateSum(int[] path, int[][] grid) {
        int sum = 0;
        for (int i = 0; i < path.length - 1; i++) {
            sum += grid[path[i]][path[i + 1]];
        }
        return sum;
    }

    private static void permutations(int n, int[] nodes, int[] branch, int level, Set<Integer> visited, List<int[]> paths) {
        //Got this algorithm from https://exceptional-code.blogspot.com/2012/09/generating-all-permutations.html
        if (level >= n - 1) {
            //printArray(branch);
            paths.add(Arrays.copyOf(branch, branch.length));
        }
        for (int i = 0; i < n; i++) {
            if (!visited.contains(i)) {
                branch[++level] = nodes[i];
                visited.add(i);
                permutations(n, nodes, branch, level, visited, paths);
                visited.remove(i);
                level--;
            }
        }
    }

    private static void printArray(int[] array) {
        System.out.println(String.join(",", Arrays.toString(array)));
    }
    private static int getUniqueNames(List<String> input) {
        Set<String> names = new HashSet<>();
        for (String line : input) {
            String[] parts = line.split("\\s+");
            names.add(parts[0]);
            names.add(parts[2]);
        }
        return names.size();
    }

    private static int[][] buildAdjacencyMatrix(List<String> input, int n) {
        int index = 0;
        int[][] graph = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (i == j) {
                    continue;
                }
                String[] parsedLine = input.get(index).split("\\s+=\\s+");
                graph[i][j] = Integer.parseInt(parsedLine[1]);
                graph[j][i] = Integer.parseInt(parsedLine[1]);
                index++;
            }
        }
        return graph;
    }
}