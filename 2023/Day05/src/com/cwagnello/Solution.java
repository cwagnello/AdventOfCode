package com.cwagnello;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Solution {
    private static final int TO = 0;
    private static final int FROM = 1;
    private static final int LENGTH = 2;

    public static void main(String[] args) {
        String input = readInput();
        String[] data = input.split("\\n\\n");
        int index = data[0].indexOf(":") + 1;
        List<Long> seeds = Stream.of(data[0].substring(index).trim().split("\\s+")).map(Long::parseLong).toList();
        List<RangeMap> seedToSoil = generateRangeMapping(data[1]);
        List<RangeMap> soilToFertilizer = generateRangeMapping(data[2]);
        List<RangeMap> fertilizerToWater = generateRangeMapping(data[3]);
        List<RangeMap> waterToLight = generateRangeMapping(data[4]);
        List<RangeMap> lightToTemperature = generateRangeMapping(data[5]);
        List<RangeMap> temperatureToHumidity = generateRangeMapping(data[6]);
        List<RangeMap> humidityToLocation = generateRangeMapping(data[7]);

        long min = Long.MAX_VALUE;
        for (long seed : seeds) {
            min = Math.min(min, map(map(map(map(map(map(map(seed, seedToSoil), soilToFertilizer), fertilizerToWater), waterToLight), lightToTemperature), temperatureToHumidity), humidityToLocation));
        }
        System.out.println("Part1, min: " + min);
        //        System.out.println(part1());

        long startTime = System.currentTimeMillis();
        min = Long.MAX_VALUE;
        for (int i = 0; i < seeds.size(); i += 2) {
            for (long seed = seeds.get(i); seed < seeds.get(i) + seeds.get(i + 1); seed++) {
                min = Math.min(min, map(map(map(map(map(map(map(seed, seedToSoil), soilToFertilizer), fertilizerToWater), waterToLight), lightToTemperature), temperatureToHumidity), humidityToLocation));
            }
        }
        System.out.println("Part2, min: " + min);
        System.out.println("Elapsed time: " + (System.currentTimeMillis() - startTime));
    }

    private static long map(long input, List<RangeMap> rangeMaps) {
        for (RangeMap rangeMap : rangeMaps) {
            if (input >= rangeMap.getFrom().getStart() && input <= rangeMap.getFrom().getEnd()) {
                return input + rangeMap.diff();
            }
        }
        return input;
    }

    private static List<RangeMap> generateRangeMapping(String data) {
        List<RangeMap> rangeMapping = new ArrayList<>();
        String[] rangeData = data.split("\\n");
        for (int i = 1; i < rangeData.length; i++) {
            String[] parts = rangeData[i].split("\\s+");
            Range from = new Range(Long.parseLong(parts[FROM]), Long.parseLong(parts[FROM]) + Long.parseLong(parts[LENGTH]), Long.parseLong(parts[LENGTH]));
            Range to = new Range(Long.parseLong(parts[TO]), Long.parseLong(parts[TO]) + Long.parseLong(parts[LENGTH]), Long.parseLong(parts[LENGTH]));
            rangeMapping.add(new RangeMap(to, from));
        }
        return rangeMapping;
    }

    private static int part1(List<String> input) {
        return 0;
    }

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
}

class Range {
    private long start;
    private long end;
    private long length;

    public Range(long start, long end, long length) {
        this.start = start;
        this.end = end;
        this.length = length;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}

class RangeMap {
    private Range to;
    private Range from;

    public RangeMap(Range to, Range from) {
        this.to = to;
        this.from = from;
    }

    public Range getFrom() {
        return from;
    }

    public void setFrom(Range from) {
        this.from = from;
    }

    public Range getTo() {
        return to;
    }

    public void setTo(Range to) {
        this.to = to;
    }

    //Assume the mapped value is already within the correct range and we just need to extrapolate what the correct mapped value is
    public long diff() {
        long diff = this.getTo().getStart() - this.getFrom().getStart();
        return diff;
    }

}