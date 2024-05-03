package com.cwagnello;

import java.io.File;
import java.io.IOException;
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

public class Main {
    private static final Set<String> requiredKeys = new HashSet<>(Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"));
    private static final Set<String> eyeColor = new HashSet<>(Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth"));

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new File("input.txt")).useDelimiter(Pattern.compile("\\n\\n"))) {
	    //try (Scanner sc = new Scanner(new File("input.txt")).useDelimiter(Pattern.compile("\\n\\n"))) {
	        List<String> records = new ArrayList<>();

	        while (sc.hasNext()) {
                String record = sc.next().replaceAll("\\n", " ");
                records.add(record);
            }
	        part1(records);
	        part2(records);
        }
	    catch (IOException ioe) {
	        ioe.printStackTrace();
        }
    }

    private static void part1(List<String> records) {
        int count = 0;

        for (String record : records) {
            String[] fields = record.split("\\s+");
            Set<String> recordKeys = new HashSet<>();
            for (int i = 0; i < fields.length; i++) {
                String[] keyValue = fields[i].split(":");
                recordKeys.add(keyValue[0]);
            }
            if (recordKeys.containsAll(requiredKeys)) {
                count++;
            }
        }
        System.out.println(count);
    }

    private static void part2(List<String> records) {
        int count = 0;
//System.out.println("# records: " + records.size());
        for (String record : records) {
            String[] fields = record.split("\\s+");
            Map<String, String> recordKeys = new HashMap<>();
            for (int i = 0; i < fields.length; i++) {
                String[] keyValue = fields[i].split(":");
                recordKeys.put(keyValue[0], keyValue[1]);
            }
            boolean hasRequiredKeys = true;
            for (String key : requiredKeys) {
                if (!recordKeys.containsKey(key) || !validateField(key, recordKeys.get(key))) {
                    hasRequiredKeys = false;
                    break;
                }
            }
            if (hasRequiredKeys) {
                count++;
            }
        }
        System.out.println(count);
    }

    /**
     * byr (Birth Year) - four digits; at least 1920 and at most 2002.
     * iyr (Issue Year) - four digits; at least 2010 and at most 2020.
     * eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
     * hgt (Height) - a number followed by either cm or in:
     * If cm, the number must be at least 150 and at most 193.
     * If in, the number must be at least 59 and at most 76.
     * hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
     * ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
     * pid (Passport ID) - a nine-digit number, including leading zeroes.
     * cid (Country ID) - ignored, missing or not.
     * @param key
     * @param value
     * @return
     */
    private static boolean validateField(String key, String value) {
        boolean isValid = false;
        switch (key) {
            case "byr":
                isValid = Integer.parseInt(value) >= 1920 && Integer.parseInt(value) <= 2002;
                //System.out.println("Valid byr: " + value);
            break;
            case "iyr":
                isValid = Integer.parseInt(value) >= 2010 && Integer.parseInt(value) <= 2020;
            break;
            case "eyr":
                isValid = Integer.parseInt(value) >= 2020 && Integer.parseInt(value) <= 2030;
            break;
            case "hgt":
                Pattern p = Pattern.compile("^(\\d+)(\\w+)$");
                Matcher m = p.matcher(value);
                if (m.find()) {
                    int height = Integer.parseInt(m.group(1));
                    String dimensions = m.group(2);
                    //System.out.println("height: " + height + ", dim: " + dimensions);
                    if (dimensions.equals("cm") && height >= 150 && height <= 193) {
                        isValid = true;
                    }
                    else if (dimensions.equals("in") && height >= 59 && height <= 76) {
                        isValid = true;
                    }
                }
            break;
            case "hcl":
                isValid = value.length() == 7 && value.matches("#[0-9a-f]{6}");
                //System.out.println("isValid: " + isValid + ", value: " + value);
            break;
            case "ecl":
                isValid = eyeColor.contains(value);
            break;
            case "pid":
                isValid = value.length() == 9 && value.matches("\\d+");
            break;
            default: isValid = false;
        }
        return isValid;
    }
}
