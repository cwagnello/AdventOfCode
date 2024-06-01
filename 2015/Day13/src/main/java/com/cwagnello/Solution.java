package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        System.out.println("2015 Day13 - part1: " + part1(input));
        System.out.println("2015 Day13 - part2: " + part2(input));
    }

    private static int part1(List<String> input) {
        Map<String, Integer> names = getUniqueNames(input);
        int n = names.size();
        int[][] adjacencyMatrix = buildAdjacencyMatrix(input, names, n);

        List<int[]> paths = generatePermutations(n, new ArrayList<>());

        int max = Integer.MIN_VALUE;
        for (int[] path : paths) {
            max = Math.max (max, calculateSum(path, adjacencyMatrix));
        }
        return max;
    }

    private static int part2(List<String> input) {
        Map<String, Integer> names = getUniqueNames(input);
        names.put("me", names.size());
        int n = names.size();
        int[][] adjacencyMatrix = buildAdjacencyMatrix(input, names, n);

        List<int[]> paths = generatePermutations(n, new ArrayList<>());

        int max = Integer.MIN_VALUE;
        for (int[] path : paths) {
            max = Math.max (max, calculateSum(path, adjacencyMatrix));
        }
        return max;
    }

    private static int calculateSum(int[] path, int[][] adjacencyMatrix) {
        int sum = adjacencyMatrix[path[0]][path[adjacencyMatrix.length - 1]];
        sum += adjacencyMatrix[path[adjacencyMatrix.length - 1]][path[0]];

        for (int i = 0; i < path.length - 1; i++) {
            sum += adjacencyMatrix[path[i]][path[i + 1]];
            sum += adjacencyMatrix[path[i + 1]][path[i]];
        }
        return sum;
    }

    private static List<int[]> generatePermutations(int n, List<int[]> paths) {
        Set<Integer> visited = new HashSet<>();
        int[] nodes = new int[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = i;
        }
        int[] branch = new int[n];
        permutations(n, nodes, branch, -1, visited, paths);
        return paths;
    }

    private static void permutations(int n, int[] nodes, int[] branch, int level, Set<Integer> visited, List<int[]> paths) {
        //Got this algorithm from https://exceptional-code.blogspot.com/2012/09/generating-all-permutations.html
        if (level >= n - 1) {
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

    private static Map<String, Integer> getUniqueNames(List<String> input) {
        Map<String, Integer> names = new HashMap<>();
        int i = 0;
        for (String line : input) {
            String[] parts = line.split("\\s+");
            if (names.containsKey(parts[0])) {
                continue;
            }
            names.put(parts[0], i);
            i++;
        }
        return names;
    }

    private static int[][] buildAdjacencyMatrix(List<String> input, Map<String,Integer> names, int n) {
        int[][] adjacencyMatrix = new int[n][n];
        Pattern pattern = Pattern.compile("^(\\w+)\\s+.*?(gain|lose)\\s+(\\d+).*?\\s+(\\w+)\\.$");
        for (int i = 0; i < input.size(); i++) {
            Matcher matcher = pattern.matcher(input.get(i));
            if (matcher.find()) {
                String firstPerson = matcher.group(1);
                String secondPerson = matcher.group(4);
                String gainOrLose = matcher.group(2);
                int happiness = Integer.parseInt(matcher.group(3));
                adjacencyMatrix[names.get(firstPerson)][names.get(secondPerson)] = "gain".equals(gainOrLose) ? happiness : -1 * happiness;
            }
        }
        return adjacencyMatrix;
    }

}
