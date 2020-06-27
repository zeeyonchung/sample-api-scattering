package com.kakaopay.scattering.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ReceiveMoneyService {

    @Transactional
    public long receive(ReceiveRequest successRequest) {
        return 0;
    }
}
