package com.cwagnello.aoc2024.day13;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Data
public class Prize {
    private static final int TOKEN_A_COST = 3;
    private static final int TOKEN_B_COST = 1;
    private Coordinate buttonA;
    private Coordinate buttonB;
    private Coordinate location;

    @Setter(AccessLevel.NONE)
    private Answer answer;

    public Prize(Coordinate a, Coordinate b, Coordinate location, Answer answer) {
        super();
        this.location = location;
        this.buttonA = a;
        this.buttonB = b;
        this.answer = new Answer();
    }

    public void solve() {
        //B = (y * Xa - x * Ya) / (Yb * Xa - Xb * Ya)
        //A = (x - B * Xb) / Xa
        double B = (double) (location.y() * buttonA.x() - location.x() * buttonA.y()) / (buttonB.y() * buttonA.x() - buttonB.x() * buttonA.y());
        double A = (location.x() - B * buttonB.x()) / (double)buttonA.x();
        if (Math.round(A) != A || Math.round(B) != B) {
            return;
        }
        this.answer = new Answer((long)A, (long)B);
    }

    public long minimumTokens() {
        if (answer.a() == -1) {
            return 0;
        }
        long min = Long.MAX_VALUE;
//            log.info("a: {}, b: {}, tokens: {}", answer.a(), answer.b(), TOKEN_A_COST * answer.a() + TOKEN_B_COST * answer.b());
        min = Math.min(min, TOKEN_A_COST * answer.a() + TOKEN_B_COST * answer.b());
        //log.info("Min tokens: {}", min);
        return min;
    }

}
