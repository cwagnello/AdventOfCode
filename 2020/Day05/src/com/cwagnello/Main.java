package com.cwagnello;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        List <String> passes = new ArrayList<>();
        try (Scanner sc = new Scanner(new File("input.txt"))) {
            while (sc.hasNextLine()) {
                passes.add(sc.nextLine());
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        part1(passes);
        part2(passes);
    }

    private static void part1(List<String> passes) {
        int row = 0;
        int column = 0;
        Pattern p = Pattern.compile("^(\\w{7})(\\w{3})$");
        int max = 0;

        for (String pass : passes) {
            Matcher m = p.matcher(pass);
            if (m.find()) {
                row = Integer.parseInt(m.group(1).replaceAll("F", "0").replaceAll("B", "1"), 2);
                column = Integer.parseInt(m.group(2).replaceAll("L", "0").replaceAll("R", "1"), 2);
                max = Math.max (max, row * 8 + column);
            }
        }
        System.out.println(max);
    }

    private static void part2(List<String> passes) {
        int row = 0;
        int column = 0;
        Pattern p = Pattern.compile("^(\\w{7})(\\w{3})$");
        List<Integer> ids = new ArrayList<>();

        for (String pass : passes) {
            Matcher m = p.matcher(pass);
            if (m.find()) {
                row = Integer.parseInt(m.group(1).replaceAll("F", "0").replaceAll("B", "1"), 2);
                column = Integer.parseInt(m.group(2).replaceAll("L", "0").replaceAll("R", "1"), 2);
                ids.add(row * 8 + column);
            }
        }
        Collections.sort(ids);

        for (int i = 0; i < ids.size() - 1; i++) {
            if (ids.get(i + 1) - ids.get(i) > 1) {
                System.out.println((ids.get(i + 1) + ids.get(i))/2);
            }
        }
    }
}
