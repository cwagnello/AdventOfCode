package com.cwagnello;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Binary implements Operator{
    private Wire input1;
    private Wire input2;
    private Wire output;

    public boolean hasValidInputs() {
        return input1.getValue() != null && input2.getValue() != null;
    }
}
