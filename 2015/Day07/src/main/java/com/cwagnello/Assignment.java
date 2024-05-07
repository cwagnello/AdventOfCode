package com.cwagnello;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper=true)
public class Assignment extends Unary {
    @Override
    public boolean evaluate() {
        this.getOutput().setValue(this.getInput().getValue());
        return true;
    }
}
