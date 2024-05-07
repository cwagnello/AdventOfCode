package com.cwagnello;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper=true)
public class Rshift extends Binary {
    @Override
    public boolean evaluate() {
        if (this.hasValidInputs()) {
            this.getOutput().setValue(this.getInput1().getValue() >> this.getInput2().getValue());
            return true;
        }
        return false;
    }
}
