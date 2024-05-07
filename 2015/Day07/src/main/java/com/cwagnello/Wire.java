package com.cwagnello;

import lombok.Data;

@Data
public class Wire {
    private String name;
    private Integer value;

    public Wire(String name) {
        this.name = name;
    }
}
