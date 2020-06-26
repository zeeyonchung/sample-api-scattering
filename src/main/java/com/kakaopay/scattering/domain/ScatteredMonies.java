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

    public int size() {
        return monies.size();
    }

    void setScatterEvent(ScatterEvent event) {
        monies.forEach(m -> m.setScatterEvent(event));
    }

    public ScatteredMoney sum() {
        return monies.stream()
                .reduce(ScatteredMoney.ZERO, ScatteredMoney::sum);
    }

    public Long assignOne() {
        return monies.stream()
                .filter(ScatteredMoney::canAssign)
                .findFirst()
                .map(ScatteredMoney::assign)
                .orElseThrow(() -> new IllegalStateException("할당할 수 있는 금액이 없습니다"));
    }
}
