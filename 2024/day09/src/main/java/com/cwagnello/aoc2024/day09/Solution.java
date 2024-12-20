package com.cwagnello.aoc2024.day09;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Solution {
    public static void main (String[] args) {
        List<String> input = readFile("src/main/resources/input.txt");
        log.info("2024 day 9 part 1: {}", part1(parse(input)));
        log.info("2024 day 9 part 2: {}", part2(parseFullBlocks(input)));
    }

    private static long part1(List<Block> blocks) {
        long checkSum = 0;
        int left = 0, right = blocks.size() - 1;
        while (left < right) {
            if (blocks.get(left) instanceof FileBlock && blocks.get(right) instanceof FileBlock) {
                left++;
            }
            else if (blocks.get(left) instanceof FileBlock && blocks.get(right) instanceof Space) {
                left++;
                right--;
            }
            else if (blocks.get(left) instanceof Space && blocks.get(right) instanceof FileBlock) {
                blocks.set(left, new FileBlock(blocks.get(right).getId()));
                blocks.set(right, new Space(-1));
                left++;
                right--;
            }
            else if (blocks.get(left) instanceof Space && blocks.get(right) instanceof Space) {
                right--;
            }
        }
        int i = 0;
        while (blocks.get(i) instanceof FileBlock) {
            //log.info("i: {}, blockId: {}", i, blocks.get(i).getId());
            checkSum += (long)i * blocks.get(i).getId();
            i++;
        }
        return checkSum;
    }

    private static long part2(List<Block> blocks) {
        long checkSum = 0;
        int left = 0, right = blocks.size() - 1;
        while (left < right) {
            if (blocks.get(left) instanceof FileBlock) {
                left++;
            }
            else if (blocks.get(right) instanceof Space) {
                right--;
            }
            else {
                boolean blockMoved = false;
                for (int i = left; i < right; i++) {
                    if (blocks.get(i) instanceof Space && blocks.get(right) instanceof FileBlock) {
                        if (blocks.get(i).getLength() >= blocks.get(right).getLength()) {
                            blocks.add(i, blocks.get(right));
                            blocks.get(i + 1).setLength(blocks.get(i + 1).getLength() - blocks.get(i).getLength());
                            blocks.set(right + 1, new Space(-1, blocks.get(i).getLength()));
                            i = blocks.size();
                            blockMoved = true;
                        }
                    }
                }
                if (!blockMoved) {
                    right--;
                }
            }
        }
        int i = 0;
        for (Block b: blocks) {
            if (b instanceof FileBlock) {
                for (int j = 0; j < b.getLength(); j++) {
                    //log.info("i: {}, blockId: {}", i, blocks.get(i).getId());
                    checkSum += (long) (i + j) * b.getId();
                }
            }
            i += b.getLength();
        }
        return checkSum;
    }

    private static List<Block> parse (List<String> input) {
        List<Block> blocks = new ArrayList<>();
        String[] blockData = input.getFirst().split("");
        for (int i = 0; i < blockData.length; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < Integer.parseInt(blockData[i]); j++) {
                    blocks.add(new FileBlock(i / 2));
                }
            }
            else {
                for (int j = 0; j < Integer.parseInt(blockData[i]); j++) {
                    blocks.add(new Space(-1));
                }
            }
        }
        return blocks;
    }

    private static List<Block> parseFullBlocks (List<String> input) {
        List<Block> blocks = new ArrayList<>();
        String[] blockData = input.getFirst().split("");
        for (int i = 0; i < blockData.length; i++) {
            if (i % 2 == 0) {
                blocks.add(new FileBlock(i / 2, Integer.parseInt(blockData[i])));
            }
            else {
                blocks.add(new Space(-1, Integer.parseInt(blockData[i])));
            }
        }
        return blocks;
    }

    private static List<String> readFile(final String fileName) {
        File file = new File(fileName);
        List<String> input = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                //assume input data is well formed
                input.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return input;
    }

}
