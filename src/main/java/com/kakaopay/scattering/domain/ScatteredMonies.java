package com.kakaopay.scattering.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ScatteredMonies {

    @OneToMany(mappedBy = "scatterEvent", cascade = CascadeType.ALL)
    private List<ScatteredMoney> monies;

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
}
