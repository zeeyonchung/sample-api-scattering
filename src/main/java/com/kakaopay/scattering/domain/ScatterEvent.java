package com.kakaopay.scattering.domain;

import com.kakaopay.scattering.domain.exception.AlreadyFinishedException;
import com.kakaopay.scattering.domain.exception.NotSameRoomException;
import com.kakaopay.scattering.domain.exception.SelfReceiveException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"token", "roomId"})}
)
@Entity
public class ScatterEvent extends BaseTimeEntity {

    private static final long RECEIVE_LIMIT_MINUTES = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Token token;
    private String roomId;
    private Scatterer scatterer;
    private ScatteredMonies scatteredMonies;

    @Builder
    public ScatterEvent(Token token,
                        String roomId,
                        Scatterer scatterer,
                        ScatteredMonies scatteredMonies,
                        LocalDateTime createdDate) {

        super(createdDate);

        Objects.requireNonNull(token);
        Objects.requireNonNull(roomId);
        Objects.requireNonNull(scatterer);
        Objects.requireNonNull(scatteredMonies);

        this.token = token;
        this.roomId = roomId;
        this.scatterer = scatterer;
        this.scatteredMonies = scatteredMonies;

        scatteredMonies.setScatterEvent(this);
    }

    public ScatteredMoney assignOne(LocalDateTime now, String roomId, Long userId) {
        validateAssignable(now, roomId, userId);
        return scatteredMonies.assignOneTo(userId);
    }

    private void validateAssignable(LocalDateTime now, String roomId, Long userId) {
        validateNotFinished(now);
        validateSameRoom(roomId);
        validateNotSelfReceive(userId);
    }

    private void validateNotFinished(LocalDateTime now) {
        if (getCreatedDate().plusMinutes(RECEIVE_LIMIT_MINUTES).isBefore(now)) {
            throw new AlreadyFinishedException("유효 시간이 지나 받을 수 없습니다");
        }
    }

    private void validateNotSelfReceive(Long userId) {
        if (this.scatterer.equals(new Scatterer(userId))) {
            throw new SelfReceiveException("자신이 뿌리기한 건은 자신이 받을 수 없습니다");
        }
    }

    private void validateSameRoom(String roomId) {
        if (!this.roomId.equals(roomId)) {
            throw new NotSameRoomException("뿌리기가 호출된 대화방과 동일한 대화방이 아니라 받을 수 없습니다");
        }
    }
}

