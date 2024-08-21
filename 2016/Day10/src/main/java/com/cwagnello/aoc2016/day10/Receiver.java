package com.cwagnello.aoc2016.day10;

public class Receiver {
    private final int id;
    private final Type type;

    public Receiver(int id, String type) {
        this.id = id;
        this.type = Type.value(type);
    }

    public boolean isBot() {
        return type.equals(Type.BOT);
    }

    public boolean isOutput() {
        return type.equals(Type.OUTPUT);
    }

    public int id() {
        return this.id;
    }

    enum Type {
        BOT,
        OUTPUT;

        public static Type value(String type) {
            return switch(type) {
                case "bot" -> BOT;
                case "output" -> OUTPUT;
                default -> throw new IllegalArgumentException("Unknown type: " + type);
            };
        }
    }

}
