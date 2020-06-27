package com.kakaopay.scattering.application;

import com.kakaopay.scattering.domain.ScatterEvent;
import com.kakaopay.scattering.domain.ScatterEventRepo;
import com.kakaopay.scattering.domain.Scatterer;
import com.kakaopay.scattering.domain.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class ScatterEventService {

    private static final int SHOW_DETAIL_LIMIT_DAYS = 7;

    private final ScatterEventRepo scatterEventRepo;

    @Transactional(readOnly = true)
    public ScatterEventDetail detail(ScatterEventDetailRequest request) {
        String token = request.getToken();
        Long userId = request.getUserId();
        String roomId = request.getRoomId();

        ScatterEvent event = scatterEventRepo.findByTokenAndRoomId(Token.of(token), roomId)
                .orElseThrow(() -> new IllegalArgumentException("토큰과 방번호에 일치하는 이벤트가 없습니다"));

        validateRequest(userId, event);
        return ScatterEventDetail.create(event);
    }

    private void validateRequest(Long userId, ScatterEvent event) {
        if (!event.getScatterer().equals(new Scatterer(userId))) {
            throw new IllegalArgumentException("뿌린 사람만 조회 가능합니다");
        }

        LocalDateTime limitDate = LocalDateTime.now().minusDays(SHOW_DETAIL_LIMIT_DAYS);
        if (event.getCreatedDate().isBefore(limitDate)) {
            throw new IllegalArgumentException("조회 가능 기간이 지났습니다");
        }
    }
}
