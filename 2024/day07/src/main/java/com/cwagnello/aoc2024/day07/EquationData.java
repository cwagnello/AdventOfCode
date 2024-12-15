package com.cwagnello.aoc2024.day07;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class EquationData {
    private long result;
    private List<Long> operands;

    public EquationData(long result, String data) {
        this.result = result;
        this.operands = Arrays.stream(data.split("\\s+")).map(Long::parseLong).toList();
    }

    // 0 -> add
    // 1 -> multiply
    public boolean findValidEquation() {
        for (int index = 0; index < Math.pow(2, this.operands.size() - 1); index++) {
            int bitmask = index;
            long sum = this.operands.getFirst();
            for (int i = 1; i < this.operands.size(); i++) {
                if ((bitmask & 1) == 1) {
                    sum *= this.operands.get(i);
                }
                else {
                    sum += this.operands.get(i);
                }
                bitmask /= 2;
            }
            if (this.result == sum) {
                return true;
            }
        }
        return false;
    }

    // 0 -> add
    // 1 -> multiply
    // 2 -> concatenate
    public boolean isValid(List<String> permutations) {
        for (String equation: permutations) {
            long sum = this.operands.get(0);
            for (int i = 0; i < equation.length(); i++) {
                switch(equation.charAt(i)) {
                    case '0' -> sum += this.operands.get(i + 1);
                    case '1' -> sum *= this.operands.get(i + 1);
                    case '2' -> sum = Long.parseLong(sum + "" + this.operands.get(i + 1));
                }
            }
            if (sum == this.result) {
                return true;
            }
        }
        return false;
    }
}
