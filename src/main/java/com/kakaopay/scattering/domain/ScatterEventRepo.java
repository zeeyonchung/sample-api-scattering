package com.kakaopay.scattering.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ScatterEventRepo extends JpaRepository<ScatterEvent, Long> {
    @Query("select e from ScatterEvent e " +
            " left join fetch e.scatteredMonies.monies m " +
            " left join fetch m.receiveHistory" +
            " where e.token = :token and e.roomId = :roomId")
    Optional<ScatterEvent> findByTokenAndRoomId(@Param("token") Token token, @Param("roomId") String roomId);
}
