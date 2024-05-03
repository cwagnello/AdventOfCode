package com.cwagnello;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    char[][] grid = new char[323][31];
	    try (Scanner sc = new Scanner(new File("input.txt"))) {
	        int index = 0;
	        while (sc.hasNextLine()) {
	            grid[index++] = sc.nextLine().toCharArray();
            }
	        part1(grid);
	        part2(grid);
        }
	    catch (IOException ioe) {
	        ioe.printStackTrace();
        }
    }

    private static void part1(char[][] grid) {
    	System.out.println(numberOfTrees(grid, 1, 3));
	}

	private static void part2(char[][] grid) {
		List<Integer> treeCounts = new ArrayList<>();
		long number = 1;
		number *= numberOfTrees(grid, 1, 1);
		number *= numberOfTrees(grid, 1, 3);
		number *= numberOfTrees(grid, 1, 5);
		number *= numberOfTrees(grid, 1, 7);
		number *= numberOfTrees(grid, 2, 1);
		System.out.println(number);
	}

	private static int numberOfTrees(char[][] grid, int dRow, int dCol) {
		int rows = grid.length;
		int cols = grid[0].length;
		int col = 0;
		int row = 0;
		int count = 0;
		while (row < rows) {
			if (grid[row][col] == '#') {
				count++;
			}
			col = (col + dCol) % cols;
			row = (row + dRow);
		}
		return count;
	}
}
