package com.cwagnello;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        File file = new File("src/input.txt");
        Pattern p = Pattern.compile("^(\\d+)-(\\d+)\\s+(\\w):\\s+(\\w+)$");
        part1(file, p);
        part2(file, p);
    }

    private static void part1(File file, Pattern p) {
        try (Scanner sc = new Scanner(file)) {
            int count = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Matcher m = p.matcher(line);
                if (m.find()) {
                    int min = new Integer(m.group(1));
                    int max = new Integer(m.group(2));
                    Character character = m.group(3).charAt(0);
                    String password = m.group(4);
                    //System.out.println("min: " + min + ", max: " + max + ", character:" + character + ", password: " + password);
                    Map<Character, Integer> frequency = new HashMap<>();
                    for (char c : password.toCharArray()) {
                        frequency.put(c, frequency.getOrDefault(c, 0) + 1);
                    }
                    int freq = frequency.get(character) == null ? 0 : frequency.get(character);
                    if (freq >= min && freq <= max) {
                        count++;
                    }
                }
            }
            System.out.println(count);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void part2(File file, Pattern p) {
        try (Scanner sc = new Scanner(file)) {
            int count = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Matcher m = p.matcher(line);
                if (m.find()) {
                    int index1 = new Integer(m.group(1)) - 1;
                    int index2 = new Integer(m.group(2)) - 1;
                    Character character = m.group(3).charAt(0);
                    String password = m.group(4);
                    //System.out.println("index1: " + index1 + ", index2: " + index2 + ", character:" + character + ", password: " + password);
                    int total = 0;
                    if (password.charAt(index1) == character) {
                        total++;
                    }
                    if (password.charAt(index2) == character) {
                        total++;
                    }
                    if (total == 1) {
                        count++;
                    }
                    else {
                        System.out.println("Invalid. index1: " + index1 + ", index2: " + index2 + ", character:" + character + ", password: " + password);
                    }
                }
            }
            System.out.println(count);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
