package com.cwagnello;

public class JumpIfOne extends Jump {
    private Register register;

    public JumpIfOne(Register register, int offset) {
        super(offset);
        this.register = register;
    }
    @Override
    public int evaluate() {
        return this.register.getValue() == 1 ? this.getOffset() : 1;
    }
}
