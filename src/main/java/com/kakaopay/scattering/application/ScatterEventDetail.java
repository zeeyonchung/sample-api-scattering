package com.kakaopay.scattering.application;

import com.kakaopay.scattering.domain.ReceiveHistory;
import com.kakaopay.scattering.domain.ScatterEvent;
import com.kakaopay.scattering.domain.ScatteredMoney;
import com.kakaopay.scattering.domain.ScatteredMonies;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScatterEventDetail {

    private Long eventId;
    private LocalDateTime createdDate;
    private long originalMoney;
    private long receivedMoneySum;
    private List<ReceiveHistoryDetail> receiveHistories;

    public static ScatterEventDetail create(ScatterEvent event) {
        ScatteredMonies monies = event.getScatteredMonies();
        long originalMoney = monies.sum().getMoney();
        long receivedMoneySum = monies.sumAssigned().getMoney();

        ScatterEventDetail detail = new ScatterEventDetail();
        detail.eventId = event.getId();
        detail.createdDate = event.getCreatedDate();
        detail.originalMoney = originalMoney;
        detail.receivedMoneySum = receivedMoneySum;
        detail.receiveHistories = createReceiveHistoryDetails(monies);

        return detail;
    }

    private static List<ReceiveHistoryDetail> createReceiveHistoryDetails(ScatteredMonies monies) {
        return monies.getContent().stream()
                .filter(ScatteredMoney::isAssigned)
                .map(ReceiveHistoryDetail::create)
                .collect(toList());
    }

    public static class ReceiveHistoryDetail {
        private LocalDateTime createdDate;
        private Long receiverId;
        private long receivedMoney;

        private static ReceiveHistoryDetail create(ScatteredMoney money) {
            ReceiveHistory history = money.getReceiveHistory();

            ReceiveHistoryDetail detail = new ReceiveHistoryDetail();
            detail.createdDate = history.getCreatedDate();
            detail.receiverId = history.getReceiver().getUserId();
            detail.receivedMoney = money.getMoney();

            return detail;
        }
    }
}
