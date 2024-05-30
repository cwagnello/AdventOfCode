package com.cwagnello;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
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

        String jsonDocument = input.get(0);

        System.out.println("2015 Day12 part1: " + part1(jsonDocument));
        System.out.println("2015 Day12 part2: " + part2(jsonDocument));
    }

    private static int part1(String jsonDocument) {
        int sum = 0;
        for (int i = 0; i < jsonDocument.length(); i++) {
            StringBuilder sb = new StringBuilder();
            while (i < jsonDocument.length() && isNumber(jsonDocument.charAt(i))) {
                sb.append(jsonDocument.charAt(i));
                i++;
            }
            if (!sb.isEmpty()) {
                sum += Integer.parseInt(sb.toString());
            }
        }
        return sum;
    }

    private static int part2(String jsonDocument) {
        int sum = 0;
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(jsonDocument);
            sum = recurse(jsonNode);
        }
        catch (JsonProcessingException jsonProcessingException) {
            System.out.println("Error processing input json: " + jsonProcessingException);
        }
        return sum;
    }

    private static int recurse(JsonNode jsonNode) {
        int sum = 0;

        switch(jsonNode.getNodeType()) {
            case ARRAY -> {
                Iterator it = jsonNode.elements();
                while (it.hasNext()) {
                    sum += recurse((JsonNode)it.next());
                }
            }
            case OBJECT -> {
                for (Map.Entry<String, JsonNode> entry : jsonNode.properties()) {
                    if (entry.getValue().asText().equals("red")) {
                        sum = 0;
                        break;
                    }
                    else {
                        sum += recurse(entry.getValue());
                    }
                }
            }
            case NUMBER -> {
                sum += jsonNode.asInt();
            }
            case NULL, STRING, BINARY, BOOLEAN, MISSING, POJO -> {
                //don't care about these cases
            }
        }
        return sum;
    }

    private static boolean isNumber(char c) {
        return c == '-' || Character.isDigit(c);
    }
}
