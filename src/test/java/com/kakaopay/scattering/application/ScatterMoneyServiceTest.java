package com.kakaopay.scattering.application;

import com.kakaopay.scattering.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;

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
                .receivers(Arrays.asList(new Receiver(1L), new Receiver(2L), new Receiver(3L)))
                .scatterer(new Scatterer(500L))
                .build();

        Token token = scatterMoneyService.scatter(request);
        ScatterEvent saved = scatterEventRepo.findByTokenAndScatterer(token, request.getScatterer());

        assertAll(
                () -> assertThat(saved.getToken()).isEqualTo(token),
                () -> assertThat(saved.getScatteredMonies().size()).isEqualTo(3),
                () -> assertThat(saved.getReceivers().size()).isEqualTo(3));
    }
}
