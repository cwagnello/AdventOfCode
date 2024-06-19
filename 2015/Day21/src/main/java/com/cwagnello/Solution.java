package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Player boss = parseBoss("src/main/resources/input.txt");
        List<Item> items = new ArrayList<>();
        items.addAll(parse("src/main/resources/input-ring.txt", "ring"));
        items.addAll(parse("src/main/resources/input-armor.txt", "armor"));
        items.addAll(parse("src/main/resources/input-weapon.txt","weapon"));

        System.out.println("2015 Day 21 part 1: " + part1(items, boss));
        System.out.println("2015 Day 21 part 2: " + part2(items, boss));
    }

    private static int part1(List<Item> items, Player boss) {
        int minCost = Integer.MAX_VALUE;
        for (int i = 0; i < Math.pow(2, items.size()); i++) {
            Player player = Player.builder().hitPoints(100).build();
            if (validConfiguration(i)) {
                player.configure(i, items);
                if (player.winsFight(boss)) {
                    minCost = Math.min(minCost, player.calculateEquipmentCost());
                }
            }
        }
        return minCost;
    }

    private static int part2(List<Item> items, Player boss) {
        int maxCost = Integer.MIN_VALUE;
        for (int i = 0; i < Math.pow(2, items.size()); i++) {
            Player player = Player.builder().hitPoints(100).build();
            if (validConfiguration(i)) {
                player.configure(i, items);
                if (!player.winsFight(boss)) {
                    maxCost = Math.max(maxCost, player.calculateEquipmentCost());
                }
            }
        }
        return maxCost;
    }

    private static boolean validConfiguration(int bitmask) {
        final int WEAPON = 0xF800;
        final int ARMOR = 0x07C0;
        final int RING = 0x003F;
        int[] itemBitmasks = {WEAPON, ARMOR, RING};

        for (int itemBitmask : itemBitmasks) {
            int value = itemBitmask & bitmask;
            switch(itemBitmask) {
                case WEAPON -> {
                    if (bitCount(value) != 1) {
                        return false;
                    }
                }
                case ARMOR -> {
                    if (bitCount(value) > 1) {
                        return false;
                    }
                }
                case RING -> {
                    if (bitCount(value) > 2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static int bitCount(int bitmask) {
        int count = 0;
        for (int shift = 0; shift < 16; shift++) {
            if (((bitmask >> shift) & 1) == 1) {
                count++;
            }
        }
        return count;
    }
    private static Player parseBoss(String filename) {
        List<String> input = readFile(filename);
        Player boss = new Player();
        for (String line : input) {
            String[] parts = line.split(":\\s+");
            switch(parts[0]) {
                case "Hit Points" -> boss.setHitPoints(Integer.parseInt(parts[1]));
                case "Damage" -> boss.setWeapon(Weapon.builder().damage(Integer.parseInt(parts[1])).build());
                case "Armor" -> boss.add(Armor.builder().armor(Integer.parseInt(parts[1])).build());
            }
        }
        return boss;
    }

    private static List<Item> parse(String filename, String type) {
        List<String> input = readFile(filename);
        List<Item> items = new ArrayList<>();
        for (String weapon : input) {
            String[] parts = weapon.split("\\s+");
            switch(type) {
                case "weapon" -> items.add(new Weapon(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
                case "armor" -> items.add(new Armor(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
                case "ring" -> items.add(new Ring(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
            }
        }
        return items;
    }

    private static List<String> readFile(String filename) {
        File file = new File(filename);
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
        return input;
    }
}
