package com.cwagnello;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class Ring extends Item {
    public Ring(String name, int cost, int damage, int armor) {
        super (name, cost, damage, armor);
    }
}
