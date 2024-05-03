package com.cwagnello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

        // Part1
        List<Hand> hands = new ArrayList<>();
        for (String line: input) {
            String[] data = line.split("\\s+");
            hands.add(new Hand(data[0], Integer.parseInt(data[1])));
        }
        Collections.sort(hands);
        int winnings = 0;
        for (int rank = 0; rank < hands.size(); rank++) {
            winnings += (rank + 1) * hands.get(rank).getBid();
        }
        System.out.println("Part1 winnings: " + winnings);

        // Part2
        List<JokerHand> jokerHands = new ArrayList<>();
        for (String line: input) {
            String[] data = line.split("\\s+");
            jokerHands.add(new JokerHand(data[0], Integer.parseInt(data[1])));
        }
        Collections.sort(jokerHands);
        winnings = 0;
        for (int rank = 0; rank < jokerHands.size(); rank++) {
            winnings += (rank + 1) * jokerHands.get(rank).getBid();
        }
        System.out.println("Part2 winnings: " + winnings);
    }

}


class Card implements Comparable<Card> {
    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", type=" + type +
                '}';
    }

    int value;
    char type;

    public Card(int value, char type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public int compareTo(Card other) {
        return Integer.compare(this.value, other.value);
    }
}

class JokerHand extends Hand {
    public JokerHand(String cardData, int bid) {
        super(cardData, bid);
        //Create updated Joker Hand
        if (cardData.contains("J")) {
            Classification classification = this.getClassification();
            int jCount = 0;
            for (int i = 0; i < cardData.length(); i++) {
                if (cardData.charAt(i) == 'J') {
                    jCount++;
                }
            }
            Classification newClassification = null;
            if (classification == Classification.ONE_OF_KIND) {
                newClassification = Classification.TWO_OF_KIND;
            }
            else if (classification == Classification.TWO_OF_KIND) {
                newClassification = Classification.THREE_OF_KIND;
            }
            else if (classification == Classification.TWO_PAIR) {
                if (jCount == 1) {
                    newClassification = Classification.FULL_HOUSE;
                }
                else if (jCount == 2) {
                    newClassification = Classification.FOUR_OF_KIND;
                }
            }
            else if (classification == Classification.THREE_OF_KIND) {
                newClassification = Classification.FOUR_OF_KIND;
            }
            else {
                //For full house, four of a kind, and five of a kind the new classification
                // is always five of a kind
                newClassification = Classification.FIVE_OF_KIND;
            }
            this.setClassification(newClassification);
        }
    }

    @Override
    protected int cardValue(char card) {
        if (Character.isDigit(card)) {
            return card - '0';
        }
        return switch (card) {
            case 'A' -> 14;
            case 'T' -> 10;
            case 'J' -> 1;
            case 'Q' -> 12;
            case 'K' -> 13;
            default -> 0;
        };
    }
}

class Hand implements Comparable<Hand> {
    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + cards +
                ", bid=" + bid +
                ", classification=" + classification +
                '}';
    }

    private List<Card> cards;
    private int bid;
    private Classification classification;

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public Hand(String cardData, int bid) {
        this.cards = generateCards(cardData);
        this.setBid(bid);
        this.classification = determineHandClassification(this.cards);
    }

    private List<Card> generateCards(String cardData) {
        char[] card = cardData.toCharArray();
        List<Card> cards = new ArrayList<>();
        for (char c: card) {
            cards.add(new Card(cardValue(c), c));
        }
        return cards;
    }

    @Override
    public int compareTo(Hand other) {
        if (this.classification == other.classification) {
            int i = 0;
            while (i < 5 && this.cards.get(i).compareTo(other.cards.get(i)) == 0) {
                i++;
            }
            return this.cards.get(i).compareTo(other.cards.get(i));
        }
        return this.classification.compareTo(other.classification);
    }

    protected int cardValue(char card) {
        if (Character.isDigit(card)) {
            return card - '0';
        }
        return switch (card) {
            case 'A' -> 14;
            case 'T' -> 10;
            case 'J' -> 11;
            case 'Q' -> 12;
            case 'K' -> 13;
            default -> 0;
        };
    }
    protected Classification determineHandClassification(List<Card> cards) {
        int[] frequency = new int[15];
        for (Card c: cards) {
            frequency[c.value]++;
        }
        List<Integer> counts = new ArrayList<>();
        for (int f: frequency) {
            if (f > 0) {
                counts.add(f);
            }
        }
        Collections.sort(counts);
        int num = 0;
        for (int n: counts) {
            num = 10 * num + n;
        }
        return switch(num) {
            case 11111 -> Classification.ONE_OF_KIND;
            case 1112 -> Classification.TWO_OF_KIND;
            case 122 -> Classification.TWO_PAIR;
            case 113 -> Classification.THREE_OF_KIND;
            case 23 -> Classification.FULL_HOUSE;
            case 14 -> Classification.FOUR_OF_KIND;
            case 5 -> Classification.FIVE_OF_KIND;
            default -> throw new IllegalArgumentException();
        };
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }
}

enum Classification {
    ONE_OF_KIND(0),
    TWO_OF_KIND(1),
    TWO_PAIR(2),
    THREE_OF_KIND(3),
    FULL_HOUSE(4),
    FOUR_OF_KIND(5),
    FIVE_OF_KIND(6);

    private int score;

    Classification(int score) {
        this.score = score;
    }
}
