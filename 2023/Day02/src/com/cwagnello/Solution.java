package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 12 red cubes, 13 green cubes, and 14 blue cubes
 */
public class Solution {
    static final Game cubeGame = new Game(-1, 12, 14, 13);
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

        Map<Integer, List<Game>> games = parseGameData(input);
        part1(games);
        part2(games);
    }

    private static void part1(Map<Integer, List<Game>> games) {
        int sum = 0;
        boolean isValid = true;
        for (int id: games.keySet()) {
            for (Game g: games.get(id)) {
                if (!g.isValid(cubeGame)) {
                    isValid = false;
                }
            }
            if (isValid) {
                //System.out.println("Valid id: " + id);
                sum += id;
            }
            isValid = true;
        }
        System.out.println("Part 1 sum of IDs: " + sum);
    }

    private static void part2(Map<Integer, List<Game>> games) {
        int sum = 0;
        for (int id: games.keySet()) {
            int maxRed = 0, maxBlue = 0, maxGreen = 0;

            for (Game g: games.get(id)) {
                maxRed = Math.max(maxRed, g.getRed());
                maxBlue = Math.max(maxBlue, g.getBlue());
                maxGreen = Math.max(maxGreen, g.getGreen());
            }
            sum += maxRed * maxBlue * maxGreen;
        }
        System.out.println("Part 2 sum: " + sum);
    }
    private static Map<Integer, List<Game>> parseGameData(List<String> input) {
        Map<Integer, List<Game>> games = new HashMap<>();
        for (String h: input) {
            String[] gameData = h.split(":");
            String[] gameInfo = gameData[0].split(" ");

            int id = Integer.parseInt(gameInfo[1]);
            for (String round : gameData[1].split(";")) {
                String[] itemSet = round.split(",");
                Game game = new Game();
                game.setId(id);
                for (String j : itemSet) {
                    String[] colorData = j.trim().split(" ");
                    switch (colorData[1]) {
                        case "red" -> game.setRed(Integer.parseInt(colorData[0]));
                        case "blue" -> game.setBlue(Integer.parseInt(colorData[0]));
                        case "green" -> game.setGreen(Integer.parseInt(colorData[0]));
                    }
                }
                if (!games.containsKey(id)) {
                    games.put(id, new ArrayList<>());
                }
                games.get(id).add(game);
            }

        }
        return games;
    }

}

class Game {
    private int id;
    private int red;
    private int blue;
    private int green;

    public Game() {

    }
    public Game(int id, int red, int blue, int green) {
        this.id = id;
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public boolean isValid(Game other) {
        return this.getRed() <= other.getRed()
                && this.getBlue() <= other.getBlue()
                && this.getGreen() <= other.getGreen();
    }
    @Override
    public String toString() {
        return "id: " + this.id + ", red: " + this.red + ", blue: " + this.blue + ", green: " + this.green;
    }
}
