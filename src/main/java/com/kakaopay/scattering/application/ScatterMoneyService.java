package com.kakaopay.scattering.application;

import com.kakaopay.scattering.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ScatterMoneyService {

    private final ScatterEventRepo scatterEventRepo;
    private final MoneyDivider moneyDivider;
    private final TokenGenerator tokenGenerator;

    @Transactional
    public Token scatter(ScatterRequest request) {
        Token token = tokenGenerator.generate();
        String roomId = request.getRoomId();
        Scatterer scatterer = new Scatterer(request.getUserId());
        ScatteredMonies scatteredMonies = moneyDivider.divide(request.getMoney(), request.getReceiverCount());

        ScatterEvent scatterEvent = ScatterEvent.builder()
                .token(token)
                .roomId(roomId)
                .scatterer(scatterer)
                .scatteredMonies(scatteredMonies)
                .build();

        scatterEventRepo.save(scatterEvent);

        return token;
    }
}
