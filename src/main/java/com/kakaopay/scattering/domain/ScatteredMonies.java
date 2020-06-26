package com.kakaopay.scattering.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    public int size() {
        return monies.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScatteredMonies monies1 = (ScatteredMonies) o;
        return Objects.equals(monies, monies1.monies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(monies);
    }
}
