package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
    public static void main (String[] args) {
        File file = new File("src/com/cwagnello/input.txt");
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

        Map<String, Module> modules = new HashMap<>();
        modules.put("rx", new Broadcaster("rx"));
        parseInput(input, modules);
        System.out.println("Day20 - Part1: " + part1(modules));

        modules.clear();
        modules.put("rx", new Broadcaster("rx"));
        parseInput(input, modules);
        System.out.println("Day20 - Part2: " + part2(modules));
    }

    private static long part2(Map<String, Module> modules) {

        //Get all modules that are inputs to module "qn". The point is that when all 4 go high the output of "qn"
        // will be low. Each goes in a cycle that would take a long time to complete to wait for the required
        // button presses for each to go high. So if we just look at when each individual one goes high we assume that
        // that is the cycle length
        List<Module> lastModules = new ArrayList<>();
        lastModules.addAll(modules.get("qn").getInputs());
        Map<String, Long> buttonPressesPerModule = new HashMap<>();

        long buttonPresses = 0;
        while (++buttonPresses > 0) {
            Deque<Pulse> queue = new LinkedList<>();
            Pulse start = new Pulse();
            start.setDestination(modules.get("broadcaster"));
            queue.offer(start);

            while (!queue.isEmpty()) {
                Pulse current = queue.poll();
                if (current.getDestination().shouldSendPulse(current)) {
                    boolean nextSignal = modules.get(current.getDestination().getName()).receivePulse(current);
                    if (nextSignal && lastModules.contains(current.getDestination())) {
                        //System.out.println("Module: " + current.getDestination().getName() + ", count:" + buttonPresses);
                        buttonPressesPerModule.put(current.getDestination().getName(), buttonPresses);
                        if (buttonPressesPerModule.size() == 4) {
                            return buttonPressesPerModule.values().stream().reduce(1L, (a, b) -> a * b);
                        }
                    }
                    //add next pulses to queue for processing if there is a change in the signal
                    for (Module module : current.getDestination().getOutputs()) {
                        Pulse nextPulse = new Pulse();
                        nextPulse.setDestination(module);
                        nextPulse.setSignal(nextSignal);
                        queue.offer(nextPulse);
                    }
                }
            }
        }
        return -1;
    }
    private static long part1(Map<String, Module> modules) {

        long high = 0;
        long low = 0;
        for (int buttonPresses = 0; buttonPresses < 1000; buttonPresses++) {
            Deque<Pulse> queue = new LinkedList<>();
            Pulse start = new Pulse();
            start.setDestination(modules.get("broadcaster"));
            queue.offer(start);

            while (!queue.isEmpty()) {
                Pulse current = queue.poll();
                if (current.getSignal()) {
                    high++;
                } else {
                    low++;
                }

                if (current.getDestination().shouldSendPulse(current)) {
                    boolean nextSignal = modules.get(current.getDestination().getName()).receivePulse(current);
                    //add next pulses to queue for processing if there is a change in the signal
                    for (Module module : current.getDestination().getOutputs()) {
                        Pulse nextPulse = new Pulse();
                        nextPulse.setDestination(module);
                        nextPulse.setSignal(nextSignal);
                        queue.offer(nextPulse);
                    }
                }
            }
            //System.out.println("Low: " + low + ", High: " + high );
        }

        return high * low;
    }

    private static void parseInput(List<String> input, Map<String, Module> modules) {
        String[][] data = new String[input.size()][2];
        //Get all modules
        int index = 0;
        for (String line: input) {
            data[index] = line.split(" -> ");
            String moduleName = data[index][0].substring(1);
            if (data[index][0].startsWith("%")) {
                modules.put(moduleName, new FlipFlop(moduleName));
                data[index][0] = moduleName;
            }
            else if (data[index][0].startsWith("&")) {
                modules.put(moduleName, new Conjunction(moduleName));
                data[index][0] = moduleName;
            }
            else if (data[index][0].startsWith("broad")) {
                modules.put(data[index][0], new Broadcaster(data[index][0]));
            }
            else {
                throw new IllegalArgumentException("Unknown module type: " + data[index][0]);
            }
            index++;
        }

        Module button = new Broadcaster("button");
        button.getOutputs().add(modules.get("broadcaster"));
        modules.get("broadcaster").getInputs().add(button);
        modules.put("button", button);

        //Connect the remaining input/output module connections
        index = 0;
        while (index < data.length) {
            for (String moduleName: data[index][1].split(",\\s+")) {
                Module current = modules.get(data[index][0]);
                Module next = modules.get(moduleName);
                current.getOutputs().add(next);
                // "next" can be null for the final output module
                if (next != null) {
                    next.getInputs().add(current);
                }
            }

            index++;
        }
    }

}

abstract class Module {
    private String name;
    private List<Module> inputs;
    private List<Module> outputs;
    private boolean signal;

    public Module() {
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.signal = false;
    }
    public List<Module> getInputs() {
        return inputs;
    }
    public List<Module> getOutputs() {
        return outputs;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean getSignal() {
        return signal;
    }

    public void setSignal(boolean signal) {
        this.signal = signal;
    }

    public boolean shouldSendPulse(Pulse pulse) {
        return true;
    }
    abstract public boolean receivePulse(Pulse pulse);
}

class Broadcaster extends Module {
    public Broadcaster(String name) {
        this.setName(name);
    }

    public boolean receivePulse(Pulse pulse) {
        this.setSignal(pulse.getSignal());
        return this.getSignal();
    }

    @Override
    public boolean shouldSendPulse(Pulse pulse) {
        return true;
    }
}

class FlipFlop extends Module {
    public FlipFlop(String name) {
        this.setName(name);
    }

    //If a flip-flop module receives a high pulse, it is ignored and nothing happens.
    // However, if a flip-flop module receives a low pulse, it flips between on and off.
    public boolean receivePulse(Pulse pulse) {
        boolean currentPulse = this.getSignal();
        //update output for the next module
        if (!pulse.getSignal()) {
            currentPulse = !currentPulse;
            this.setSignal(currentPulse);
        }
        return currentPulse;
    }

    @Override
    public boolean shouldSendPulse(Pulse pulse) {
        return !pulse.getSignal();
    }
}

class Conjunction extends Module {
    public Conjunction(String name) {
        this.setName(name);
    }
    //This is just a NAND gate
    public boolean receivePulse(Pulse pulse) {
        for (Module input: this.getInputs()) {
            if (!input.getSignal()) {
                this.setSignal(true);
                return this.getSignal();
            }
        }
        this.setSignal(false);
        return this.getSignal();
    }

}

class Pulse {
    private boolean signal;
    private Module destination;
    public Pulse() {
        this.signal = false;
    }

    public boolean getSignal() {
        return this.signal;
    }
    public void setSignal(boolean signal) {
        this.signal = signal;
    }

    public Module getDestination() {
        return destination;
    }

    public void setDestination(Module destination) {
        this.destination = destination;
    }

}