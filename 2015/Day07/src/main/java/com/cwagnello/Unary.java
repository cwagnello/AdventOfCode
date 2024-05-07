package com.cwagnello;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Unary implements Operator{
    private Wire input;
    private Wire output;

    public boolean hasValidInputs() {
        return input.getValue() != null;
    }
}
