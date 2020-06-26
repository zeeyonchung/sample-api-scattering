package com.kakaopay.scattering.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScatterEventRepo extends JpaRepository<ScatterEvent, Long> {
    ScatterEvent findByTokenAndScatterer(Token token, Scatterer scatterer);
}
