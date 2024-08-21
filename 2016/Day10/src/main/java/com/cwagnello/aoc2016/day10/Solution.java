package com.cwagnello.aoc2016.day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
    public static void main (String[] args) {
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

        Map<Integer, Bot> bots = new HashMap<>();
        Map<Integer, Output> outputs = new HashMap<>();
        List<Instruction> instructions = Parser.getInstance().parse(input, bots, outputs);
        System.out.println("2016 day 10 part 1: " + part1(instructions, bots, outputs));
        System.out.println("2016 day 10 part 2: " + part2(instructions, bots, outputs));
    }

    private static int part1(List<Instruction> instructions, Map<Integer, Bot> bots, Map<Integer, Output> outputs) {
        while (true) {
            for (Instruction instruction : instructions) {
                switch (instruction) {
                    case Assign assign -> {
                        if (!assign.isProcessed()) {
                            assign.setProcessed(true);
                            bots.get(assign.botId()).add(assign.chip());
                        }
                    }
                    case BotDistributeChip distributeChip -> {
                        updateBots(bots, outputs, distributeChip);
                        if (bots.get(distributeChip.lower().id()).contains(61) && bots.get(distributeChip.lower().id()).contains(17)) {
                            return distributeChip.lower().id();
                        }
                        if (bots.get(distributeChip.higher().id()).contains(61) && bots.get(distributeChip.higher().id()).contains(17)) {
                            return distributeChip.higher().id();
                        }
                    }
                    default -> throw new IllegalArgumentException("Unknown instruction: " + instruction);
                }
            }
        }
    }

    private static int part2(List<Instruction> instructions, Map<Integer, Bot> bots, Map<Integer, Output> outputs) {
        while (true) {
            for (Instruction instruction : instructions) {
                switch (instruction) {
                    case Assign assign -> {
                        if (!assign.isProcessed()) {
                            assign.setProcessed(true);
                            bots.get(assign.botId()).add(assign.chip());
                        }
                    }
                    case BotDistributeChip distributeChip -> {
                        updateBots(bots, outputs, distributeChip);
                        if (outputs.get(0).isSet() && outputs.get(1).isSet() && outputs.get(2).isSet()) {
                            return outputs.get(0).chip() * outputs.get(1).chip() * outputs.get(2).chip();
                        }
                    }
                    default -> throw new IllegalArgumentException("Unknown instruction: " + instruction);
                }
            }
        }
    }

    private static void updateBots(Map<Integer, Bot> bots, Map<Integer, Output> outputs, BotDistributeChip distributeChip) {
        Bot source = bots.get(distributeChip.source());
        if (source.hasTwoChips()) {
            if (distributeChip.lower().isBot()) {
                Bot lower = bots.get(distributeChip.lower().id());
                lower.add(source.giveLowest());
            } else {
                Output lower = outputs.get(distributeChip.lower().id());
                lower.add(source.giveLowest());
            }
            if (distributeChip.higher().isBot()) {
                Bot higher = bots.get(distributeChip.higher().id());
                higher.add(source.giveHighest());
            } else {
                Output higher = outputs.get(distributeChip.higher().id());
                higher.add(source.giveHighest());
            }
        }
    }
}
