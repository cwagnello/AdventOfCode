package com.cwagnello;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper=true)
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
