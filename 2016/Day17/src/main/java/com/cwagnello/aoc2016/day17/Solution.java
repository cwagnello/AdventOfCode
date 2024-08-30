package com.cwagnello.aoc2016.day17;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Solution {

    private static final int ROW = 0;
    private static final int COLUMN = 1;
    private static final int GRID_LENGTH = 4;

    private static final Map<DIRECTION, int[]> directions = Map.of(
            DIRECTION.U, new int[]{-1, 0},
            DIRECTION.D, new int[]{1, 0},
            DIRECTION.L, new int[]{0, -1},
            DIRECTION.R, new int[]{0, 1}
    );

    public static void main(String[] args) {
        File file = new File("src/main/resources/input.txt");
        List<String> input = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                //assume input data is well formed
                input.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String salt = parse(input);
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(3, 3);
        System.out.println("2016 day 17 part 1: " + part1(start, end, salt));
        System.out.println("2016 day 17 part 2: " + part2(start, end, salt));
    }

    private static String part1(Coordinate start, Coordinate end, String salt) {
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(start));

        while (!queue.isEmpty()) {
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                State current = queue.remove();
                if (current.coordinate().equals(end)) {
                    return String.join("", current.path());
                }
                for (DIRECTION direction: getValidDirections(salt, current)) {
                    int dRow = directions.get(direction)[ROW] + current.row();
                    int dCol = directions.get(direction)[COLUMN] + current.column();
                    Coordinate nextCoord = new Coordinate(dRow, dCol);

                    if (isInBounds(dRow, dCol)) {
                        List<String> nextPath = new ArrayList<>(current.path());
                        nextPath.add(direction.toString());
                        queue.offer(new State(nextCoord, nextPath));
                    }
                }
            }
        }
        return "😦";
    }

    private static int part2(Coordinate start, Coordinate end, String salt) {
        List<String> paths = new ArrayList<>();
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(start));

        while (!queue.isEmpty()) {
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                State current = queue.remove();
                if (current.coordinate().equals(end)) {
                    paths.add(String.join("", current.path()));
                    continue;
                }
                for (DIRECTION direction : getValidDirections(salt, current)) {
                    int dRow = directions.get(direction)[ROW] + current.row();
                    int dCol = directions.get(direction)[COLUMN] + current.column();
                    Coordinate nextCoord = new Coordinate(dRow, dCol);

                    if (isInBounds(dRow, dCol)) {
                        List<String> nextPath = new ArrayList<>(current.path());
                        nextPath.add(direction.toString());
                        queue.offer(new State(nextCoord, nextPath));
                    }
                }
            }
        }
        return paths.stream().map(String::length).max(Integer::compareTo).get();
    }

    private static List<DIRECTION> getValidDirections(String salt, State state) {
        List<DIRECTION> validDirections = new ArrayList<>();
        String md5Hash = calculateMD5(salt + String.join("", state.path()));
        for (int i = 0; i < DIRECTION.values().length; i++) {
            if (md5Hash.charAt(i) >= 'b' && md5Hash.charAt(i) <= 'f') {
                validDirections.add(DIRECTION.values()[i]);
            }
        }
        return validDirections;
    }

    private static boolean isInBounds(int row, int column) {
        return row >= 0 && column >= 0 && row < GRID_LENGTH && column < GRID_LENGTH;
    }

    private static String parse(List<String> input) {
        return input.getFirst();
    }

    private static String calculateMD5(String saltedKey) {
        String pw = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = saltedKey.getBytes(StandardCharsets.UTF_8);
            messageDigest.update(bytes);
            byte[] output = new byte[16];
            messageDigest.digest(output, 0, 16);
            messageDigest.reset();
            pw = HexFormat.of().formatHex(output);
        }
        catch (NoSuchAlgorithmException | DigestException exception) {
            System.out.println("Error generating md5 hash: " + exception);
        }
        return pw;
    }

}
