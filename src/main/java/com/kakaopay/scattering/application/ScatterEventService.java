package com.kakaopay.scattering.application;

import com.kakaopay.scattering.domain.ScatterEventRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class ScatterEventService {

    private final ScatterEventRepo scatterEventRepo;

    @Transactional(readOnly = true)
    public ScatterEventDetail detail(ScatterEventDetailRequest request) {
        return null;
    }
}
