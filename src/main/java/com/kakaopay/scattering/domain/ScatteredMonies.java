package com.kakaopay.scattering.domain;

import java.util.Collections;
import java.util.List;

public class ScatteredMonies {

    private final List<ScatteredMoney> monies;

    private ScatteredMonies(List<ScatteredMoney> monies) {
        this.monies = Collections.unmodifiableList(monies);
    }

    public static ScatteredMonies of(List<ScatteredMoney> monies) {
        return new ScatteredMonies(monies);
    }

    public ScatteredMoney sum() {
        return monies.stream()
                .reduce(ScatteredMoney.ZERO, ScatteredMoney::sum);
    }
}
