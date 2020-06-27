package com.kakaopay.scattering.application;

import com.kakaopay.scattering.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class ScatterEventServiceTest {

    @Autowired
    private ScatterEventService scatterEventService;

    @Autowired
    private ScatterEventRepo scatterEventRepo;

    @BeforeEach
    void setUp() {
        scatterEventRepo.deleteAll();
    }

    @DisplayName("조회")
    @Test
    void detail() {
        // given
        ScatteredMoney money1 = ScatteredMoney.of(100L);
        ScatteredMoney money2 = ScatteredMoney.of(200L);
        ScatteredMonies monies = ScatteredMonies.of(Arrays.asList(money1, money2));
        LocalDateTime createdDate = LocalDateTime.of(2020, 6, 1, 12, 10);

        ScatterEvent event = ScatterEvent.builder()
                .token(Token.of("123"))
                .roomId("room")
                .scatterer(new Scatterer(1L))
                .scatteredMonies(monies)
                .build();

        money1.assignTo(2L);
        scatterEventRepo.save(event);

        // when
        ScatterEventDetailRequest request = ScatterEventDetailRequest.builder()
                .token("123")
                .userId(1L)
                .roomId("room")
                .build();

        ScatterEventDetail detail = scatterEventService.detail(request);

        // then
        assertAll(
                () -> assertThat(detail.getOriginalMoney()).isEqualTo(300L),
                () -> assertThat(detail.getAssignedMoneySum()).isEqualTo(100L),
                () -> assertThat(detail.getReceiveHistories().size()).isEqualTo(1),
                () -> assertThat(detail.getReceiveHistories().get(0).getReceiverId()).isEqualTo(2L),
                () -> assertThat(detail.getReceiveHistories().get(0).getReceivedMoney()).isEqualTo(100L));
    }
}