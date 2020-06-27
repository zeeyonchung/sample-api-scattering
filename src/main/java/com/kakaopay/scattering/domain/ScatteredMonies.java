package com.kakaopay.scattering.domain;

import com.kakaopay.scattering.domain.exception.AlreadyReceivedException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ScatteredMonies {

    @OneToMany(mappedBy = "scatterEvent", cascade = CascadeType.ALL)
    private List<ScatteredMoney> monies;

    private ScatteredMonies(List<ScatteredMoney> monies) {
        this.monies = monies;
    }

    public static ScatteredMonies of(List<ScatteredMoney> monies) {
        return new ScatteredMonies(monies);
    }

    public List<ScatteredMoney> getContent() {
        return Collections.unmodifiableList(monies);
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

    public ScatteredMoney sumAssigned() {
        return monies.stream()
                .filter(ScatteredMoney::isAssigned)
                .reduce(ScatteredMoney.ZERO, ScatteredMoney::sum);
    }

    public ScatteredMoney assignOneTo(Long userId) {
        checkAlreadyReceived(userId);

        return monies.stream()
                .filter(m -> !m.isAssigned())
                .findFirst()
                .map(m -> m.assignTo(userId))
                .orElseThrow(() -> new IllegalStateException("할당할 수 있는 금액이 없습니다"));
    }

    private void checkAlreadyReceived(Long userId) {
        monies.stream()
                .filter(m -> m.isAssignedTo(userId))
                .findAny()
                .ifPresent(m -> {throw new AlreadyReceivedException("이미 금액을 받았습니다");});
    }
}
