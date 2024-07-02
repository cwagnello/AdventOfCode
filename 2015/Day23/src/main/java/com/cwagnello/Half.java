package com.cwagnello;

public class Half extends Instruction {
    private Register register;

    public Half(Register register) {
        this.register = register;
    }

    public void evaluate() {
        this.register.setValue(this.register.getValue() >> 1);
    }
}
