package com.cwagnello;

public record Triangle(int a, int b, int c) {
    public boolean isValid() {
        return a + b > c && a + c > b && b + c > a;
    }
}
