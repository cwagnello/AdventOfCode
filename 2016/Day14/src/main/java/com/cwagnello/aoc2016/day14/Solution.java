package com.cwagnello.aoc2016.day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    private static final Pattern THREE_IN_A_ROW = Pattern.compile("((.)\\2\\2)");

    public static void main(String[] args) {
        File file = new File("src/main/resources/input.txt");
        List<String> input = new ArrayList<>();
            try(Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                //assume input data is well formed
                input.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String salt = input.getFirst();
        System.out.println("2016 day 14 part 1: " + part1(salt));
        System.out.println("2016 day 14 part 2: " + part2(salt));
    }

    private static long part1(String salt) {
        List<String> characters = new ArrayList<>();
        int index = 0;
        while (characters.size() < 64) {
            String md5Hash = calculateMD5(salt + index);
            Matcher matcher = THREE_IN_A_ROW.matcher(md5Hash);
            if (matcher.find()) {
                String repeatedChar = matcher.group(2);
                for (int i = 1; i <= 1000; i++) {
                    md5Hash = calculateMD5(salt + (index + i));
                    if (md5Hash.contains(repeatedChar.repeat(5))) {
                        characters.add(repeatedChar);
                        break;
                    }
                }
            }
            index++;
        }
        return index - 1;
    }

    private static long part2(String salt) {
        Map<Integer, String> cache = new LinkedHashMap<>();
        List<String> characters = new ArrayList<>();
        int index = 0;
        while (characters.size() < 64) {
            String md5Hash = calculateMD5(salt + index);
            md5Hash = iterate(md5Hash, index, cache);

            Matcher matcher = THREE_IN_A_ROW.matcher(md5Hash);
            if (matcher.find()) {
                String repeatedChar = matcher.group(2);
                for (int i = 1; i <= 1000; i++) {
                    md5Hash = calculateMD5(salt + (index + i));
                    md5Hash = iterate(md5Hash, (index + i), cache);
                    if (md5Hash.contains(repeatedChar.repeat(5))) {
                        characters.add(repeatedChar);
                        //System.out.printf("index: %d, found char: %s%n", index + i, repeatedChar);
                        break;
                    }
                }
            }
            index++;
        }
        return index - 1;

    }

    private static String iterate(String md5Hash, int index, Map<Integer, String> cache) {
        if (!cache.containsKey(index)) {
            for (int i = 0; i < 2016; i++) {
                md5Hash = calculateMD5(md5Hash);
            }
            cache.put(index, md5Hash);
            return md5Hash;
        }
        return cache.get(index);
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

