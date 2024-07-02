package com.cwagnello;

import lombok.Data;

@Data
public class Register {
    private String name;
    private long value;

    public Register(String name) {
        this.name = name;
        this.value = 0;
    }
}
