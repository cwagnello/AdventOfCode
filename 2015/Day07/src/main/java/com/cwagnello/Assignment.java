package com.cwagnello;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Assignment extends Unary {
    @Override
    public boolean evaluate() {
        this.getOutput().setValue(this.getInput().getValue());
        return true;
    }
}
