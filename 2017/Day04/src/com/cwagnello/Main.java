package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	    File file = new File("test.txt");
	    System.out.println(partOne(file));
	    System.out.println(partTwo(file));
    }

    static int partOne(File file) {
        int count = 0;
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                Set<String> wordSet = new HashSet<>();
                String[] wordsArray = sc.nextLine().split("\\s+");
                wordSet.addAll(Arrays.asList(wordsArray));
                if (wordsArray.length == wordSet.size()) {
                    count++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    static int partTwo(File file) {
        int count = 0;
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                Set<String> wordSet = new HashSet<>();
                String[] wordsArray = sc.nextLine().split("\\s+");
                for (String word : wordsArray) {
                    String[] characters = word.split("");
                    Arrays.sort(characters);
                    wordSet.add(String.join("", characters));
                }
                if (wordsArray.length == wordSet.size()) {
                    count++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }


}
