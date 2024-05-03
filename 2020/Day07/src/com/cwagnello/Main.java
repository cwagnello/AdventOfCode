package com.cwagnello;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
	    try (Scanner sc = new Scanner(new File("input.txt"))) {
	        List<String> data = new ArrayList<>();
	        while (sc.hasNextLine()) {
	            data.add(sc.nextLine().replace(".", ""));
            }
	        part1(data);
	        part2(data);
        }
	    catch (IOException ioe) {
	        ioe.printStackTrace();
        }
    }

    private static void part1(List<String> data) {
        Map<String, Set<String>> graph = createGraph1(data);
        Set<String> foundGoldBag = new HashSet<>();
        //traverse graph looking for gold bags
        for (String bag: graph.keySet()) {
            Set<String> visited = new HashSet<>();
            Queue<String> queue = new LinkedList<>();
            queue.add(bag);
            if (bag.equals("shiny gold")) {
                queue.clear();
            }
            while (!queue.isEmpty()) {
                int size = queue.size();
                //System.out.println("Bag: " + bag);
                for (int i = 0; i < size; i++) {
                    String currentBag = queue.remove();
                    //System.out.println("current bag: " + currentBag);
                    if (currentBag.equals("shiny gold")) {
                        queue.clear();
                        foundGoldBag.add(bag);
                        break;
                    }
                    for (String innerBag: graph.get(currentBag)) {
                        if (!visited.contains(innerBag)) {
                            visited.add(innerBag);
                            queue.add(innerBag);
                        }
                    }
                }
            }
        }
        System.out.println(foundGoldBag.size());
    }

    private static void part2(List<String> data) {

    }

    private static Map<String, Set<String>> createGraph1(List<String> data) {
        Map<String, Set<String>> graph = new HashMap<>();
        //System.out.println("Size:" + data.size());
        for (String d: data) {
            String[] bags = d.split("\\s+contain\\s+");
            String key = bags[0].replaceAll("\\sbags?$", "");
            //System.out.println("Bag: " + key);
            List<String> contents = Arrays.stream(bags[1].split("\\s*?,\\s*?")).map(b -> b.replaceAll("\\s*?\\d+\\s+|\\sbags?$", "")).collect(Collectors.toList());
            //System.out.println("contents: " + contents);
            graph.put(key, new HashSet<>());
            if (contents.get(0).equals("no other")) {
                continue;
            }
            if (!graph.containsKey(key)) {
                graph.put(key, new HashSet<>());
            }
            graph.get(key).addAll(contents);
        }
        return graph;
    }

    private static Bag createGraph2(List<String> data) {
        Bag root = new Bag("root", 1, new ArrayList<>());
        Bag current = root;

        for (String d: data) {
            String[] bags = d.split("\\s+contain\\s+");
            String newBagKey = bags[0].replaceAll("\\sbags?$", "");
            //System.out.println("Bag: " + newBagKey);
            List<Bag> contents = Arrays.stream(bags[1].split("\\s*?,\\s*?")).map(b -> b.replaceAll("\\s*?bags?\\s*?$", "")).map(b->createBag(b)).collect(Collectors.toList());
            //System.out.println("contents: " + contents);
            current = new Bag(newBagKey, 1, new ArrayList<>());
            if (contents.get(0).color.equals("no other")) {
                continue;
            }
        }
        return root;
    }

    private static Bag createBag(String input) {
        Pattern p = Pattern.compile("^(\\d+)\\s+(\\w+)$");
        Matcher m = p.matcher(input);
        Bag bag = null;
        if (m.find()) {
            bag = new Bag(m.group(1), Integer.parseInt(m.group(2)), new ArrayList<>());
        }
        return bag;
    }

}

class Bag {
    String color;
    int count;
    List<Bag> bagsInside;

    public Bag(String color, int count, List<String> bagsInside) {
        this.color = color;
        this.count = count;
        List<Bag> bags = new ArrayList<>();
        this.bagsInside = bags;
        if (!bagsInside.get(0).equals("no other")) {
            for (String bag: bagsInside) {

                bags.add(new Bag());
            }
        }
    }
}

