package com.cwagnello;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author cwagnello
 */
public class Solution {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/input.txt"));
            int p1Count = 0;
            int p2Count = 0;
            int totalStrings = 0;
            String line;
            while((line = br.readLine()) != null) {
                totalStrings++;
                if (has3orMoreVowels(line) && hasAtLeastOnePairConsecutiveLetters(line) && doesNotHaveCertainPairs(line)) {
                    p1Count++;
                }
                if (containsPairTwice(line) && containsThreeCharactersFirstAndThirdMatch(line)) {
                    p2Count++;
                }
            }
            System.out.println("Total number of strings " + totalStrings);
            System.out.println("Number of p1 \"nice\" strings: " + p1Count);
            System.out.println("Number of p2 \"nice\" strings: " + p2Count);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + ex);
        } catch (IOException ex) {
            System.out.println("IO exception" + ex);
        }
    }
    private static boolean has3orMoreVowels(String line) {
        int count = 0;
        Pattern pattern = Pattern.compile("[aeiou]");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            count++;
        }
        //System.out.println("Number of vowels in : " + line + " is " + count);
            
        return count >= 3;
    }
    
    private static boolean hasAtLeastOnePairConsecutiveLetters(String line) {
        int count = 0;
        Pattern pattern = Pattern.compile("(.)\\1");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            count++;
        }
        //System.out.println("Number of 2+ consecutive letters in : " + line + " is " + count);
        return count >= 1;
    }

    private static boolean doesNotHaveCertainPairs(String line) {
        Pattern pattern = Pattern.compile("ab|cd|pq|xy");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return false;
        }
        //System.out.println("String " + line + " does not contain the illegal pairs");
        return true;
    }

    private static boolean containsPairTwice(String line) {
        int count = 0;
        Pattern pattern = Pattern.compile("(..).*?\\1");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            count++;
        }
        if (count > 0) {
            //System.out.println("Number of 2+ consecutive letters in : " + line + " is " + count);
        }
        return count >= 1;
    }

    private static boolean containsThreeCharactersFirstAndThirdMatch(String line) {
        int count = 0;
        Pattern pattern = Pattern.compile("(.).\\1");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            count++;
        }
        //System.out.println("Number of 2+ consecutive letters in : " + line + " is " + count);
        return count >= 1;
    }

}
