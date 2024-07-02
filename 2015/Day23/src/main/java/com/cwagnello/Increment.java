package com.cwagnello;

public class Increment extends Instruction {
    private Register register;

    public Increment(Register register) {
        this.register = register;
    }

    public void evaluate() {
        this.register.setValue(this.register.getValue() + 1);
    }
}
