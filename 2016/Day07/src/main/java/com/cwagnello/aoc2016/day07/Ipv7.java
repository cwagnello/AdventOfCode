package com.cwagnello.aoc2016.day07;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ipv7 {
    private final String ip7Data;
    private final List<String> outerChunks;
    private final List<String> hypernetChunks;

    private final Pattern IPV7_REGEX = Pattern.compile("([a-z]+)\\[([a-z]+)\\]");

    public Ipv7(String ip7Data) {
        this.ip7Data = ip7Data;
        this.outerChunks = new ArrayList<>();
        this.hypernetChunks = new ArrayList<>();
        parse(this.ip7Data);
    }

    public boolean supportsSsl() {
        List<String> babList = new ArrayList<>();
        for (String hypernet: hypernetChunks) {
            for (int i = 0; i < hypernet.length() - 2; i++) {
                if(hypernet.charAt(i) == hypernet.charAt(i + 2)
                        && hypernet.charAt(i) != hypernet.charAt(i + 1)) {
                    babList.add(new String(new char[]{hypernet.charAt(i + 1), hypernet.charAt(i), hypernet.charAt(i + 1)}));
                }
            }
        }
        for (String bab: babList) {
            for (String outer: outerChunks) {
                if (outer.contains(bab)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean supportsTls() {
        return hasValidTlsHypernets(hypernetChunks) && hasValidTlsOuterChunks(outerChunks);
    }

    private boolean hasValidTlsOuterChunks(List<String> outerChunks) {
        boolean isValid = false;
        for (String outer: outerChunks) {
            if (containsAbbaPattern(outer)) {
                isValid = true;
            }
        }
        return isValid;
    }

    private boolean hasValidTlsHypernets(List<String> hypernetChunks) {
        boolean isValid = true;
        for (String hypernet: hypernetChunks) {
            if (containsAbbaPattern(hypernet)) {
                isValid = false;
            }
        }
        return isValid;
    }

    private boolean containsAbbaPattern(String input) {
        for (int i = 0; i < input.length() - 3; i++) {
            if (input.charAt(i) == input.charAt(i + 3)
                    && input.charAt(i + 1) == input.charAt(i + 2)
                    && input.charAt(i) != input.charAt(i + 1)) {
                return true;
            }
        }
        return false;
    }

    private void parse(String line) {
        Matcher matcher = IPV7_REGEX.matcher(line);
        int end = -1;
        while (matcher.find()) {
            this.outerChunks.add(matcher.group(1));
            this.hypernetChunks.add(matcher.group(2));
            end = matcher.end();
        }
        this.outerChunks.add(line.substring(end));
    }
}
