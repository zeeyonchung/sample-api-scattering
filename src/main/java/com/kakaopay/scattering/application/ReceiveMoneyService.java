package com.kakaopay.scattering.application;

import com.kakaopay.scattering.domain.ScatterEvent;
import com.kakaopay.scattering.domain.ScatterEventRepo;
import com.kakaopay.scattering.domain.ScatteredMoney;
import com.kakaopay.scattering.domain.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ReceiveMoneyService {

    private final ScatterEventRepo scatterEventRepo;
    private final ReceiveHistoryService receiveHistoryService;

    @Transactional
    public long receive(ReceiveRequest request) {
        String token = request.getToken();
        String roomId = request.getRoomId();
        Long userId = request.getUserId();

        ScatterEvent scatterEvent = scatterEventRepo.findByTokenAndRoomId(Token.of(token), roomId)
                .orElseThrow(() -> new IllegalArgumentException("토큰과 방번호에 일치하는 이벤트가 없습니다"));

        ScatteredMoney assignedMoney = scatterEvent.assignOne(LocalDateTime.now(), roomId, userId);
        receiveHistoryService.create(assignedMoney.getId(), userId);

        return assignedMoney.getMoney();
    }
}
