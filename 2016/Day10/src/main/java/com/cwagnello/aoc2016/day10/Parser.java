package com.cwagnello.aoc2016.day10;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {
    private static final Pattern ASSIGNMENT = Pattern.compile("^value\\s+(\\d+).*?(\\d+)");
    private static final int FROM_BOT = 1;
    private static final int TYPE_LOW = 5;
    private static final int TO_LOW = 6;
    private static final int TYPE_HIGH = 10;
    private static final int TO_HIGH = 11;
    private static Parser parser;

    private Parser() {
    }

    public static Parser getInstance() {
        if (parser == null) {
            parser = new Parser();
        }
        return parser;
    }

    public List<Instruction> parse(List<String> input, Map<Integer, Bot> bots, Map<Integer, Output> outputs) {
        List<Instruction> instructions = new ArrayList<>();
        for (String line: input) {
            if (line.contains("value")) {
                Matcher matcher = ASSIGNMENT.matcher(line);
                if (matcher.find()) {
                    int chip = Integer.parseInt(matcher.group(1));
                    int bot = Integer.parseInt(matcher.group(2));
                    if (!bots.containsKey(bot)) {
                        bots.put(bot, new Bot(bot));
                    }
                    instructions.add(new Assign(bot, chip));
                }
            }
            else {
                String[] parts = line.split("\\s+");
                int bot = Integer.parseInt(parts[FROM_BOT]);
                if (!bots.containsKey(bot)) {
                    bots.put(bot, new Bot(bot));
                }
                Receiver low = new Receiver(Integer.parseInt(parts[TO_LOW]), parts[TYPE_LOW]);
                Receiver high = new Receiver(Integer.parseInt(parts[TO_HIGH]), parts[TYPE_HIGH]);
                addReceiver(low, bots, outputs);
                addReceiver(high, bots, outputs);
                instructions.add(new BotDistributeChip(bot, low, high));
            }
        }
        return instructions;
    }

    private void addReceiver(Receiver receiver, Map<Integer, Bot> bots, Map<Integer, Output> outputs) {
        if (receiver.isBot()) {
            if (!bots.containsKey(receiver.id())) {
                bots.put(receiver.id(), new Bot(receiver.id()));
            }
        }
        else {
            if (!outputs.containsKey(receiver)) {
                outputs.put(receiver.id(), new Output(receiver.id()));
            }
        }

    }
}
