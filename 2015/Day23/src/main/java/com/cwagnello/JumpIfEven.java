package com.cwagnello;

public class JumpIfEven extends Jump {
    private Register register;

    public JumpIfEven(Register register, int offset) {
        super(offset);
        this.register = register;
    }

    @Override
    public int evaluate() {
        return this.register.getValue() % 2 == 0 ? this.getOffset() : 1;
    }
}
