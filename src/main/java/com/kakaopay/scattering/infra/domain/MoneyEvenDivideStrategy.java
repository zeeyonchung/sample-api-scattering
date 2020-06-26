package com.kakaopay.scattering.infra.domain;

import com.kakaopay.scattering.domain.MoneyDivideStrategy;
import com.kakaopay.scattering.domain.ScatteredMoney;
import com.kakaopay.scattering.domain.ScatteredMonies;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Component
public class MoneyEvenDivideStrategy implements MoneyDivideStrategy {

    @Override
    public ScatteredMonies divide(long originalMoney, int count) {
        long rest = originalMoney % count;
        long money = originalMoney / count;

        return IntStream.range(0, count)
                .mapToLong(index -> index < rest ? 1 : 0)
                .mapToObj(bonus -> ScatteredMoney.of(money + bonus))
                .collect(collectingAndThen(toList(), ScatteredMonies::of));
    }
}
