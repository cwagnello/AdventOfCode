package com.cwagnello.aoc2016.day08;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * rect 4x1
 * rotate row y=0 by 10
 * rotate column x=5 by 2
 */
public final class Parser {
    private static Parser parser;
    private Parser() {}

    public static Parser getInstance() {
        if (parser == null) {
            parser = new Parser();
        }
        return parser;
    }

    private static final Pattern RECT = Pattern.compile("(\\d+)x(\\d+)");
    private static final Pattern ROTATE = Pattern.compile("=(\\d+)\\s+by\\s+(\\d+)");

    public List<Instruction> parse(List<String> input) {
        List<Instruction> instructions = new ArrayList<>();
        for (String instruction: input) {
            if (instruction.contains("rect")) {
                Matcher matcher = RECT.matcher(instruction);
                if (matcher.find()) {
                    int width = Integer.parseInt(matcher.group(1));
                    int height = Integer.parseInt(matcher.group(2));
                    instructions.add(new Rectangle(width, height));
                }
            }
            else if (instruction.contains("column")) {
                Matcher matcher = ROTATE.matcher(instruction);
                if (matcher.find()) {
                    int column = Integer.parseInt(matcher.group(1));
                    int shift = Integer.parseInt(matcher.group(2));
                    instructions.add(new RotateColumn(column, shift));
                }
            }
            else if (instruction.contains("row")) {
                Matcher matcher = ROTATE.matcher(instruction);
                if (matcher.find()) {
                    int row = Integer.parseInt(matcher.group(1));
                    int shift = Integer.parseInt(matcher.group(2));
                    instructions.add(new RotateRow(row, shift));
                }
            }
        }
        return instructions;
    }
}
