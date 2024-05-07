package com.cwagnello;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Or extends Binary {
    @Override
    public boolean evaluate() {
        if (this.hasValidInputs()) {
            this.getOutput().setValue(this.getInput1().getValue() | this.getInput2().getValue());
            return true;
        }
        return false;
    }
}
