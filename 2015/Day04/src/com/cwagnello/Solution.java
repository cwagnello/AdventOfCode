package com.cwagnello;

import java.security.MessageDigest;

/**
 *
 * @author cwagnello
 */
public class Solution {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        
        String inputSecret = "bgvyzdsv";
        
        for(int i = 0; i < Integer.MAX_VALUE; i++) {
            byte[] mdbytes = md.digest((inputSecret + i).getBytes("UTF-8"));
            String md5 = convertMD5toString(mdbytes);

            if(md5.startsWith("000000")) {
                System.out.println("number: " + i);
                System.out.println("Digest(in hex format):: " + md5);
                break;
            }
        }

    }

    private static String convertMD5toString(byte[] mdbytes) {
        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i=0;i< mdbytes.length;i++) {
            String hex=Integer.toHexString(0xff & mdbytes[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
}
