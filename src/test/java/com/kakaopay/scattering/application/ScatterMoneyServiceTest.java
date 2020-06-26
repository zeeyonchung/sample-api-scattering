package com.kakaopay.scattering.application;

import com.kakaopay.scattering.domain.ScatterEvent;
import com.kakaopay.scattering.domain.ScatterEventRepo;
import com.kakaopay.scattering.domain.Scatterer;
import com.kakaopay.scattering.domain.Token;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class ScatterMoneyServiceTest {

    @Autowired
    private ScatterMoneyService scatterMoneyService;

    @Autowired
    private ScatterEventRepo scatterEventRepo;

    @Transactional
    @DisplayName("생성한 뿌리기 이벤트를 저장한다")
    @Test
    void scatter_save() {
        ScatterRequest request = ScatterRequest.builder()
                .money(1000L)
                .scatterer(new Scatterer(500L))
                .receiverCount(3)
                .build();

        Token token = scatterMoneyService.scatter(request);
        ScatterEvent saved = scatterEventRepo.findByTokenAndScatterer(token, request.getScatterer());

        assertAll(
                () -> assertThat(saved.getToken()).isEqualTo(token),
                () -> assertThat(saved.getScatteredMonies().size()).isEqualTo(3));
    }
}
