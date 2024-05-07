package com.cwagnello;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Not extends Unary {
    @Override
    public boolean evaluate() {
        if (this.hasValidInputs()) {
            this.getOutput().setValue(~this.getInput().getValue());
            return true;
        }
        return false;
    }
}
