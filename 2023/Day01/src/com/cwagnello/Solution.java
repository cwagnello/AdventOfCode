package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {

        File file = new File("src/com/cwagnello/input.txt");
        List<String> input1 = new ArrayList<>();
        List<String> input2 = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while(sc.hasNext()) {
                String line = sc.next();
                input1.add(line);
                input2.add(replaceNumberStrings(line));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("part1: " + process(input1));
        System.out.println("part2: " + process(input2));
    }

    private static int process(List<String> input) {
        return input.stream()
                .map(s -> findNumber(s))
                .mapToInt(Integer::parseInt).sum();
    }

    private static String findNumber(String s) {
        String result = "";
        String[] chars = s.split("");
        for (int i = 0; i < chars.length; i++) {
            if (chars[i].matches("\\d")) {
                result += chars[i];
                break;
            }
        }
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i].matches("\\d")) {
                result += chars[i];
                break;
            }
        }
        //System.out.println("input: " + s + ", found: " + result);
        return result;
    }

    private static String replaceNumberStrings(String s) {
        //System.out.println("before: " + s);
        String[] chars = s.split("");
        String[] updated = new String[chars.length];
        int index = 0;

        for (int i = 0; i < s.length(); i++) {
            switch (chars[i]){
                case "o": {
                    if(i + 3 <= s.length() && "one".equals(s.substring(i, i + 3))) {
                        updated[i] = "1";
                    }
                    else {
                        updated[i] = chars[i];
                    }
                    break;
                }
                case "t": {
                    if (i + 5 <= s.length() && "three".equals(s.substring(i, i + 5))) {
                        updated[i] = "3";
                    }
                    else if (i + 3 <= s.length() && "two".equals(s.substring(i, i + 3))) {
                        updated[i] = "2";
                    }
                    else {
                        updated[i] = chars[i];
                    }
                    break;
                }
                case "f": {
                    if (i + 4 <= s.length() && "four".equals(s.substring(i, i + 4))) {
                        updated[i] = "4";
                    }
                    else if (i + 4 <= s.length() && "five".equals(s.substring(i, i + 4))) {
                        updated[i] = "5";
                    }
                    else {
                        updated[i] = chars[i];
                    }
                    break;
                }
                case "s": {
                    if (i + 3 <= s.length() && "six".equals(s.substring(i, i + 3))) {
                        updated[i] = "6";
                    }
                    else if (i + 5 <= s.length() && "seven".equals(s.substring(i, i + 5))) {
                        updated[i] = "7";
                    }
                    else {
                        updated[i] = chars[i];
                    }
                    break;
                }
                case "e": {
                    if (i + 5 <= s.length() && "eight".equals(s.substring(i, i + 5))) {
                        updated[i] = "8";
                    }
                    else {
                        updated[i] = chars[i];
                    }
                    break;
                }
                case "n": {
                    if (i + 4 <= s.length() && "nine".equals(s.substring(i, i + 4))) {
                        updated[i] = "9";
                    }
                    else {
                        updated[i] = chars[i];
                    }
                    break;
                }
                default: {
                    updated[i] = chars[i];
                }
            }
        }
        //System.out.println("after: " + String.join("", updated));
        return String.join("", updated);
    }
}
