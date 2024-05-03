package com.cwagnello;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    File file = new File("src/com/cwagnello/input.txt");
	    try (Scanner sc = new Scanner(file)){
		int[] numbers = new int[200];
			int i = 0;
	        while (sc.hasNext()) {
				numbers[i++] = sc.nextInt();
            }

			part1(numbers);
	        part2(numbers);
		}
	    catch (IOException ioe) {
	        ioe.printStackTrace();
        }
    }

	private static void part1(int[] numbers) {
		for (int j = 0; j < numbers.length; j++) {
			for (int k = j + 1; k < numbers.length; k++) {
				if (numbers[j] + numbers[k] == 2020) {
					System.out.println(numbers[j] * numbers[k]);
				}
			}
		}
	}

	private static void part2(int[] numbers) {
		for (int i = 0; i < numbers.length; i++) {
			for (int j = i + 1; j < numbers.length; j++) {
				for (int k = j + 1; k < numbers.length; k++) {
					if (numbers[i] + numbers[j] + numbers[k] == 2020) {
						System.out.println(numbers[i] * numbers[j] * numbers[k]);
					}
				}
			}
		}
	}


}
