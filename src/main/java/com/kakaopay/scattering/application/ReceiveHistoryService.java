package com.kakaopay.scattering.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ReceiveHistoryService {

    @Transactional
    public void create(Long receivedMoneyId, Long receivedUserId) {

    }
}
