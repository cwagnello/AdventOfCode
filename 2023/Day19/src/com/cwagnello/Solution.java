package com.cwagnello;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private static String readInput() {
        String file = "src/com/cwagnello/input.txt";
        String input = null;
        try {
            input = readFile(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    public static void main(String[] args) {
        String input = readInput();
        String[] data = input.split("\\n\\n");
        List<Part> parts = new ArrayList<>();
        Map<String, List<Rule>> workflows = new HashMap<>();
        parseInput(data[0], data[1], parts, workflows);

        System.out.println("Day19 - Part1: " + part1(parts, workflows));
        System.out.println("Day19 - Part2: " + part2(workflows));
    }

    private static long part1(List<Part> parts, Map<String, List<Rule>> workflows) {
        long sum = 0;
        for (Part part: parts) {
            Workflow start = new Workflow("in");
            Deque<Rule> queue = new LinkedList<>();
            queue.offer(start);
            while (!queue.isEmpty()) {
                Rule current = queue.poll();
                switch (current) {
                    case Workflow w -> {
                        for (Rule r : workflows.get(w.name)) {
                            queue.offer(r);
                        }
                    }
                    case LessThan l -> {
                        if (l.evaluate(part)) {
                            queue.clear();
                            queue.offer(l.rule);
                        }
                    }
                    case GreaterThan g -> {
                        if (g.evaluate(part)) {
                            queue.clear();
                            queue.offer(g.rule);
                        }
                    }
                    case Accept a -> {
                        sum += calculateSum(part);
                        queue.clear();
                    }
                    case Reject r -> queue.clear();
                    case default -> throw new IllegalStateException("Unknown workflow rule " + current.getClass());
                }
            }
        }
        return sum;
    }

    private static long part2(Map<String, List<Rule>> workflows) {
        long sum = 0;
        Rule startRule = new Workflow("in");
        PartRange startRange = new PartRange(new Range(1, 4000), new Range(1, 4000), new Range(1, 4000), new Range(1, 4000));
        Node start = new Node(startRule, startRange);
        return traverseParts(workflows, start);
    }

    private static long traverseParts(Map<String, List<Rule>> workflows, Node start) {
        long sum = 0;
        if (start.rule.name.contains("A")) {
            //Calculate ratings for given part range
            System.out.println("" + start.rule + ", part Range: " + start.partRange);
            return start.calculateRating();
        }
        if (start.rule.name.contains("R")) {
            return 0;
        }
        PartRange current = new PartRange(start.partRange);
        for (Rule rule : workflows.get(start.rule.name)) {
            switch(rule) {
                case LessThan l -> {
                    PartRange updatedPartRange = new PartRange(current);
                    switch (l.field) {
                        case X -> {
                            updatedPartRange.x.end = l.value - 1;
                            current.x.start = l.value;
                        }
                        case M -> {
                            updatedPartRange.m.end = l.value - 1;
                            current.m.start = l.value;
                        }
                        case A -> {
                            updatedPartRange.a.end = l.value - 1;
                            current.a.start = l.value;
                        }
                        case S -> {
                            updatedPartRange.s.end = l.value - 1;
                            current.s.start = l.value;
                        }
                    }
                    sum += traverseParts(workflows, new Node(l.rule, updatedPartRange));
                }
                case GreaterThan g -> {
                    PartRange updatedPartRange = new PartRange(current);
                    switch (g.field) {
                        case X -> {
                            updatedPartRange.x.start = g.value + 1;
                            current.x.end = g.value;
                        }
                        case M -> {
                            updatedPartRange.m.start = g.value + 1;
                            current.m.end = g.value;
                        }
                        case A -> {
                            updatedPartRange.a.start = g.value + 1;
                            current.a.end = g.value;
                        }
                        case S -> {
                            updatedPartRange.s.start = g.value + 1;
                            current.s.end = g.value;
                        }
                    }
                    sum += traverseParts(workflows, new Node(g.rule, updatedPartRange));
                }
                default -> sum += traverseParts(workflows, new Node(rule, current));
            }
        }
        return sum;
    }

    private static long calculateSum(Part part) {
        return part.x + part.m + part.a + part.s;
    }
    private static void parseInput(String workflowData, String partData, List<Part> parts, Map<String, List<Rule>> workflows) {
        workflows.putAll(parseWorkflowData(workflowData));
        parts.addAll(parsePartsData(partData));
    }

    private static Map<String, List<Rule>> parseWorkflowData(String workflowData) {
        Map<String, List<Rule>> workflows = new HashMap<>();
        workflows.put("A", List.of(new Accept()));
        workflows.put("R", List.of(new Reject()));
        Pattern workflowPattern = Pattern.compile("(\\w+)\\{(.*?)\\}");
        Pattern ruleConditionalPattern = Pattern.compile("(\\w)([<>])(\\d+):(\\w+)");

        for (String line : workflowData.split("\\n")) {
            Matcher workflowMatcher = workflowPattern.matcher(line);
            if (workflowMatcher.matches()) {
                String workflowName = workflowMatcher.group(1);
                String[] ruleData = workflowMatcher.group(2).split(",");
                for (String rule: ruleData) {
                    if (rule.contains("<") || rule.contains(">")) {
                        Matcher ruleMatcher = ruleConditionalPattern.matcher(rule);
                        if (ruleMatcher.matches()) {
                            String field = ruleMatcher.group(1);
                            String value = ruleMatcher.group(3);
                            String ruleName = ruleMatcher.group(4);
                            if (!workflows.containsKey(workflowName)) {
                                workflows.put(workflowName, new ArrayList<>());
                            }
                            switch(ruleMatcher.group(2)) {
                                case "<" ->
                                        workflows.get(workflowName)
                                                .add(new LessThan(FIELD.valueOf(field.toUpperCase()), Integer.parseInt(value), new Workflow(ruleName), ruleName));
                                case ">" ->
                                        workflows.get(workflowName)
                                                .add(new GreaterThan(FIELD.valueOf(field.toUpperCase()), Integer.parseInt(value), new Workflow(ruleName), ruleName));
                            }
                        }
                    }
                    else {
                        if (!workflows.containsKey(workflowName)) {
                            workflows.put(workflowName, new ArrayList<>());
                        }
                        workflows.get(workflowName).add(classifyRule(rule));
                    }
                }
            }
        }
        return workflows;
    }

    private static Rule classifyRule(String input) {
        return switch(input) {
            case "A" -> new Accept();
            case "R" -> new Reject();
            default -> new Workflow(input);
        };
    }

    private static List<Part> parsePartsData(String partData) {
        List<Part> parts = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\{x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)\\}");
        for (String line : partData.split("\\n")) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
                Part part = new Part(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(4)));
                parts.add(part);
            }
        }
        return parts;
    }
}

class Range {
    long start;
    long end;
    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }
    public Range(Range range) {
        this.start = range.start;
        this.end = range.end;
    }
    @Override
    public String toString() {
        return "(" + start + " -> " + end + ")";
    }
}
class PartRange {
    Range x;
    Range m;
    Range a;
    Range s;
    Workflow workflow;
    public PartRange(Range x, Range m, Range a, Range s) {
        this.x = x;
        this.m = m;
        this.a = a;
        this.s = s;
    }
    public PartRange(PartRange partRange) {
        this(new Range(partRange.x), new Range(partRange.m), new Range(partRange.a), new Range(partRange.s));
        this.workflow = partRange.workflow;
    }
    @Override
    public String toString() {
        return "x=" + this.x + ", m=" + this.m + ", a=" + this.a + ", s=" + this.s;
    }
}

class Node {
    Rule rule;
    PartRange partRange;
    public Node(Rule rule, PartRange partRange) {
        this.rule = rule;
        this.partRange = partRange;
    }
    public long calculateRating() {
        return (this.partRange.x.end - this.partRange.x.start + 1)
                * (this.partRange.m.end - this.partRange.m.start + 1)
                * (this.partRange.a.end - this.partRange.a.start + 1)
                * (this.partRange.s.end - this.partRange.s.start + 1);
    }
}
enum FIELD {
    X,
    M,
    A,
    S;
}
abstract class Rule {
    Rule rule;
    String name;
    abstract boolean evaluate(Part part);
}

class LessThan extends Rule {
    long value;
    FIELD field;
    public LessThan(FIELD field, long value, Rule rule, String name) {
        this.field = field;
        this.value = value;
        this.rule = rule;
        this.name = name;
    }
    @Override
    public boolean evaluate(Part part) {
        return switch(this.field) {
            case X -> part.x < this.value;
            case M -> part.m < this.value;
            case A -> part.a < this.value;
            case S -> part.s < this.value;
            default -> throw new IllegalArgumentException("Invalid field type in rule " + this.field);
        };
    }
}

class GreaterThan extends Rule {
    long value;
    FIELD field;
    public GreaterThan(FIELD field, long value, Rule rule, String name) {
        this.field = field;
        this.value = value;
        this.rule = rule;
        this.name = name;
    }
    @Override
    public boolean evaluate(Part part) {
        return switch(this.field) {
            case X -> part.x > this.value;
            case M -> part.m > this.value;
            case A -> part.a > this.value;
            case S -> part.s > this.value;
            default -> throw new IllegalArgumentException("Invalid field type in rule " + this.field);
        };
    }
    @Override
    public String toString() {
        return "GreaterThan(" + name + ", field=" + this.field + ", value=" + this.value + ", nextRule=" + super.name + ")";
    }
}

class Accept extends Rule {

    public Accept() {
        this.name = "A";
    }
    @Override
    boolean evaluate(Part part) {
        return true;
    }
    @Override
    public String toString() {
        return "Accept(" + this.name + ")";
    }
}

class Reject extends Rule {
    public Reject() {
        this.name = "R";
    }
    @Override
    boolean evaluate(Part part) {
        return true;
    }
    @Override
    public String toString() {
        return "Reject(" + name + ")";
    }
}

class Workflow extends Rule {
    public Workflow(String name) {
        this.name = name;
    }

    @Override
    boolean evaluate(Part part) {
        return true;
    }
    @Override
    public String toString() {
        return "Workflow(" + this.name + ")";
    }
}

class Part {
    long x;
    long m;
    long a;
    long s;
    public Part(long x, long m, long a, long s) {
        this.x = x;
        this.m = m;
        this.a = a;
        this.s = s;
    }

    @Override
    public String toString() {
        return "{ x:" + this.x + ", m:" + m + ", a:" + this.a + ", s:" + this.s + "}";
    }
}