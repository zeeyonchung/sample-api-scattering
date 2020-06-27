package com.kakaopay.scattering.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScatterEventRepo extends JpaRepository<ScatterEvent, Long> {
    Optional<ScatterEvent> findByTokenAndRoomId(Token token, String roomId);
}
