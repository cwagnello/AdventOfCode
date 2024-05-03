package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Solution {
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

        System.out.println("Day9 Part1: "  + part1(input));
        System.out.println("Day9 Part2: "  + part2(input));
    }

    private static int part1(List<String> input) {
        int sum = 0;
        for (String line: input) {
            List<List<Integer>> data = new ArrayList<>();
            List<Integer> numbers = new ArrayList<>(Arrays.stream(line.split("\\s+")).map(Integer::parseInt).toList());
            data.add(numbers);

            boolean isNonZero = true;
            while (isNonZero) {
                List<Integer> diff = new ArrayList<>();
                int i = 1;
                for (i = 1; i < numbers.size(); i++) {
                    diff.add(numbers.get(i) - numbers.get(i - 1));
                }
                if (!isAllZeroes(diff)) {
                    data.add(diff);
                    numbers = data.get(data.size() - 1);
                }
                else {
                    isNonZero = false;
                }
            }
            for (int i = data.size() - 1; i > 0; i--) {
                int lastElementLastRow = data.get(i).get(data.get(i).size() - 1);
                int lastElementPrevRow = data.get(i - 1).get(data.get(i - 1).size() - 1);
                data.get(i - 1).add(lastElementLastRow + lastElementPrevRow);
            }
            sum += data.get(0).get(data.get(0).size() - 1);
        }
        return sum;
    }

    private static int part2(List<String> input) {
        int sum = 0;
        for (String line: input) {
            Deque<Deque<Integer>> data = new LinkedList<>();
            List<Integer> numbers = new ArrayList<>(Arrays.stream(line.split("\\s+")).map(Integer::parseInt).toList());
            data.add(new LinkedList<>(numbers));

            boolean isNonZero = true;
            while (isNonZero) {
                List<Integer> diff = new ArrayList<>();
                int i = 1;
                for (i = 1; i < numbers.size(); i++) {
                    diff.add(numbers.get(i) - numbers.get(i - 1));
                }
                if (!isAllZeroes(diff)) {
                    data.add(new LinkedList<>(diff));
                    numbers = new LinkedList<>(data.getLast());
                }
                else {
                    isNonZero = false;
                }
            }
            int firstElementLastRow = data.getLast().getFirst();
            Iterator<Deque<Integer>> iterator = data.descendingIterator();
            iterator.next();
            while (iterator.hasNext()) {
                firstElementLastRow = (Integer)((LinkedList)iterator.next()).getFirst() - firstElementLastRow;
            }
            sum += firstElementLastRow;
        }
        return sum;
    }
    private static boolean isAllZeroes(List<Integer> input) {
        for (int n : input) {
            if (n != 0) {
                return false;
            }
        }
        return true;
    }
}
