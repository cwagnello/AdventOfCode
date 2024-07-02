package com.cwagnello;

public class Triple extends Instruction {
    private Register register;

    public Triple(Register register) {
        this.register = register;
    }

    public void evaluate() {
        this.register.setValue(3 * this.register.getValue());
    }
}
