package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {

        File file = new File("src/main/resources/input.txt");
        List<String> input = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                //assume input data is well formed
                input.add(line);
            }
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String doorId = input.getFirst();
        System.out.println("2016 day 05 part 1: " + part1(doorId));
        System.out.println("2016 day 05 part 2: " + part2(doorId));
    }

    private static String part1(String input) {
        String complete = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            int index = 0;
            while (complete.length() < 8) {
                String toHash = input + index;
                byte[] bytes = toHash.getBytes(StandardCharsets.UTF_8);
                messageDigest.update(bytes);
                byte[] output = new byte[16];
                messageDigest.digest(output, 0, 16);
                String pw = HexFormat.of().formatHex(output);
                if (pw.startsWith("00000")) {
                    complete += pw.substring(5, 6);
                }
                messageDigest.reset();
                index++;
            }
        }
        catch (NoSuchAlgorithmException | DigestException exception) {
            System.out.println("Error generating md5 hash: " + exception);
        }
        return complete;
    }

    private static String part2(String input) {
        String[] complete = new String[8];
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            int index = 0;
            Set<Integer> foundIndexes = new HashSet<>();

            while (foundIndexes.size() < 8) {
                String toHash = input + index;
                byte[] bytes = toHash.getBytes(StandardCharsets.UTF_8);
                messageDigest.update(bytes);
                byte[] output = new byte[16];
                messageDigest.digest(output, 0, 16);
                String pw = HexFormat.of().formatHex(output);
                if (pw.startsWith("00000") && pw.substring(5, 6).matches("[0-7]")) {
                    int newIndex = Integer.parseInt(pw.substring(5, 6));
                    if (!foundIndexes.contains(newIndex)) {
                        foundIndexes.add(newIndex);
                        complete[newIndex] = pw.substring(6, 7);
                    }
                }
                messageDigest.reset();
                index++;
            }
        }
        catch (NoSuchAlgorithmException | DigestException exception) {
            System.out.println("Error generating md5 hash: " + exception);
        }
        return String.join("", complete);
    }

}
