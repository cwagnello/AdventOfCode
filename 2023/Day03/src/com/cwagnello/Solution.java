package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

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

        System.out.println("Part1 sum: " + part1(input));
        System.out.println("Part2 sum: " + part2(input));
    }


    private static int part1(List<String> input) {
        char[][] data = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            data[i] = input.get(i).toCharArray();
        }
        int sum = 0;
        List<Part> parts = generatePartsList(data);
        for (Part p : parts) {
            sum += p.getNumber();
        }
        return sum;
    }

    private static int part2(List<String> input) {
        char[][] data = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            data[i] = input.get(i).toCharArray();
        }
        int sum = 0;
        List<Part> parts = generatePartsList(data);
        Map<Symbol, List<Part>> gearsMap = new HashMap<>();
        for (Part part: parts) {
            for (Symbol s: part.getSymbols()) {
                if (s.getSymbol() == '*') {
                    if (!gearsMap.containsKey(s)) {
                        gearsMap.put(s, new ArrayList<>());
                    }
                    gearsMap.get(s).add(part);
                }
            }
        }

        for (Symbol s: gearsMap.keySet()) {
            if (gearsMap.get(s).size() == 2) {
                sum += gearsMap.get(s).get(0).getNumber() * gearsMap.get(s).get(1).getNumber();
            }
        }
        return sum;
    }
    private static List<Part> generatePartsList(char[][] data) {
        List<Part> parts = new ArrayList<>();
        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[0].length; col++) {
                int start = -1;
                int end = -1;
                if (Character.isDigit(data[row][col])) {
                    while (col < data[0].length && Character.isDigit(data[row][col])) {
                        if (start == -1) {
                            start = col;
                        }
                        end = col;
                        col++;
                    }
                    Part part = generatePart(data, row, start, end);
                    if (part.isPart()){
                        parts.add(part);
                    }
                }
            }
        }
        return parts;
    }

    private static Part generatePart(char[][] data, int row, int start, int end) {
        Part part = new Part();
        part.setRow(row);
        part.setCol(start);
        for (int i = row - 1; i < row + 2; i++) {
            for (int j = start - 1; j <= end + 1; j++) {
                if (inBounds(data, i, j) && data[i][j] != '.' && !Character.isDigit(data[i][j])) {
                    part.addSymbol(new Symbol(data[i][j], i, j));
                    int partNumber = Integer.parseInt(new String(data[row], start, end - start + 1));
                    //System.out.println("Part from: [" + row + "][" + end + "], number: " + partNumber);
                    part.setNumber(partNumber);
                }
            }
        }
        return part;
    }

    private static boolean inBounds(char[][] data, int row, int col) {
        return row >= 0 && row < data.length && col >= 0 && col < data[0].length;
    }


}
class Part {
    private int number;
    private int row;
    private int col;
    private Set<Symbol> symbols = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return getNumber() == part.getNumber() && getRow() == part.getRow() && getCol() == part.getCol();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getRow(), getCol());
    }

    public void addSymbol(Symbol symbol) {
        if (symbol != null) {
            this.symbols.add(symbol);
        }
    }

    public Set<Symbol> getSymbols() {
        return this.symbols;
    }

    public boolean isPart() {
        return symbols.size() > 0;
    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

}

class Symbol {
    private Character symbol;
    private int row;
    private int col;

    public Symbol() {}

    public Symbol(Character symbol, int row, int col) {
        this.symbol = symbol;
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol part = (Symbol) o;
        return getRow() == part.getRow() && getCol() == part.getCol();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getCol());
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Character getSymbol() {
        return symbol;
    }

    public void setSymbol(Character symbol) {
        this.symbol = symbol;
    }
}