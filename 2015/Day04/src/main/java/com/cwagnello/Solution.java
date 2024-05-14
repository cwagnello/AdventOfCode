package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author cwagnello
 */
public class Solution {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        File file = new File("src/main/resources/input.txt");
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

        MessageDigest md = MessageDigest.getInstance("MD5");
        
        String inputSecret = input.get(0);

        System.out.println("2015 Day04 part1: " + process(md, inputSecret, "00000"));
        System.out.println("2015 Day04 part2: " + process(md, inputSecret, "000000"));

    }

    private static int process(MessageDigest md, String inputSecret, String prefix) throws UnsupportedEncodingException {
        for(int i = 0; i < Integer.MAX_VALUE; i++) {
            byte[] mdbytes = md.digest((inputSecret + i).getBytes(StandardCharsets.UTF_8));
            String md5 = convertMD5toString(mdbytes);
//            System.out.println("Digest(in hex format):: " + md5);

            if(md5.startsWith(prefix)) {
                return i;
            }
        }
        return -1;
    }

    private static String convertMD5toString(byte[] mdbytes) {
        //convert the byte to hex format zero pad the result to create a uniform length string
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
            String hex = Integer.toHexString(0xff & mdbytes[i]);
            if (hex.length()==1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
}
