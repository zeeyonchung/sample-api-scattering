package com.kakaopay.scattering.application;

import com.kakaopay.scattering.domain.ScatterEvent;
import com.kakaopay.scattering.domain.ScatterEventRepo;
import com.kakaopay.scattering.domain.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class ScatterEventService {

    private final ScatterEventRepo scatterEventRepo;

    @Transactional(readOnly = true)
    public ScatterEventDetail detail(ScatterEventDetailRequest request) {
        String token = request.getToken();
        Long userId = request.getUserId();
        String roomId = request.getRoomId();

        ScatterEvent event = scatterEventRepo.findByTokenAndRoomId(Token.of(token), roomId)
                .orElseThrow(() -> new IllegalArgumentException("토큰과 방번호에 일치하는 이벤트가 없습니다"));

        return ScatterEventDetail.create(event);
    }
}
